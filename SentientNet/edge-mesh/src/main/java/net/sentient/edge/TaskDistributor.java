/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.mesh;

 import java.util.concurrent.BlockingQueue;
 import java.util.concurrent.LinkedBlockingQueue;
 import java.util.concurrent.atomic.AtomicBoolean;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 /**
  * TaskDistributor handles distributed task queueing and execution
  * within the mesh node. Tasks are securely queued and processed
  * asynchronously to ensure load balancing and fault tolerance.
  */
 public class TaskDistributor {
 
     private static final Logger LOGGER = Logger.getLogger(TaskDistributor.class.getName());
 
     private final MeshNode node;
     private final BlockingQueue<Runnable> taskQueue;
     private final AtomicBoolean running;
     private Thread workerThread;
 
     /**
      * Constructs a TaskDistributor for the given mesh node.
      *
      * @param node The MeshNode instance that owns this TaskDistributor.
      */
     public TaskDistributor(MeshNode node) {
         if (node == null) {
             throw new IllegalArgumentException("MeshNode cannot be null.");
         }
         this.node = node;
         this.taskQueue = new LinkedBlockingQueue<>();
         this.running = new AtomicBoolean(false);
     }
 
     /**
      * Starts the task listener thread which processes tasks from the queue.
      */
     public void startTaskListener() {
         if (running.compareAndSet(false, true)) {
             workerThread = new Thread(this::processTasks, "TaskDistributor-" + node.getNodeId());
             workerThread.setDaemon(true);
             workerThread.start();
             LOGGER.info("TaskDistributor started for node: " + node.getNodeId());
         } else {
             LOGGER.warning("TaskDistributor already running for node: " + node.getNodeId());
         }
     }
 
     /**
      * Stops the task listener thread gracefully.
      */
     public void stopTaskListener() {
         if (running.compareAndSet(true, false)) {
             workerThread.interrupt();
             LOGGER.info("TaskDistributor stopping for node: " + node.getNodeId());
         } else {
             LOGGER.warning("TaskDistributor not running for node: " + node.getNodeId());
         }
     }
 
     /**
      * Enqueues a new task to be executed asynchronously.
      *
      * @param task Runnable task to execute.
      * @throws IllegalStateException if the distributor is not running.
      */
     public void submitTask(Runnable task) {
         if (!running.get()) {
             throw new IllegalStateException("TaskDistributor is not running. Cannot accept new tasks.");
         }
         if (task == null) {
             throw new IllegalArgumentException("Task cannot be null.");
         }
         try {
             taskQueue.put(task);
             LOGGER.fine("Task submitted to queue on node: " + node.getNodeId());
         } catch (InterruptedException e) {
             Thread.currentThread().interrupt();
             LOGGER.log(Level.WARNING, "Interrupted while submitting task.", e);
         }
     }
 
     /**
      * Internal method to process tasks from the queue in a loop.
      */
     private void processTasks() {
         while (running.get()) {
             try {
                 Runnable task = taskQueue.take();
                 try {
                     task.run();
                     LOGGER.fine("Task executed successfully on node: " + node.getNodeId());
                 } catch (Exception e) {
                     LOGGER.log(Level.SEVERE, "Task execution failed on node: " + node.getNodeId(), e);
                 }
             } catch (InterruptedException e) {
                 if (!running.get()) {
                     LOGGER.info("TaskDistributor worker thread interrupted due to shutdown.");
                     break;
                 }
                 Thread.currentThread().interrupt();
                 LOGGER.log(Level.WARNING, "TaskDistributor worker thread interrupted unexpectedly.", e);
             }
         }
         LOGGER.info("TaskDistributor worker thread exiting for node: " + node.getNodeId());
     }
 }
 