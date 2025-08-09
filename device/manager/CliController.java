/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package device.manager;

import applemdm.*;
import androidenterprise.*;

import java.util.Scanner;

public class CliController {

    private final ApnsPushService apnsPush;
    private final EnrollmentProfileGenerator profileGen;
    private final DeviceCommandDispatcher dispatcher;
    private final FcmPushService fcmPush;
    private final DevicePolicyController dpc;

    public CliController(ApnsPushService apnsPush, EnrollmentProfileGenerator profileGen,
                         DeviceCommandDispatcher dispatcher, FcmPushService fcmPush,
                         DevicePolicyController dpc) {
        this.apnsPush = apnsPush;
        this.profileGen = profileGen;
        this.dispatcher = dispatcher;
        this.fcmPush = fcmPush;
        this.dpc = dpc;
    }

    public void start() {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("✅ CLI ready. Enter commands: ");
            while (true) {
                try {
                    System.out.print("> ");
                    String input = scanner.nextLine().trim();
                    String[] args = input.split(" ");
                    switch (args[0]) {
                        case "send-apns":
                            apnsPush.sendPush("{\"mdm\":\"DeviceLock\"}");
                            break;
                        case "generate-profile":
                            profileGen.generateProfile("out/enroll.mobileconfig", "https://mdm.myserver.com", "Royal Corp");
                            break;
                        case "lock-device":
                            dispatcher.sendLockCommand("test-device-1");
                            break;
                        case "send-fcm":
                            if (args.length < 3) {
                                System.out.println("Usage: send-fcm <device-token> <message>");
                            } else {
                                fcmPush.sendPushNotification(args[1], "DPC Notice", args[2]);
                            }
                            break;
                        case "enforce":
                            dpc.enforcePolicy("android-device-1", args[1], args[2]);
                            break;
                        case "exit":
                            System.exit(0);
                        default:
                            System.out.println("Commands: send-apns, generate-profile, lock-device, send-fcm <token> <msg>, enforce <key> <value>, exit");
                    }
                } catch (Exception e) {
                    System.err.println("[CLI ERROR] " + e.getMessage());
                }
            }
        }).start();
    }
}
