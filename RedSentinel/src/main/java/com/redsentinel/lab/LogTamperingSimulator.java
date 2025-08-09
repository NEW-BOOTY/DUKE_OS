/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.redsentinel.lab;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LogTamperingSimulator {

    private static final Logger LOGGER = Logger.getLogger(LogTamperingSimulator.class.getName());

    private static final String ORIGINAL_LOG = System.getProperty("user.home") + File.separator + "redsentinel_auth.log";
    private static final String BACKUP_LOG = System.getProperty("user.home") + File.separator + "redsentinel_auth_backup.log";

    private LogTamperingSimulator() {
        // Prevent instantiation
    }

    public static void simulateLogTampering() {
        LOGGER.info("[RedSentinel™] Simulating log tampering technique...");

        try {
            // Step 1: Simulate a log file creation
            createSimulatedLog();

            // Step 2: Backup original log
            Files.copy(Paths.get(ORIGINAL_LOG), Paths.get(BACKUP_LOG), StandardCopyOption.REPLACE_EXISTING);

            // Step 3: Overwrite a suspicious entry
            Path logPath = Paths.get(ORIGINAL_LOG);
            String modifiedContent = new String(Files.readAllBytes(logPath))
                    .replaceAll("unauthorized access from 192\\.168\\.0\\.13", "[REDACTED - Cleared by RedSentinel™]");

            Files.write(logPath, modifiedContent.getBytes());

            LOGGER.info("Log tampering simulation completed. Backup saved to: " + BACKUP_LOG);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error during simulated log tampering.", e);
        }
    }

    private static void createSimulatedLog() throws IOException {
        File file = new File(ORIGINAL_LOG);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("[" + timestamp() + "] user=devin login success\n");
                writer.write("[" + timestamp() + "] unauthorized access from 192.168.0.13\n");
                writer.write("[" + timestamp() + "] system config changed by root\n");
            }
        }
    }

    private static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}

/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
