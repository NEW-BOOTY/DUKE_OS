/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.core.impl;

import net.sentient.core.interfaces.CommandInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of the CommandInterface for CLI-based interaction.
 */
public final class DefaultCommandInterface implements CommandInterface {

    private static final Logger logger = Logger.getLogger(DefaultCommandInterface.class.getName());

    @Override
    public void start() {
        logger.info("Command Interface started. Awaiting user input...");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;

            while (true) {
                System.out.print("Sentient> ");
                input = reader.readLine();

                if (input == null || input.equalsIgnoreCase("exit")) {
                    logger.info("Exiting Command Interface.");
                    break;
                }

                try {
                    handleCommand(input.trim());
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error executing command: " + input, e);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Command Interface crashed unexpectedly.", e);
        }
    }

    private void handleCommand(String command) throws Exception {
        switch (command.toLowerCase()) {
            case "status":
                System.out.println("SentientNet is online.");
                break;
            case "shutdown":
                System.out.println("Initiating system shutdown...");
                System.exit(0);
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }
}
