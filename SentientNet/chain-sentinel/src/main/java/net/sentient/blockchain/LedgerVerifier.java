/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.blockchain;

 import java.util.ArrayList;
 import java.util.List;
 
 public final class LedgerVerifier {
 
     public static boolean verify(List<BlockLedger> chain) {
         if (chain == null || chain.isEmpty()) return false;
 
         for (int i = 1; i < chain.size(); i++) {
             BlockLedger prev = chain.get(i - 1);
             BlockLedger curr = chain.get(i);
 
             if (!curr.getPreviousHash().equals(prev.getHash())) {
                 return false;
             }
         }
         return true;
     }
 
     public static List<IntegrityProof> generateProof(List<BlockLedger> chain) {
         List<IntegrityProof> proofs = new ArrayList<>();
         for (int i = 0; i < chain.size(); i++) {
             BlockLedger block = chain.get(i);
             boolean valid = (i == 0 || block.getPreviousHash().equals(chain.get(i - 1).getHash()));
             proofs.add(new IntegrityProof(block.getIndex(), block.getHash(), valid));
         }
         return proofs;
     }
 }
 