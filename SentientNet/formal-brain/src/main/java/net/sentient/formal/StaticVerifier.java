/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.formalbrain;

 import java.io.IOException;
 import java.nio.file.Files;
 import java.nio.file.Path;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 /**
  * StaticVerifier performs static analysis and formal verification
  * of code artifacts, leveraging the FormalBrain engine.
  */
 public final class StaticVerifier {
 
     private static final Logger LOGGER = Logger.getLogger(StaticVerifier.class.getName());
 
     private final FormalBrain formalBrain;
 
     /**
      * Constructs a StaticVerifier with the provided FormalBrain instance.
      *
      * @param formalBrain non-null FormalBrain instance.
      */
     public StaticVerifier(FormalBrain formalBrain) {
         if (formalBrain == null) {
             throw new IllegalArgumentException("FormalBrain cannot be null.");
         }
         this.formalBrain = formalBrain;
         LOGGER.info("StaticVerifier initialized.");
     }
 
     /**
      * Verifies the source code at the given path using formal verification.
      *
      * @param sourcePath Path to the source code file.
      * @return true if verification passes, false otherwise.
      * @throws IOException if reading the file fails.
      */
     public boolean verifySourceCode(Path sourcePath) throws IOException {
         if (sourcePath == null) {
             throw new IllegalArgumentException("Source path cannot be null.");
         }
         if (!Files.exists(sourcePath)) {
             throw new IOException("Source file does not exist: " + sourcePath);
         }
         LOGGER.info("Starting static verification on source file: " + sourcePath);
 
         String sourceCode = Files.readString(sourcePath);
         if (sourceCode.trim().isEmpty()) {
             LOGGER.warning("Source file is empty: " + sourcePath);
             return false;
         }
 
         // For demo, we treat the entire source code as a single statement
         boolean result = formalBrain.verifyStatement(sourceCode);
 
         LOGGER.info("Static verification result for file '" + sourcePath + "': " + result);
         return result;
     }
 }
 