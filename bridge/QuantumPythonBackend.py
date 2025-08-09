#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Copyright © 2025 Devin B. Royal.
All Rights Reserved.

This backend module is invoked by QAOSAI's Java bridge (ProcessBuilder).
It performs a basic Quantum Fourier Transform (QFT)-like simulation on
an incoming quantum state vector, received via stdin in JSON format.
"""

import sys
import json
import math
import traceback


def simulate_quantum_operation(state_vector):
    """Simulates a Fourier-like quantum transformation."""
    n = len(state_vector)
    transformed = []

    for k in range(n):
        real = 0.0
        imag = 0.0
        for j in range(n):
            angle = 2 * math.pi * j * k / n
            real += state_vector[j] * math.cos(angle)
            imag -= state_vector[j] * math.sin(angle)
        magnitude = math.sqrt(real ** 2 + imag ** 2)
        transformed.append(magnitude)

    return transformed


def main():
    try:
        raw_input = sys.stdin.read()
        if not raw_input.strip():
            raise ValueError("No input received.")

        data = json.loads(raw_input)

        if "state" not in data or not isinstance(data["state"], list):
            raise ValueError("Input JSON must contain key 'state' with a list of numbers.")

        state_vector = data["state"]
        result = simulate_quantum_operation(state_vector)

        output = {
            "success": True,
            "inputState": state_vector,
            "transformedState": result
        }

        print(json.dumps(output, indent=2))

    except Exception as e:
        error_trace = traceback.format_exc()
        sys.stderr.write(f"QuantumPythonBackend ERROR: {str(e)}\n{error_trace}")
        print(json.dumps({
            "success": False,
            "error": str(e)
        }))


if __name__ == "__main__":
    main()
