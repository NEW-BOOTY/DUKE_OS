<!--
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
-->

# SentientNet :: Formal Verification of ChainSentinel

## 1. Objective

Formal verification ensures that the ChainSentinel ledger:
- Is tamper-proof under all permutations of execution.
- Preserves logical correctness during fault injection.
- Aligns with deterministic runtime semantics.

## 2. Specification

Let:
- `B_n` = Block at index n
- `H(x)` = SHA-256 hash of input x
- `D_n` = Data payload of block n
- `T_n` = Timestamp of block n

### Correctness Property

```math
∀n > 0: B_n.previousHash == H(B_{n-1}.previousHash + D_{n-1} + T_{n-1})
