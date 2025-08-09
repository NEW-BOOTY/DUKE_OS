/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.neuro_shield;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class ThreatScanner {

    private static final Logger logger = Logger.getLogger(ThreatScanner.class.getName());

    private final List<Pattern> threatSignatures = new CopyOnWriteArrayList<>();
    private final List<String> threatLog = new CopyOnWriteArrayList<>();

    public ThreatScanner() {
        preloadThreatPatterns();
    }

    private void preloadThreatPatterns() {
        logger.info("Loading quantum-aware heuristic threat signatures...");
        threatSignatures.add(Pattern.compile("(?i)exploit"));
        threatSignatures.add(Pattern.compile("(?i)malware"));
        threatSignatures.add(Pattern.compile("(?i)keylogger"));
        threatSignatures.add(Pattern.compile("(?i)unauthorized access"));
        threatSignatures.add(Pattern.compile("(?i)buffer overflow"));
        threatSignatures.add(Pattern.compile("(?i)side-channel"));
        threatSignatures.add(Pattern.compile("(?i)quantum tunnel"));
        logger.info("Threat signature heuristics loaded.");
    }

    public void scanDirectoryForThreats(File dir) throws ThreatScanException {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            throw new ThreatScanException("Invalid scan target directory: " + dir);
        }

        try {
            Files.walk(dir.toPath())
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        List<String> lines = Files.readAllLines(path);
                        for (String line : lines) {
                            for (Pattern pattern : threatSignatures) {
                                if (pattern.matcher(line).find()) {
                                    String threat = String.format("Threat match in [%s]: %s", path.getFileName(), pattern.pattern());
                                    logger.warning(threat);
                                    threatLog.add(threat);
                                    break;
                                }
                            }
                        }
                    } catch (IOException e) {
                        logger.log(Level.WARNING, "Error reading file: " + path.getFileName(), e);
                    }
                });
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Threat scan failed", e);
            throw new ThreatScanException("Directory scan failed", e);
        }
    }

    public List<String> getThreatLog() {
        return List.copyOf(threatLog);
    }

    public static class ThreatScanException extends Exception {
        public ThreatScanException(String message) {
            super(message);
        }

        public ThreatScanException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
