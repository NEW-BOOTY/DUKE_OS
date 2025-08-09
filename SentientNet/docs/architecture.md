<!--
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
-->

# SentientNet :: ChainSentinel Architecture

## 1. Overview
ChainSentinel is the tamper-proof, blockchain-backed core for runtime journaling in SentientNet. It guarantees integrity, traceability, and forensic reproducibility of all system events, including self-modifying logic and AI-driven repairs.

## 2. Core Principles
- **Immutability:** All blocks are cryptographically linked via SHA-256 and Base64-encoded digests.
- **Distributed Trust:** ChainSentinel can operate standalone or with distributed ledger replication in `edge-orchestrator`.
- **Event-driven:** Every logical event (e.g., startup, configuration load, enclave establishment) becomes an atomic block.
- **Formal Anchoring:** Blocks serve as attestable proof for system audits, regression analysis, and zero-day forensics.

## 3. Component Breakdown
ChainSentinel
├── BlockLedger // Core block object with metadata and cryptographic linkage
├── ChainSentinel // Main orchestrator for appending events and maintaining ledger
├── IntegrityProof // Verification unit to assert ledger authenticity
└── LedgerVerifier // Stateless chain integrity checker and diff reporter

## 4. Use Cases
- Boot trace verification
- Proof-of-runtime attestation
- Blockchain-secured rollback protection
- Audit logs for edge AI inference decisions
