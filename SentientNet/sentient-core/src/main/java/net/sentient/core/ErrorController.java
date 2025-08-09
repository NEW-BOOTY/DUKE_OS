/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.*;

/**
 * ErrorController is a singleton responsible for capturing, logging,
 * and persisting critical and non-critical runtime errors across the SentientNet system.
 */
public final class ErrorController {

    private static final Logger logger = Logger.getLogger(ErrorController.class.getName());
    private static final String LOG_DIRECTORY = "logs";
    private static final String LOG_FILE_PREFIX = "error-log-";
    private static final ConcurrentLinkedQueue<String> errorQueue = new ConcurrentLinkedQueue<>();

    private static final ErrorController instance = new ErrorController();

    private final Path logFilePath;

    /**
     * Private constructor to initialize error logging system.
     */
    private ErrorController() {
        try {
            Files.createDirectories(Paths.get(LOG_DIRECTORY));
            String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            logFilePath = Paths.get(LOG_DIRECTORY, LOG_FILE_PREFIX + timestamp + ".log");

            FileHandler handler = new FileHandler(logFilePath.toString(), true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            logger.setUseParentHandlers(false);
            logger.info("ErrorController initialized. Logging to: " + logFilePath);

        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize ErrorController logging system", e);
        }
    }

    /**
     * Provides the global instance of the ErrorController.
     *
     * @return ErrorController instance
     */
    public static ErrorController getInstance() {
        return instance;
    }

    /**
     * Records and logs an error entry.
     *
     * @param code    Unique error code
     * @param message Descriptive message
     */
    public void recordError(String code, String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        String formatted = String.format("[%s] ERROR_CODE=%s: %s", timestamp, code, message);
        errorQueue.add(formatted);
        logger.severe(formatted);
    }

    /**
     * Records an exception with stack trace.
     *
     * @param code Unique error code
     * @param ex   Throwable exception
     */
    public void recordException(String code, Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        recordError(code, sw.toString());
    }

    /**
     * Flushes any queued error messages into the log file.
     */
    public void flushQueue() {
        while (!errorQueue.isEmpty()) {
            String error = errorQueue.poll();
            if (error != null) {
                logger.severe(error);
            }
        }
    }

    /**
     * Returns the path to the error log file.
     *
     * @return Path to log file
     */
    public Path getLogFilePath() {
        return logFilePath;
    }
}
