/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package com.redsentinel.lab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PersistenceManager {

    private static final Logger LOGGER = Logger.getLogger(PersistenceManager.class.getName());

    public static void simulatePersistence() {
        LOGGER.info("[RedSentinel™] PersistenceManager simulating persistence mechanisms...");

        try {
            createStartupScript();
            createHiddenScheduledTask();

            LOGGER.info("Persistence simulation complete. No real changes made outside sandbox.");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during persistence simulation.", e);
        }
    }

    private static void createStartupScript() throws IOException {
        String userHome = System.getProperty("user.home");
        File startupDir = new File(userHome + File.separator + "RedSentinel_Persistence");
        if (!startupDir.exists() && !startupDir.mkdirs()) {
            throw new IOException("Unable to create directory: " + startupDir.getAbsolutePath());
        }

        File scriptFile = new File(startupDir, "startup_simulation.sh");
        try (FileWriter fw = new FileWriter(scriptFile)) {
            fw.write("#!/bin/bash\n");
            fw.write("echo '[RedSentinel™] Simulated persistence: Startup script triggered.'\n");
        }

        if (!scriptFile.setExecutable(true)) {
            LOGGER.warning("Could not mark simulated startup script as executable.");
        }

        LOGGER.info("Simulated startup script created at: " + scriptFile.getAbsolutePath());
    }

    private static void createHiddenScheduledTask() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        String taskName = "RedSentinel_Startup_Sim";

        if (os.contains("win")) {
            LOGGER.info("Simulating Windows Scheduled Task registration: " + taskName);
            // No actual commands executed for safety
        } else {
            File cronSimFile = new File("/tmp/." + taskName + "_cron");
            try (FileWriter fw = new FileWriter(cronSimFile)) {
                fw.write("@reboot echo '[RedSentinel™] Simulated cron persistence executed'\n");
            }

            LOGGER.info("Simulated cron job file written: " + cronSimFile.getAbsolutePath());
        }
    }
}

/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
