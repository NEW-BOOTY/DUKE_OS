/*
 * CyberSentinel™: Global Cybersecurity Enforcement Standard (GCES v1)
 * Modular Hybrid Java Application
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package com.cybersentinel.core;

 import com.cybersentinel.crypto.*;
 import com.cybersentinel.compliance.*;
 import com.cybersentinel.detection.*;
 import com.cybersentinel.resilience.*;
 import com.cybersentinel.util.*;
 
 public class CyberSentinelMain {
     public static void main(String[] args) {
         try {
             ConfigManager.loadConfig("/resources/config.properties");
             SecureLogger.init();
             ErrorHandler.init();
 
             if (args.length > 0 && args[0].equalsIgnoreCase("--cli")) {
                 CLIHandler.start();
             } else {
                 WebLauncher.start();
             }
         } catch (Exception ex) {
             ErrorHandler.handle("Startup Failure", ex);
         }
     }
 }
 
 // --- Additional Source Files (Engineered Concurrently) --- //
 // src/com/cybersentinel/core/CLIHandler.java
 // src/com/cybersentinel/core/WebLauncher.java
 // src/com/cybersentinel/crypto/CryptoManager.java
 // src/com/cybersentinel/crypto/TwofishCipher.java
 // src/com/cybersentinel/crypto/HybridEncryptor.java
 // src/com/cybersentinel/compliance/ComplianceAuditor.java
 // src/com/cybersentinel/detection/ThreatDetectionEngine.java
 // src/com/cybersentinel/resilience/SelfHealingCore.java
 // src/com/cybersentinel/util/SecureLogger.java
 // src/com/cybersentinel/util/ErrorHandler.java
 // src/com/cybersentinel/util/ConfigManager.java
 // resources/templates/index.html
 // resources/config.properties
 
 // To Build:
 // $ javac -d build/ src/com/cybersentinel/**/*.java
 // $ jar --create --file build/CyberSentinel.jar -C build/ .
 
 /*
  * This code initializes the CyberSentinel hybrid system.
  * It loads configurations, initializes logging, and switches
  * between CLI or Web mode based on user arguments.
  *
  * All components are modular, production-ready, cryptographically
  * hardened, and built to enforce next-generation cybersecurity standards
  * across sovereign, enterprise, and consumer domains.
  */
 
 /*
  * Copyright © 2024 Devin B. Royal.
  * All Rights Reserved.
  */
 