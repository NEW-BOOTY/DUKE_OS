/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.launcher;

import net.sentient.core.ErrorController;
import net.sentient.core.SentientCore;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sentinel Launcher
 * Entry point for SentientNet Operating Intelligence.
 * Supports CLI startup and environment checks.
 */
public final class Launcher {

    private static final Logger logger = Logger.getLogger(Launcher.class.getName());

    private Launcher() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        try {
            logger.info("Starting SentientNet Launcher...");

            // Environment sanity checks can be added here
            checkEnvironment();

            // Initialize core system
            SentientCore core = SentientCore.getInstance();
            core.initialize();

            // Start CLI or embedded REST server based on args or env
            if (args.length > 0 && args[0].equalsIgnoreCase("--rest")) {
                logger.info("Starting REST Interface...");
                core.getRestInterface().start();
            } else {
                logger.info("Starting Command Line Interface...");
                core.getCommandInterface().start();
            }

            logger.info("SentientNet started successfully.");

        } catch (Exception e) {
            ErrorController.getInstance().recordError("LAUNCHER_FAIL", "Failed to start SentientNet: " + e.getMessage());
            logger.log(Level.SEVERE, "Fatal error during startup", e);
            System.exit(1);
        }
    }

    private static void checkEnvironment() throws Exception {
        // Example: Check for Java version, memory, required permissions, etc.
        String javaVersion = System.getProperty("java.version");
        logger.info("Java Version: " + javaVersion);
        if (!javaVersion.startsWith("20")) {
            throw new Exception("Java 20 is required to run SentientNet.");
        }
        // Add more environment checks as needed
    }
}
