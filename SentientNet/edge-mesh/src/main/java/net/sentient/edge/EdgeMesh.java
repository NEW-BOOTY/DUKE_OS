/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.mesh;

 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import java.util.logging.Logger;
 
 /**
  * EdgeMesh is the central orchestrator for managing node state,
  * consensus protocol, and distributed task execution within the SentientNet edge layer.
  * It coordinates the lifecycle of the mesh node and delegates responsibilities
  * to its subcomponents: MeshNode, NodeConsensus, and TaskDistributor.
  */
 public class EdgeMesh {
 
     private static final Logger LOGGER = Logger.getLogger(EdgeMesh.class.getName());
 
     // Thread pool to manage consensus and task execution threads.
     private static final ExecutorService executor = Executors.newCachedThreadPool();
 
     private final MeshNode localNode;
     private final NodeConsensus consensus;
     private final TaskDistributor distributor;
 
     /**
      * Initializes the EdgeMesh controller with the current node context.
      * @param node MeshNode instance representing this edge instance.
      */
     public EdgeMesh(MeshNode node) {
         this.localNode = node;
         this.consensus = new NodeConsensus(node);
         this.distributor = new TaskDistributor(node);
         LOGGER.info("EdgeMesh initialized for node: " + node.getNodeId());
     }
 
     /**
      * Starts the edge mesh execution including consensus and task listener.
      */
     public void start() {
         LOGGER.info("Starting EdgeMesh...");
         executor.submit(() -> {
             try {
                 consensus.startConsensusLoop();
             } catch (Exception e) {
                 LOGGER.severe("Consensus loop failed: " + e.getMessage());
             }
         });
 
         executor.submit(() -> {
             try {
                 distributor.startTaskListener();
             } catch (Exception e) {
                 LOGGER.severe("Task distributor listener failed: " + e.getMessage());
             }
         });
     }
 
     /**
      * Shuts down all threads associated with the mesh node.
      */
     public void shutdown() {
         LOGGER.warning("Shutting down EdgeMesh...");
         try {
             executor.shutdownNow();
             consensus.stopConsensus();
             LOGGER.info("EdgeMesh shutdown complete.");
         } catch (Exception e) {
             LOGGER.severe("Error during shutdown: " + e.getMessage());
         }
     }
 
     public MeshNode getLocalNode() {
         return localNode;
     }
 
     public NodeConsensus getConsensus() {
         return consensus;
     }
 
     public TaskDistributor getDistributor() {
         return distributor;
     }
 }
 