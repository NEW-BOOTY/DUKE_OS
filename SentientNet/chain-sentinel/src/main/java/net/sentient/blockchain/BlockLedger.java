/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.blockchain;

 import java.security.MessageDigest;
 import java.time.Instant;
 import java.util.Base64;
 
 public final class BlockLedger {
     private final int index;
     private final Instant timestamp;
     private final String data;
     private final String previousHash;
     private final String hash;
 
     private BlockLedger(int index, Instant timestamp, String data, String previousHash, String hash) {
         this.index = index;
         this.timestamp = timestamp;
         this.data = data;
         this.previousHash = previousHash;
         this.hash = hash;
     }
 
     public static BlockLedger createGenesisBlock() {
         return new BlockLedger(0, Instant.now(), "GENESIS", "0", hash("0GENESIS" + Instant.now()));
     }
 
     public static BlockLedger createNextBlock(BlockLedger previousBlock, String data) {
         int newIndex = previousBlock.index + 1;
         Instant now = Instant.now();
         String rawData = previousBlock.hash + data + now.toString();
         return new BlockLedger(newIndex, now, data, previousBlock.hash, hash(rawData));
     }
 
     private static String hash(String input) {
         try {
             MessageDigest digest = MessageDigest.getInstance("SHA-256");
             byte[] bytes = digest.digest(input.getBytes());
             return Base64.getEncoder().encodeToString(bytes);
         } catch (Exception e) {
             throw new RuntimeException("Hashing failed", e);
         }
     }
 
     public String getHash() { return hash; }
     public String getPreviousHash() { return previousHash; }
     public String getData() { return data; }
     public int getIndex() { return index; }
 
     @Override
     public String toString() {
         return String.format("[#%d | %s | %s]", index, timestamp, data);
     }
 }
 