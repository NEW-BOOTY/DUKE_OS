/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.neuro_shield;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class NeuroShield {

    private static final Logger logger = Logger.getLogger(NeuroShield.class.getName());
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    
    private final SecureEnclave enclave;
    private final ThreatScanner threatScanner;
    private final QuantumKeys quantumKeys;

    public NeuroShield() throws NeuroShieldException {
        try {
            enclave = new SecureEnclave();
            threatScanner = new ThreatScanner();
            quantumKeys = new QuantumKeys();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Initialization failed: " + e.getMessage(), e);
            throw new NeuroShieldException("Failed to initialize NeuroShield components", e);
        }
    }

    public synchronized void initialize() throws NeuroShieldException {
        if (initialized.get()) {
            logger.warning("NeuroShield is already initialized.");
            return;
        }

        try {
            enclave.activateEnclave();
            quantumKeys.generatePostQuantumKeypair();
            threatScanner.initializeNeuralScanner();

            initialized.set(true);
            logger.info("NeuroShield successfully initialized and secured.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during NeuroShield initialization", e);
            throw new NeuroShieldException("Critical failure during initialization", e);
        }
    }

    public boolean verifyIntegrity(byte[] systemDigest) {
        try {
            boolean valid = enclave.verifySystemState(systemDigest);
            logger.info("System integrity check: " + (valid ? "PASSED" : "FAILED"));
            return valid;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Integrity verification error", e);
            return false;
        }
    }

    public void performLiveThreatScan() {
        try {
            logger.info("Starting live threat scan...");
            threatScanner.scanRuntimeEnvironment();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Live threat scan failed", e);
        }
    }

    public byte[] requestQuantumPublicKey() {
        try {
            return quantumKeys.getPublicKey();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Quantum key retrieval failed", e);
            return new byte[0];
        }
    }

    public void shutdown() {
        try {
            logger.info("Shutting down NeuroShield...");
            enclave.deactivateEnclave();
            initialized.set(false);
            logger.info("NeuroShield shutdown completed.");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Shutdown encountered issues", e);
        }
    }

    public boolean isInitialized() {
        return initialized.get();
    }

    public static class NeuroShieldException extends Exception {
        public NeuroShieldException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
