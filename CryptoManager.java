/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

 package com.cybersentinel.crypto;

 import com.cybersentinel.util.SecureLogger;
 import com.cybersentinel.util.ErrorHandler;
 
 import javax.crypto.Cipher;
 import javax.crypto.KeyGenerator;
 import javax.crypto.SecretKey;
 import java.security.KeyPair;
 import java.security.KeyPairGenerator;
 import java.security.PrivateKey;
 import java.security.PublicKey;
 import java.util.Base64;
 import java.util.concurrent.ConcurrentHashMap;
 
 public class CryptoManager {
 
     private static final ConcurrentHashMap<String, SecretKey> sessionKeys = new ConcurrentHashMap<>();
     private static PublicKey serverPublicKey;
     private static PrivateKey serverPrivateKey;
 
     public static void initialize() {
         try {
             KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA"); // Replace with X25519 when implemented
             keyPairGen.initialize(2048);
             KeyPair keyPair = keyPairGen.generateKeyPair();
             serverPublicKey = keyPair.getPublic();
             serverPrivateKey = keyPair.getPrivate();
 
             SecureLogger.log("CryptoManager initialized with new key pair.");
         } catch (Exception e) {
             ErrorHandler.handle("Failed to initialize CryptoManager", e);
         }
     }
 
     public static String encryptWithSessionKey(String plaintext, String sessionId) {
         try {
             SecretKey key = sessionKeys.computeIfAbsent(sessionId, id -> generateAESKey());
             Cipher cipher = Cipher.getInstance("AES");
             cipher.init(Cipher.ENCRYPT_MODE, key);
             byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
 
             return Base64.getEncoder().encodeToString(encryptedBytes);
         } catch (Exception e) {
             ErrorHandler.handle("Encryption failed", e);
             return null;
         }
     }
 
     public static String decryptWithSessionKey(String encryptedBase64, String sessionId) {
         try {
             SecretKey key = sessionKeys.get(sessionId);
             if (key == null) {
                 throw new IllegalArgumentException("Session key not found.");
             }
 
             Cipher cipher = Cipher.getInstance("AES");
             cipher.init(Cipher.DECRYPT_MODE, key);
             byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedBase64));
 
             return new String(decryptedBytes);
         } catch (Exception e) {
             ErrorHandler.handle("Decryption failed", e);
             return null;
         }
     }
 
     public static PublicKey getPublicKey() {
         return serverPublicKey;
     }
 
     public static PrivateKey getPrivateKey() {
         return serverPrivateKey;
     }
 
     private static SecretKey generateAESKey() {
         try {
             KeyGenerator keyGen = KeyGenerator.getInstance("AES");
             keyGen.init(256);
             return keyGen.generateKey();
         } catch (Exception e) {
             ErrorHandler.handle("Failed to generate AES key", e);
             return null;
         }
     }
 }
 