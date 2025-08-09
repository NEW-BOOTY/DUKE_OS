/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package applemdm;

import java.io.FileWriter;
import java.util.UUID;

public class EnrollmentProfileGenerator {

    public static void generate(MdmConfig config) {
        String uuid = UUID.randomUUID().toString();
        String profile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" "
                + "\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n"
                + "<plist version=\"1.0\">\n"
                + "<dict>\n"
                + "    <key>PayloadContent</key>\n"
                + "    <array>\n"
                + "        <dict>\n"
                + "            <key>PayloadType</key>\n"
                + "            <string>com.apple.mdm</string>\n"
                + "            <key>PayloadVersion</key>\n"
                + "            <integer>1</integer>\n"
                + "            <key>PayloadIdentifier</key>\n"
                + "            <string>com.devinroyal.mdm</string>\n"
                + "            <key>PayloadUUID</key>\n"
                + "            <string>" + uuid + "</string>\n"
                + "            <key>PayloadDisplayName</key>\n"
                + "            <string>Devin Royal MDM Profile</string>\n"
                + "            <key>ServerURL</key>\n"
                + "            <string>https://mdm.devinroyal.com/enroll</string>\n"
                + "            <key>Topic</key>\n"
                + "            <string>" + config.getApnsTopic() + "</string>\n"
                + "        </dict>\n"
                + "    </array>\n"
                + "    <key>PayloadType</key>\n"
                + "    <string>Configuration</string>\n"
                + "    <key>PayloadVersion</key>\n"
                + "    <integer>1</integer>\n"
                + "    <key>PayloadIdentifier</key>\n"
                + "    <string>com.devinroyal.mdm.config</string>\n"
                + "    <key>PayloadUUID</key>\n"
                + "    <string>" + UUID.randomUUID() + "</string>\n"
                + "    <key>PayloadDisplayName</key>\n"
                + "    <string>Devin Royal Config</string>\n"
                + "</dict>\n"
                + "</plist>";

        try (FileWriter writer = new FileWriter("resources/enrollment.mobileconfig")) {
            writer.write(profile);
            System.out.println("[MDM] Enrollment profile generated.");
        } catch (Exception e) {
            System.err.println("[MDM] Failed to write profile: " + e.getMessage());
        }
    }
}
