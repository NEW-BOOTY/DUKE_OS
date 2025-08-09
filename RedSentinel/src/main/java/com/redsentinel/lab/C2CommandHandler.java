/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package com.redsentinel.lab;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class C2CommandHandler {

    private static final Logger LOGGER = Logger.getLogger(C2CommandHandler.class.getName());
    private static final int GCM_TAG_LENGTH = 128;
    private static final int GCM_IV_LENGTH = 12;

    private C2CommandHandler() {
        // Prevent instantiation
    }

    public static void initiateCommandControl(Scanner scanner) {
        LOGGER.info("[RedSentinel™] C2CommandHandler simulating secure C2 channel...");

        try {
            System.out.print("Enter simulated C2 command to encrypt: ");
            if (!scanner.hasNextLine()) {
                LOGGER.warning("No input provided for C2 command. Returning to main menu.");
                return;
            }

            String command = scanner.nextLine().trim();
            if (command.isEmpty()) {
                LOGGER.warning("Empty C2 command received. Aborting encryption.");
                return;
            }

            SecretKey secretKey = generateSymmetricKey();
            byte[] iv = generateIV();

            String encrypted = encryptCommand(command, secretKey, iv);
            String decrypted = decryptCommand(encrypted, secretKey, iv);

            LOGGER.info("Encrypted Command (Base64): " + encrypted);
            LOGGER.info("Decrypted Command (for verification): " + decrypted);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "C2 simulation encountered a critical error.", e);
        }
    }

    private static SecretKey generateSymmetricKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES-256
        return keyGen.generateKey();
    }

    private static byte[] generateIV() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    private static String encryptCommand(String command, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);
        byte[] encrypted = cipher.doFinal(command.getBytes(StandardCharsets.UTF_8));
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    private static String decryptCommand(String encryptedBase64, SecretKey key, byte[] originalIV) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedBase64);
        byte[] iv = new byte[GCM_IV_LENGTH];
        byte[] encrypted = new byte[combined.length - GCM_IV_LENGTH];
        System.arraycopy(combined, 0, iv, 0, GCM_IV_LENGTH);
        System.arraycopy(combined, GCM_IV_LENGTH, encrypted, 0, encrypted.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}

/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
