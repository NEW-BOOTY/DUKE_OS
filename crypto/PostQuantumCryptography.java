/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Base64;
import java.util.logging.Logger;

public final class PostQuantumCryptography {

    private static final Logger LOGGER = Logger.getLogger(PostQuantumCryptography.class.getName());

    private SecretKey symmetricKey;

    public PostQuantumCryptography() throws Exception {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // AES-256
            this.symmetricKey = keyGen.generateKey();
        } catch (Exception e) {
            LOGGER.severe("Failed to initialize symmetric key: " + e.getMessage());
            throw new Exception("Secure key initialization failed", e);
        }
    }

    public String encrypt(String data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            LOGGER.severe("Encryption error: " + e.getMessage());
            throw new Exception("Encryption failure", e);
        }
    }

    public String decrypt(String base64Encrypted) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, symmetricKey);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(base64Encrypted));
            return new String(decrypted);
        } catch (Exception e) {
            LOGGER.severe("Decryption error: " + e.getMessage());
            throw new Exception("Decryption failure", e);
        }
    }

    public SecretKey getSymmetricKey() {
        return symmetricKey;
    }
}



