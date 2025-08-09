/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.self_architect;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AIRewriter autonomously rewrites Java source code files using
 * mutation-safe symbolic rewriting patterns and adaptive AI feedback.
 */
public final class AIRewriter {

    private static final Logger logger = Logger.getLogger(AIRewriter.class.getName());

    private final List<RewritingRule> rules;

    public AIRewriter(List<RewritingRule> rewritingRules) {
        if (rewritingRules == null || rewritingRules.isEmpty()) {
            throw new IllegalArgumentException("Rewriting rules must not be null or empty");
        }
        this.rules = List.copyOf(rewritingRules);
        logger.info("AIRewriter initialized with " + rules.size() + " rules.");
    }

    /**
     * Performs in-place symbolic rewriting of the given Java source file.
     *
     * @param sourceFile Path to a .java source file.
     * @throws RewritingException if I/O fails or mutation fails.
     */
    public void rewriteSource(Path sourceFile) throws RewritingException {
        if (!Files.exists(sourceFile) || !sourceFile.toString().endsWith(".java")) {
            throw new RewritingException("Invalid Java source file: " + sourceFile);
        }

        try {
            String originalCode = Files.readString(sourceFile);
            String transformedCode = applyRules(originalCode);
            Files.writeString(sourceFile, transformedCode);
            logger.info("Source rewritten successfully: " + sourceFile.getFileName());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "I/O failure during source rewrite", e);
            throw new RewritingException("Rewrite failed due to I/O error", e);
        }
    }

    private String applyRules(String sourceCode) {
        String result = sourceCode;
        for (RewritingRule rule : rules) {
            Matcher matcher = rule.pattern.matcher(result);
            if (matcher.find()) {
                logger.info("Applying rewrite rule: " + rule.description);
                result = matcher.replaceAll(rule.replacement);
            }
        }
        return result;
    }

    public record RewritingRule(Pattern pattern, String replacement, String description) { }

    public static class RewritingException extends Exception {
        public RewritingException(String message) {
            super(message);
        }

        public RewritingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
