
---

## 📁 `docs/security-model.md`

```markdown
<!--
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
-->

# SentientNet :: ChainSentinel Security Model

## 1. Security Goals

| Goal                    | Description                                           |
|-------------------------|-------------------------------------------------------|
| Integrity               | Prevent modification of any block post-creation       |
| Authenticity            | All block additions are verifiable and traceable      |
| Confidentiality         | (Planned) Encrypted payloads per block                |
| Forward Secrecy         | Hash chaining prevents compromise of future blocks     |
| Replay Resistance       | Hashes include real-time data to prevent duplication   |

## 2. Threat Model

### 🛡️ Trusted Components:
- JVM runtime
- Cryptographic libraries (BouncyCastle)
- Code signed JAR artifact
- Edge orchestrator runtime (if distributed)

### ☠️ Threats Addressed:
- Tampering with historical events
- Runtime mutation without chain append
- Rollback attacks
- Undetected fork generation

### 🚫 Out-of-Scope (Handled in other modules):
- Network-based replay (handled by `neuro-shield`)
- Physical memory inspection (handled by `sentient-core`)

## 3. Hash Algorithm

- Algorithm: `SHA-256`
- Encoding: `Base64`
- Collision-resistance: 2⁻¹²⁸ chance
- Output entropy ≥ 256 bits

## 4. Deployment Integrity

- Docker container provides:
  - Immutability
  - Read-only volumes (planned)
  - Binary signature enforcement (planned)
