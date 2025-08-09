/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.core;

import net.sentient.interfaces.RestInterface;
import net.sentient.interfaces.CommandInterface;
import net.sentient.runtime.SentientMemory;
import net.sentient.runtime.SentientScheduler;
import net.sentient.runtime.DefaultRestInterface;
import net.sentient.runtime.DefaultCommandInterface;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Core entry point and orchestrator for SentientNet's main runtime engine.
 */
public final class SentientCore {

    private static final Logger logger = Logger.getLogger(SentientCore.class.getName());
    private static final SentientCore INSTANCE = new SentientCore();

    private final SentientMemory memory;
    private final SentientScheduler scheduler;
    private final RestInterface restInterface;
    private final CommandInterface commandInterface;

    /**
     * Private constructor for singleton pattern.
     */
    private SentientCore() {
        this.memory = new SentientMemory();
        this.scheduler = new SentientScheduler();
        this.restInterface = new DefaultRestInterface();           // Replace with actual implementation
        this.commandInterface = new DefaultCommandInterface();     // Replace with actual implementation
    }

    /**
     * Gets the singleton instance of SentientCore.
     * 
     * @return SentientCore instance
     */
    public static SentientCore getInstance() {
        return INSTANCE;
    }

    /**
     * Initializes the core runtime components.
     */
    public void initialize() {
        try {
            logger.info("Initializing SentientCore...");
            memory.initialize();
            scheduler.initialize();
            logger.info("SentientCore initialized successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize SentientCore.", e);
            throw new IllegalStateException("Initialization failure", e);
        }
    }

    /**
     * Starts the core runtime loop.
     */
    public void start() {
        logger.info("Starting SentientCore runtime loop.");
        try {
            scheduler.startScheduling();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Runtime failure in SentientCore.", e);
            shutdown();
            throw new RuntimeException("SentientCore runtime failure", e);
        }
    }

    /**
     * Gracefully shuts down all subsystems.
     */
    public void shutdown() {
        logger.info("Shutting down SentientCore...");
        try {
            scheduler.shutdown();
            memory.shutdown();
            logger.info("SentientCore shutdown complete.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during SentientCore shutdown.", e);
        }
    }

    /**
     * Accessor for SentientMemory.
     * 
     * @return SentientMemory instance
     */
    public SentientMemory getMemory() {
        return memory;
    }

    /**
     * Accessor for SentientScheduler.
     * 
     * @return SentientScheduler instance
     */
    public SentientScheduler getScheduler() {
        return scheduler;
    }

    /**
     * Accessor for REST interface.
     *
     * @return RestInterface implementation
     */
    public RestInterface getRestInterface() {
        return restInterface;
    }

    /**
     * Accessor for CLI interface.
     *
     * @return CommandInterface implementation
     */
    public CommandInterface getCommandInterface() {
        return commandInterface;
    }
}
