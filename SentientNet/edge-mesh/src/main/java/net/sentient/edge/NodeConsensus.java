/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.mesh;

 import java.util.concurrent.atomic.AtomicBoolean;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 /**
  * NodeConsensus manages the consensus protocol for the MeshNode.
  * It continuously runs a consensus loop that can be extended
  * to implement specific consensus algorithms like RAFT or PBFT.
  */
 public class NodeConsensus {
 
     private static final Logger LOGGER = Logger.getLogger(NodeConsensus.class.getName());
 
     private final MeshNode self;
     private final AtomicBoolean running = new AtomicBoolean(false);
 
     /**
      * Constructs a NodeConsensus instance bound to a specific MeshNode.
      *
      * @param self The MeshNode this consensus instance belongs to.
      */
     public NodeConsensus(MeshNode self) {
         if (self == null) {
             throw new IllegalArgumentException("MeshNode cannot be null.");
         }
         this.self = self;
     }
 
     /**
      * Starts the consensus loop.
      * This method blocks and should be run on a separate thread.
      */
     public void startConsensusLoop() {
         if (!running.compareAndSet(false, true)) {
             LOGGER.warning("Consensus loop already running for node: " + self.getNodeId());
             return;
         }
         LOGGER.info("NodeConsensus started for: " + self.getNodeId());
 
         try {
             while (running.get()) {
                 performConsensusRound();
                 Thread.sleep(5000); // Interval between consensus rounds
             }
         } catch (InterruptedException e) {
             Thread.currentThread().interrupt();
             LOGGER.warning("Consensus loop interrupted for node: " + self.getNodeId());
         } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Unexpected error in consensus loop for node: " + self.getNodeId(), e);
         } finally {
             running.set(false);
             LOGGER.info("NodeConsensus stopped for: " + self.getNodeId());
         }
     }
 
     /**
      * Stops the consensus loop gracefully.
      */
     public void stopConsensus() {
         if (running.compareAndSet(true, false)) {
             LOGGER.info("Consensus stopping requested for node: " + self.getNodeId());
         } else {
             LOGGER.warning("Consensus was not running for node: " + self.getNodeId());
         }
     }
 
     /**
      * Performs one round of consensus.
      * Extend this method to implement actual consensus logic.
      */
     private void performConsensusRound() {
         LOGGER.fine("Performing consensus round for node: " + self.getNodeId());
         // TODO: Implement actual consensus protocol here (e.g., RAFT, PBFT)
         // Simulated step for demonstration:
         try {
             // Simulate processing time
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             Thread.currentThread().interrupt();
             LOGGER.warning("Consensus round interrupted.");
         }
     }
 }
 