/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.blockchain;

 import java.util.Objects;
 
 public final class IntegrityProof {
     private final int index;
     private final String hash;
     private final boolean valid;
 
     public IntegrityProof(int index, String hash, boolean valid) {
         this.index = index;
         this.hash = hash;
         this.valid = valid;
     }
 
     public boolean isValid() {
         return valid;
     }
 
     @Override
     public String toString() {
         return String.format("Block #%d [%s] -> %s", index, hash, valid ? "VALID" : "INVALID");
     }
 
     @Override
     public boolean equals(Object o) {
         if (!(o instanceof IntegrityProof)) return false;
         IntegrityProof p = (IntegrityProof) o;
         return index == p.index && hash.equals(p.hash) && valid == p.valid;
     }
 
     @Override
     public int hashCode() {
         return Objects.hash(index, hash, valid);
     }
 }
 