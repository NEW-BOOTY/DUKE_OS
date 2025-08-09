/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.core.impl;

import net.sentient.core.interfaces.RestInterface;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of the RestInterface using Jetty.
 */
public final class DefaultRestInterface implements RestInterface {

    private static final Logger logger = Logger.getLogger(DefaultRestInterface.class.getName());

    private Server server;

    @Override
    public void start() throws Exception {
        logger.info("Starting Jetty REST server on port 8080...");

        server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath("/");
        server.setHandler(handler);

        try {
            server.start();
            logger.info("REST server started successfully on http://localhost:8080");
            server.join(); // Block until shutdown
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to start REST server.", e);
            throw e;
        }
    }
}
