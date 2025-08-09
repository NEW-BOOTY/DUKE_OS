/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package quantum;

import java.util.Arrays;
import java.util.logging.Logger;

public final class QuantumFourierTransform {

    private static final Logger LOGGER = Logger.getLogger(QuantumFourierTransform.class.getName());

    public static Complex[] apply(Complex[] input) throws Exception {
        try {
            int n = input.length;
            Complex[] output = new Complex[n];
            for (int k = 0; k < n; k++) {
                output[k] = new Complex(0, 0);
                for (int j = 0; j < n; j++) {
                    double angle = -2 * Math.PI * k * j / n;
                    Complex phase = new Complex(Math.cos(angle), Math.sin(angle));
                    output[k] = output[k].add(input[j].multiply(phase));
                }
            }
            return output;
        } catch (Exception e) {
            LOGGER.severe("QFT computation failed: " + e.getMessage());
            throw new Exception("Quantum Fourier Transform failed", e);
        }
    }

    public static void debugPrint(Complex[] data) {
        Arrays.stream(data).forEach(d -> System.out.println(d));
    }

    // Simple Complex class
    public static final class Complex {
        private final double real;
        private final double imag;

        public Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }

        public Complex add(Complex other) {
            return new Complex(this.real + other.real, this.imag + other.imag);
        }

        public Complex multiply(Complex other) {
            return new Complex(
                this.real * other.real - this.imag * other.imag,
                this.real * other.imag + this.imag * other.real
            );
        }

        @Override
        public String toString() {
            return String.format("%.4f + %.4fi", real, imag);
        }
    }
}
