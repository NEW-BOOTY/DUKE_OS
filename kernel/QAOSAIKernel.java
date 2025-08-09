/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package kernel;

import ai.QuantumSecureAI;
import crypto.PostQuantumCryptography;
import network.QuantumSafeNetwork;
import quantum.QuantumFourierTransform;
import quantum.QuantumState3Qubit;

import java.util.Scanner;
import java.util.logging.Logger;

public final class QAOSAIKernel {

    private static final Logger LOGGER = Logger.getLogger(QAOSAIKernel.class.getName());

    public static void main(String[] args) {
        try {
            LOGGER.info("🔒 Initializing QAOSAI Kernel...");

            // Init secure components
            PostQuantumCryptography crypto = new PostQuantumCryptography();
            QuantumSecureAI ai = new QuantumSecureAI(crypto);
            QuantumSafeNetwork net = new QuantumSafeNetwork(7070);

            new Thread(() -> {
                try {
                    net.start();
                } catch (Exception e) {
                    LOGGER.severe("Network failed: " + e.getMessage());
                }
            }).start();

            // Interactive CLI
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter AI Query: ");
            String query = scanner.nextLine();

            String encrypted = ai.respondToQuery(query);
            System.out.println("Encrypted AI Response: " + encrypted);

            String decrypted = ai.decryptResponse(encrypted);
            System.out.println("Decrypted AI Response: " + decrypted);

            // Quantum visualization demo
            double[] qstate = {0.35, 0.1, 0.2, 0.1, 0.05, 0.05, 0.1, 0.05};
            QuantumState3Qubit state = new QuantumState3Qubit(qstate);
            state.printState();

        } catch (Exception e) {
            LOGGER.severe("Critical error: " + e.getMessage());
        }
    }
}
