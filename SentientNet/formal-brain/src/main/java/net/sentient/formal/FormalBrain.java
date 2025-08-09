/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.formalbrain;

 import java.util.Objects;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 /**
  * Core reasoning engine that uses the axiomatic model
  * and the proof engine to perform formal verification tasks.
  */
 public final class FormalBrain {
 
     private static final Logger LOGGER = Logger.getLogger(FormalBrain.class.getName());
 
     private final AxiomaticModel axiomaticModel;
     private final ProofEngine proofEngine;
 
     /**
      * Constructs a FormalBrain with the given axiomatic model and proof engine.
      *
      * @param axiomaticModel non-null axiomatic model instance.
      * @param proofEngine    non-null proof engine instance.
      * @throws NullPointerException if either parameter is null.
      */
     public FormalBrain(AxiomaticModel axiomaticModel, ProofEngine proofEngine) {
         this.axiomaticModel = Objects.requireNonNull(axiomaticModel, "AxiomaticModel cannot be null.");
         this.proofEngine = Objects.requireNonNull(proofEngine, "ProofEngine cannot be null.");
         LOGGER.info("FormalBrain initialized with AxiomaticModel and ProofEngine.");
     }
 
     /**
      * Attempts to formally verify the given statement.
      *
      * @param statement The logical statement to verify.
      * @return true if statement is verified, false otherwise.
      * @throws IllegalArgumentException if statement is null or empty.
      */
     public boolean verifyStatement(String statement) {
         if (statement == null || statement.trim().isEmpty()) {
             throw new IllegalArgumentException("Statement to verify cannot be null or empty.");
         }
 
         LOGGER.info("Verifying statement: " + statement);
 
         try {
             boolean result = proofEngine.prove(statement, axiomaticModel);
             LOGGER.info("Verification result for statement '" + statement + "': " + result);
             return result;
         } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error during verification of statement: " + statement, e);
             return false;
         }
     }
 }
 