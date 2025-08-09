/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.blockchain;

 import java.time.Instant;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;
 
 public final class ChainSentinel {
     private final List<BlockLedger> blockchain = Collections.synchronizedList(new ArrayList<>());
 
     public ChainSentinel() {
         BlockLedger genesis = BlockLedger.createGenesisBlock();
         blockchain.add(genesis);
     }
 
     public synchronized BlockLedger addEvent(String eventData) {
         BlockLedger previousBlock = blockchain.get(blockchain.size() - 1);
         BlockLedger newBlock = BlockLedger.createNextBlock(previousBlock, eventData);
         blockchain.add(newBlock);
         return newBlock;
     }
 
     public List<BlockLedger> getBlockchainSnapshot() {
         return List.copyOf(blockchain);
     }
 
     public boolean verifyChain() {
         return LedgerVerifier.verify(blockchain);
     }
 
     public void printChain() {
         blockchain.forEach(System.out::println);
     }
 }
 