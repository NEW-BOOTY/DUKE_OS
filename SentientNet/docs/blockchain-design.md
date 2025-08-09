<!--
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
-->

# SentientNet :: ChainSentinel Blockchain Design

## 1. Block Structure

Each block in the ChainSentinel ledger contains:

| Field          | Type      | Description                                  |
|----------------|-----------|----------------------------------------------|
| `index`        | int       | Position of the block in the chain           |
| `timestamp`    | Instant   | UTC ISO-8601 format                          |
| `data`         | String    | Description of the event or transaction      |
| `previousHash` | String    | Base64-encoded SHA-256 of previous block     |
| `hash`         | String    | Current block’s SHA-256 hash                 |

## 2. Genesis Block

- The chain begins with a genesis block containing a known anchor.
- Its `previousHash` is `"0"` and timestamp is the system boot time.

## 3. Hashing Algorithm

- All blocks are hashed using `SHA-256`.
- The hash input format is:previousHash + data + timestamp.toString() 

- The hash is then Base64 encoded for compactness.

## 4. Chain Validity

A chain is considered **valid** only if:
- Every block’s `previousHash` matches the actual hash of the prior block.
- Hashes conform to SHA-256 entropy checks (to be enforced via `formal-verification.md`).

## 5. Replication Roadmap

- Future modules will integrate:
- **P2P replication**
- **Merkle root proof sync**
- **Quantum-resistant block anchors**
