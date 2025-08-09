/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

 package com.cybersentinel.core;

 import com.cybersentinel.crypto.HybridEncryptor;
 import com.cybersentinel.detection.ThreatDetectionEngine;
 import com.cybersentinel.compliance.ComplianceAuditor;
 import com.cybersentinel.util.SecureLogger;
 import com.cybersentinel.util.ErrorHandler;
 
 import java.util.Scanner;
 
 public class CLIHandler {
 
     public static void start() {
         Scanner scanner = new Scanner(System.in);
         SecureLogger.log("CyberSentinel CLI initialized.");
 
         while (true) {
             System.out.println("\n[CyberSentinel CLI]");
             System.out.println("1. Run Threat Scan");
             System.out.println("2. Perform Compliance Audit");
             System.out.println("3. Encrypt Data");
             System.out.println("4. Exit");
             System.out.print("Choose an option: ");
 
             String input = scanner.nextLine();
 
             try {
                 switch (input.trim()) {
                     case "1":
                         ThreatDetectionEngine.scan();
                         break;
                     case "2":
                         ComplianceAuditor.audit();
                         break;
                     case "3":
                         System.out.print("Enter data to encrypt: ");
                         String data = scanner.nextLine();
                         String encrypted = HybridEncryptor.encrypt(data);
                         System.out.println("Encrypted Output: " + encrypted);
                         break;
                     case "4":
                         SecureLogger.log("Exiting CLI.");
                         return;
                     default:
                         System.out.println("Invalid selection.");
                         break;
                 }
             } catch (Exception e) {
                 ErrorHandler.handle("CLI Operation Failed", e);
             }
         }
     }
 }
 