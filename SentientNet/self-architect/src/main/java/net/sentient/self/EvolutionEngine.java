/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.self_architect;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EvolutionEngine manages AI-driven evolution cycles, applying
 * symbolic rewriting rules to source files and tracking iteration states.
 */
public final class EvolutionEngine {

    private static final Logger logger = Logger.getLogger(EvolutionEngine.class.getName());

    private final List<AIRewriter.RewritingRule> evolutionRules;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    /**
     * Constructs an EvolutionEngine with the given initial evolution rules.
     * Uses thread-safe CopyOnWriteArrayList to allow dynamic updates.
     *
     * @param initialRules List of rewriting rules for evolution cycles.
     */
    public EvolutionEngine(List<AIRewriter.RewritingRule> initialRules) {
        if (initialRules == null || initialRules.isEmpty()) {
            throw new IllegalArgumentException("Initial rules cannot be null or empty");
        }
        this.evolutionRules = new CopyOnWriteArrayList<>(initialRules);
        logger.info("EvolutionEngine initialized with " + evolutionRules.size() + " rules.");
    }

    /**
     * Starts an evolution cycle applying rewriting rules to the given source files.
     *
     * @param sourceFiles List of source files to evolve.
     * @throws EvolutionException If an error occurs during evolution.
     */
    public void evolve(List<Path> sourceFiles) throws EvolutionException {
        if (sourceFiles == null || sourceFiles.isEmpty()) {
            throw new EvolutionException("Source files list cannot be null or empty");
        }

        if (!isRunning.compareAndSet(false, true)) {
            throw new EvolutionException("Evolution cycle already in progress");
        }

        logger.info("Starting evolution cycle on " + sourceFiles.size() + " files.");
        AIRewriter rewriter = new AIRewriter(evolutionRules);

        try {
            for (Path sourceFile : sourceFiles) {
                try {
                    rewriter.rewriteSource(sourceFile);
                } catch (AIRewriter.RewritingException e) {
                    logger.log(Level.WARNING, "Failed to rewrite source: " + sourceFile, e);
                }
            }
        } finally {
            isRunning.set(false);
            logger.info("Evolution cycle completed.");
        }
    }

    /**
     * Adds a new rewriting rule to the evolution rule set dynamically.
     *
     * @param rule The rewriting rule to add.
     */
    public void addRule(AIRewriter.RewritingRule rule) {
        if (rule == null) {
            throw new IllegalArgumentException("Rewriting rule cannot be null");
        }
        evolutionRules.add(rule);
        logger.info("Added new rewriting rule: " + rule.description());
    }

    /**
     * Removes a rewriting rule from the evolution rule set.
     *
     * @param rule The rewriting rule to remove.
     * @return true if the rule was removed, false otherwise.
     */
    public boolean removeRule(AIRewriter.RewritingRule rule) {
        if (rule == null) {
            return false;
        }
        boolean removed = evolutionRules.remove(rule);
        if (removed) {
            logger.info("Removed rewriting rule: " + rule.description());
        }
        return removed;
    }

    public static class EvolutionException extends Exception {
        public EvolutionException(String message) {
            super(message);
        }

        public EvolutionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
