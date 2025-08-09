/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package applemdm;

import java.io.FileReader;
import com.google.gson.*;

public class MdmConfig {
    private String teamId, keyId, bundleId, apnsKeyPath, apnsTopic;
    private String testDeviceToken, keystorePassword;

    public static MdmConfig load(String path) throws Exception {
        try (FileReader reader = new FileReader(path)) {
            return new Gson().fromJson(reader, MdmConfig.class);
        } catch (Exception e) {
            throw new Exception("Failed to parse MDM configuration file: " + e.getMessage());
        }
    }

    // Getters
    public String getTeamId() { return teamId; }
    public String getKeyId() { return keyId; }
    public String getBundleId() { return bundleId; }
    public String getApnsKeyPath() { return apnsKeyPath; }
    public String getApnsTopic() { return apnsTopic; }
    public String getTestDeviceToken() { return testDeviceToken; }
    public String getKeystorePassword() { return keystorePassword; }
}
