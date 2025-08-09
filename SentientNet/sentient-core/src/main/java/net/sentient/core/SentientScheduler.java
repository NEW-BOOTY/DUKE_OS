/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Schedules and manages periodic tasks for SentientNet.
 */
public final class SentientScheduler {

    private static final Logger logger = Logger.getLogger(SentientScheduler.class.getName());
    private ScheduledExecutorService executor;

    /**
     * Initializes the scheduler subsystem.
     */
    public void initialize() {
        logger.info("Initializing SentientScheduler...");
        executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Starts periodic scheduling of internal tasks.
     */
    public void startScheduling() {
        logger.info("Starting scheduled tasks in SentientScheduler...");

        // Example: Schedule a heartbeat task every 1 second
        executor.scheduleAtFixedRate(() -> {
            try {
                heartbeat();
            } catch (Exception e) {
                logger.log(Level.WARNING, "Exception in scheduled heartbeat task", e);
            }
        }, 0, 1, TimeUnit.SECONDS);

        // Other scheduled tasks can be added here
    }

    private void heartbeat() {
        logger.fine("SentientScheduler heartbeat executed.");
        // Implement heartbeat or health-check logic here
    }

    /**
     * Stops all scheduled tasks and shuts down the scheduler.
     */
    public void shutdown() {
        logger.info("Shutting down SentientScheduler...");
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Interrupted during SentientScheduler shutdown.", e);
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
