/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package quantum;

import java.util.logging.Logger;

public final class QuantumState3Qubit {

    private static final Logger LOGGER = Logger.getLogger(QuantumState3Qubit.class.getName());

    private final double[] amplitudes;

    public QuantumState3Qubit(double[] amplitudes) throws Exception {
        if (amplitudes.length != 8) throw new Exception("3-qubit state must have 8 amplitudes.");
        this.amplitudes = amplitudes;
    }

    public double getProbability(int index) throws Exception {
        if (index < 0 || index >= amplitudes.length) throw new Exception("Index out of bounds");
        return amplitudes[index] * amplitudes[index];
    }

    public void printState() {
        for (int i = 0; i < amplitudes.length; i++) {
            LOGGER.info(String.format("|%3s⟩: %.6f", Integer.toBinaryString(i | 8).substring(1), amplitudes[i]));
        }
    }
}
