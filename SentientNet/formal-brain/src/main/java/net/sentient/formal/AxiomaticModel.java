/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.formalbrain;

 import java.util.Collections;
 import java.util.HashSet;
 import java.util.Set;
 import java.util.logging.Logger;
 
 /**
  * Represents the axiomatic base for formal verification.
  * Defines axioms and inference rules used by the proof engine.
  */
 public final class AxiomaticModel {
 
     private static final Logger LOGGER = Logger.getLogger(AxiomaticModel.class.getName());
 
     private final Set<String> axioms;
     private final Set<String> inferenceRules;
 
     /**
      * Initializes an empty Axiomatic Model.
      */
     public AxiomaticModel() {
         this.axioms = new HashSet<>();
         this.inferenceRules = new HashSet<>();
         LOGGER.info("AxiomaticModel initialized empty.");
     }
 
     /**
      * Adds an axiom statement to the model.
      *
      * @param axiom The axiom to add, must be non-null, non-empty.
      * @throws IllegalArgumentException if axiom is null or empty.
      */
     public synchronized void addAxiom(String axiom) {
         if (axiom == null || axiom.trim().isEmpty()) {
             throw new IllegalArgumentException("Axiom cannot be null or empty.");
         }
         axioms.add(axiom);
         LOGGER.fine("Axiom added: " + axiom);
     }
 
     /**
      * Adds an inference rule to the model.
      *
      * @param rule The inference rule to add, must be non-null, non-empty.
      * @throws IllegalArgumentException if rule is null or empty.
      */
     public synchronized void addInferenceRule(String rule) {
         if (rule == null || rule.trim().isEmpty()) {
             throw new IllegalArgumentException("Inference rule cannot be null or empty.");
         }
         inferenceRules.add(rule);
         LOGGER.fine("Inference rule added: " + rule);
     }
 
     /**
      * Returns an unmodifiable view of the axioms.
      *
      * @return Set of axioms.
      */
     public synchronized Set<String> getAxioms() {
         return Collections.unmodifiableSet(axioms);
     }
 
     /**
      * Returns an unmodifiable view of the inference rules.
      *
      * @return Set of inference rules.
      */
     public synchronized Set<String> getInferenceRules() {
         return Collections.unmodifiableSet(inferenceRules);
     }
 }
 