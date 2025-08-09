/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package device.manager;

import applemdm.*;
import androidenterprise.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DeviceManagerLauncher {

    public static void main(String[] args) {
        try {
            System.out.println("🔐 Device Manager System Launching...");

            MdmConfig config = new MdmConfig("resources/mdm_config.json");
            ApnsPushService apnsPush = new ApnsPushService(config);
            EnrollmentProfileGenerator profileGen = new EnrollmentProfileGenerator();
            DeviceCommandDispatcher cmdDispatcher = new DeviceCommandDispatcher();

            String fcmKey = new String(Files.readAllBytes(Paths.get("resources/fcm_server_key.txt"))).trim();
            FcmPushService fcmPush = new FcmPushService(fcmKey);
            DevicePolicyController dpc = new DevicePolicyController();

            // CLI Controller
            CliController cli = new CliController(apnsPush, profileGen, cmdDispatcher, fcmPush, dpc);
            cli.start();

            // HTTP Server
            HttpServerRouter httpRouter = new HttpServerRouter(apnsPush, fcmPush);
            httpRouter.start(8080);

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to launch device manager: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
