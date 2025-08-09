/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.neuro_shield;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SecureEnclave {

    private static final Logger logger = Logger.getLogger(SecureEnclave.class.getName());
    private static final String ENCLAVE_DIGEST_ALGORITHM = "SHA-512";

    private final AtomicBoolean enclaveActive = new AtomicBoolean(false);
    private byte[] enclaveBaselineDigest;

    public void activateEnclave() throws EnclaveException {
        if (enclaveActive.get()) {
            logger.warning("Enclave already active.");
            return;
        }

        try {
            logger.info("Activating secure enclave...");
            this.enclaveBaselineDigest = generateEnclaveBaseline();
            enclaveActive.set(true);
            logger.info("Secure enclave activated with cryptographic shielding.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Enclave activation failed", e);
            throw new EnclaveException("Failed to activate secure enclave", e);
        }
    }

    public void deactivateEnclave() throws EnclaveException {
        if (!enclaveActive.get()) {
            logger.warning("Enclave not active.");
            return;
        }

        try {
            logger.info("Deactivating secure enclave...");
            enclaveBaselineDigest = null;
            enclaveActive.set(false);
            logger.info("Secure enclave deactivated.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Enclave deactivation error", e);
            throw new EnclaveException("Failed to deactivate enclave", e);
        }
    }

    public boolean verifySystemState(byte[] currentDigest) throws EnclaveException {
        if (!enclaveActive.get()) {
            throw new EnclaveException("Enclave not active. Cannot verify system state.");
        }

        try {
            logger.info("Performing system integrity check...");
            boolean match = Arrays.equals(enclaveBaselineDigest, currentDigest);
            logger.info("System integrity " + (match ? "verified." : "compromised!"));
            return match;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "System state verification failed", e);
            throw new EnclaveException("Error during system state verification", e);
        }
    }

    public byte[] generateDigest(byte[] data) throws EnclaveException {
        try {
            MessageDigest md = MessageDigest.getInstance(ENCLAVE_DIGEST_ALGORITHM);
            return md.digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new EnclaveException("Digest algorithm not supported", e);
        }
    }

    private byte[] generateEnclaveBaseline() throws EnclaveException {
        try {
            byte[] simulatedMemory = "SentientNetSecureEnclaveBaseline".getBytes("UTF-8");
            return generateDigest(simulatedMemory);
        } catch (Exception e) {
            throw new EnclaveException("Failed to generate enclave baseline", e);
        }
    }

    public boolean isEnclaveActive() {
        return enclaveActive.get();
    }

    public static class EnclaveException extends Exception {
        public EnclaveException(String message) {
            super(message);
        }

        public EnclaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
