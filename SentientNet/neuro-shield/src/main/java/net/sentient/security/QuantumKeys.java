/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.neuro_shield;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.interfaces.KEMGenerator;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

public final class QuantumKeys {

    private static final Logger logger = Logger.getLogger(QuantumKeys.class.getName());

    private KeyPair keyPair;
    private boolean keyGenerated = false;

    static {
        // Ensure BouncyCastle PQC is registered once globally
        if (Security.getProvider(BouncyCastlePQCProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastlePQCProvider());
        }
    }

    public synchronized void generatePostQuantumKeypair() throws QuantumKeyException {
        try {
            logger.info("Generating Kyber post-quantum keypair...");
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("Kyber", BouncyCastlePQCProvider.PROVIDER_NAME);
            kpg.initialize(KyberParameterSpec.kyber768);
            this.keyPair = kpg.generateKeyPair();
            this.keyGenerated = true;
            logger.info("Post-quantum keypair generated successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to generate quantum-safe keys", e);
            throw new QuantumKeyException("Kyber keypair generation failed", e);
        }
    }

    public byte[] getPublicKey() throws QuantumKeyException {
        if (!keyGenerated || keyPair == null) {
            throw new QuantumKeyException("Quantum keys not generated yet");
        }

        try {
            PublicKey publicKey = keyPair.getPublic();
            return publicKey.getEncoded();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to retrieve public key", e);
            throw new QuantumKeyException("Error accessing public key", e);
        }
    }

    public byte[] getPrivateKey() throws QuantumKeyException {
        if (!keyGenerated || keyPair == null) {
            throw new QuantumKeyException("Quantum keys not generated yet");
        }

        try {
            PrivateKey privateKey = keyPair.getPrivate();
            return privateKey.getEncoded();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to retrieve private key", e);
            throw new QuantumKeyException("Error accessing private key", e);
        }
    }

    public boolean isKeyGenerated() {
        return keyGenerated;
    }

    public static class QuantumKeyException extends Exception {
        public QuantumKeyException(String message) {
            super(message);
        }

        public QuantumKeyException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
