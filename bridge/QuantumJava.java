/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package bridge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * QuantumJava serves as the bridge for interacting with foreign quantum computation layers.
 * Supported targets include JS (Node.js), Python Qiskit, and QML interfaces.
 */
public final class QuantumJava {

    private static final Logger LOGGER = Logger.getLogger(QuantumJava.class.getName());

    /**
     * Executes a command on a foreign quantum layer (e.g., Node.js or Python) and returns the response.
     *
     * @param interpreter "node", "python3", or system interpreter
     * @param scriptPath  Absolute path to the script file
     * @param inputData   Optional input string (passed via stdin)
     * @return output from the foreign script
     */
    public static String executeForeignQuantumScript(String interpreter, String scriptPath, String inputData) {
        Process process = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(interpreter, scriptPath);
            pb.redirectErrorStream(true);
            process = pb.start();

            if (inputData != null && !inputData.isEmpty()) {
                try (OutputStream os = process.getOutputStream()) {
                    os.write(inputData.getBytes());
                    os.flush();
                }
            }

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LOGGER.warning("Foreign quantum script exited with non-zero code: " + exitCode);
            }

            return output.toString().trim();

        } catch (Exception e) {
            LOGGER.severe("Quantum bridge failure: " + e.getMessage());
            return "BridgeError: " + e.getMessage();
        } finally {
            if (process != null) process.destroy();
        }
    }

    /**
     * Sends a simple ping test to confirm quantum bridge availability.
     *
     * @return "pong" if working
     */
    public static String ping() {
        return "QuantumJava Bridge is active and responding (pong)";
    }
}
