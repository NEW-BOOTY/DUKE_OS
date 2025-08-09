/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.self_architect;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SelfArchitect is the central orchestrator for autonomous
 * system self-modification, code evolution, and reinforcement learning.
 */
public final class SelfArchitect {

    private static final Logger logger = Logger.getLogger(SelfArchitect.class.getName());

    private final EvolutionEngine evolutionEngine;
    private final SymbolicLearner symbolicLearner;
    private final ScheduledExecutorService scheduler;

    private final List<Path> monitoredSourceFiles;
    private final long evolutionIntervalSeconds;

    private volatile boolean active;

    /**
     * Constructs SelfArchitect with dependencies and configuration.
     *
     * @param evolutionEngine AI-driven evolution engine instance.
     * @param symbolicLearner Symbolic learning engine instance.
     * @param monitoredSourceFiles Source files to monitor and evolve.
     * @param evolutionIntervalSeconds Interval between evolution cycles in seconds.
     */
    public SelfArchitect(EvolutionEngine evolutionEngine,
                         SymbolicLearner symbolicLearner,
                         List<Path> monitoredSourceFiles,
                         long evolutionIntervalSeconds) {

        if (evolutionEngine == null) {
            throw new IllegalArgumentException("EvolutionEngine cannot be null");
        }
        if (symbolicLearner == null) {
            throw new IllegalArgumentException("SymbolicLearner cannot be null");
        }
        if (monitoredSourceFiles == null || monitoredSourceFiles.isEmpty()) {
            throw new IllegalArgumentException("Monitored source files cannot be null or empty");
        }
        if (evolutionIntervalSeconds <= 0) {
            throw new IllegalArgumentException("Evolution interval must be positive");
        }

        this.evolutionEngine = evolutionEngine;
        this.symbolicLearner = symbolicLearner;
        this.monitoredSourceFiles = List.copyOf(monitoredSourceFiles);
        this.evolutionIntervalSeconds = evolutionIntervalSeconds;

        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "SelfArchitect-Scheduler");
            t.setDaemon(true);
            return t;
        });

        this.active = false;

        logger.info("SelfArchitect initialized. Monitoring " + monitoredSourceFiles.size() +
                " files with interval " + evolutionIntervalSeconds + " seconds.");
    }

    /**
     * Starts the autonomous evolution and learning cycles.
     */
    public synchronized void start() {
        if (active) {
            logger.warning("SelfArchitect already active.");
            return;
        }
        active = true;
        scheduler.scheduleWithFixedDelay(this::evolutionCycle, 0, evolutionIntervalSeconds, TimeUnit.SECONDS);
        logger.info("SelfArchitect started.");
    }

    /**
     * Stops the evolution and learning cycles gracefully.
     */
    public synchronized void stop() {
        if (!active) {
            logger.warning("SelfArchitect not active.");
            return;
        }
        active = false;
        scheduler.shutdownNow();
        logger.info("SelfArchitect stopped.");
    }

    private void evolutionCycle() {
        if (!active) {
            return;
        }
        try {
            logger.info("Starting evolution cycle.");
            // Generate new rules from symbolic learner
            List<AIRewriter.RewritingRule> newRules = symbolicLearner.generateRules();
            for (AIRewriter.RewritingRule rule : newRules) {
                evolutionEngine.addRule(rule);
            }

            // Perform evolution on monitored source files
            evolutionEngine.evolve(monitoredSourceFiles);

            // Learn from evolution feedback for next cycles
            symbolicLearner.learnFromFiles(monitoredSourceFiles);

            logger.info("Evolution cycle completed successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during evolution cycle", e);
        }
    }

    /**
     * Checks if the SelfArchitect is currently active.
     *
     * @return true if active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }
}
