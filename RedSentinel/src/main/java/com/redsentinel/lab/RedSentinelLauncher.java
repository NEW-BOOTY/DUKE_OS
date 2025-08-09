/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.redsentinel.lab;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class RedSentinelLauncher {

    private static final Logger LOGGER = Logger.getLogger(RedSentinelLauncher.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("\n[ RedSentinel™ Simulation Modules ]");
                System.out.println("-----------------------------------");
                System.out.println("1. Simulate Persistence Technique");
                System.out.println("2. Simulate Privilege Escalation");
                System.out.println("3. Simulate Firewall Evasion");
                System.out.println("4. Simulate Encrypted C2 Handler");
                System.out.println("5. Simulate Log Tampering");
                System.out.println("6. Exit");
                System.out.print("\nSelect an option (1–6): ");

                String choice = null;
                if (scanner.hasNextLine()) {
                    choice = scanner.nextLine().trim();
                } else {
                    LOGGER.warning("No input detected. Exiting safely.");
                    break;
                }

                switch (choice) {
                    case "1" -> PersistenceSimulator.simulatePersistence();
                    case "2" -> PrivilegeEscalationSimulator.simulatePrivilegeEscalation();
                    case "3" -> FirewallEvasionSimulator.simulateFirewallEvasion();
                    case "4" -> C2CommandHandler.initiateCommandControl(scanner);
                    case "5" -> LogTamperingSimulator.simulateLogTampering();
                    case "6" -> {
                        System.out.println("Exiting RedSentinel™ Simulation.");
                        return;
                    }
                    default -> System.out.println("Invalid selection. Try again.");
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Critical failure in launcher runtime.", e);
        } finally {
            scanner.close();
        }
    }
}
