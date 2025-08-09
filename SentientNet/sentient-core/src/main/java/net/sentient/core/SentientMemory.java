/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages memory storage and retrieval for SentientNet's state.
 */
public final class SentientMemory {

    private static final Logger logger = Logger.getLogger(SentientMemory.class.getName());
    private final Map<String, Object> memoryStore;

    /**
     * Constructs an empty SentientMemory instance.
     */
    public SentientMemory() {
        this.memoryStore = new ConcurrentHashMap<>();
    }

    /**
     * Initializes memory subsystem.
     */
    public void initialize() {
        logger.info("Initializing SentientMemory subsystem...");
        // Initialize or restore persistent state if required
    }

    /**
     * Retrieves an object from memory by key.
     * 
     * @param key the memory key
     * @return the stored object or null if not present
     */
    public Object get(String key) {
        return memoryStore.get(key);
    }

    /**
     * Stores an object in memory.
     * 
     * @param key the memory key
     * @param value the object to store
     */
    public void put(String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Memory key cannot be null.");
        }
        memoryStore.put(key, value);
    }

    /**
     * Removes an object from memory.
     * 
     * @param key the memory key
     */
    public void remove(String key) {
        memoryStore.remove(key);
    }

    /**
     * Gracefully shuts down the memory subsystem.
     */
    public void shutdown() {
        logger.info("Shutting down SentientMemory...");
        memoryStore.clear();
    }
}
