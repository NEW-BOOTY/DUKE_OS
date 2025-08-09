/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package device.manager;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class AuditLogger {
    private static final String LOG_PATH = "logs/audit.log";

    public static void log(String source, String action, String details) {
        try (FileWriter fw = new FileWriter(LOG_PATH, true)) {
            fw.write(String.format("{\"time\":\"%s\",\"source\":\"%s\",\"action\":\"%s\",\"details\":%s}%n",
                    Instant.now(), source, action, details));
        } catch (IOException e) {
            System.err.println("[AuditLogger] Failed to log audit: " + e.getMessage());
        }
    }
}
