/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package netconfig;

import netconfig.proxy.AutoProxyConfigurator;

import java.util.logging.*;

public final class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsageAndExit();
        }

        String pacUrl = null;
        String staticProxy = null;
        String targetUrl = "http://example.com"; // Default fallback

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--pac":
                    if (i + 1 < args.length) {
                        pacUrl = args[++i];
                    } else {
                        System.err.println("Missing value after --pac");
                        System.exit(1);
                    }
                    break;

                case "--proxy":
                    if (i + 1 < args.length) {
                        staticProxy = args[++i];
                    } else {
                        System.err.println("Missing value after --proxy");
                        System.exit(1);
                    }
                    break;

                case "--target":
                    if (i + 1 < args.length) {
                        targetUrl = args[++i];
                    } else {
                        System.err.println("Missing value after --target");
                        System.exit(1);
                    }
                    break;

                default:
                    System.err.println("Unknown argument: " + args[i]);
                    printUsageAndExit();
            }
        }

        if (pacUrl != null && staticProxy != null) {
            LOGGER.warning("Both PAC and static proxy provided. PAC takes precedence.");
        }

        String configSource = pacUrl != null ? pacUrl : staticProxy;
        if (configSource == null) {
            LOGGER.severe("No PAC or static proxy specified.");
            printUsageAndExit();
        }

        try {
            AutoProxyConfigurator.applyProxyConfiguration(configSource, targetUrl);
            LOGGER.info("Proxy configuration complete.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Unexpected error in proxy configuration", ex);
            System.exit(2);
        }
    }

    private static void printUsageAndExit() {
        System.out.println("Usage:");
        System.out.println("  java -cp your-jar.jar netconfig.Main --pac <pac_url> [--target <url>]");
        System.out.println("  java -cp your-jar.jar netconfig.Main --proxy <host:port> [--target <url>]");
        System.exit(0);
    }
}
