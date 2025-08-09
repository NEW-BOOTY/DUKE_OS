/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package applemdm;

public class AppleDeviceManager {
    public static void main(String[] args) {
        try {
            MdmConfig config = MdmConfig.load("resources/mdm_config.json");
            SecureKeyStore.init("resources/mdm_identity.p12", config.getKeystorePassword());

            EnrollmentProfileGenerator.generate(config);
            ApnsPushService pushService = new ApnsPushService(config);
            pushService.connect();

            String deviceToken = config.getTestDeviceToken();
            pushService.sendTestNotification(deviceToken);

            DeviceCommandDispatcher.sendLockCommand(deviceToken, config);

            pushService.shutdown();

        } catch (Exception e) {
            System.err.println("[FATAL] AppleDeviceManager failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
