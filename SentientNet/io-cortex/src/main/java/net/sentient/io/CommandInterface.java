/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.io_cortex;

 import java.util.concurrent.BlockingQueue;
 import java.util.concurrent.LinkedBlockingQueue;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 /**
  * CommandInterface manages the queuing and dispatching of
  * commands to hardware devices or system components.
  * Thread-safe, non-blocking command submission and retrieval.
  */
 public final class CommandInterface {
 
     private static final Logger LOGGER = Logger.getLogger(CommandInterface.class.getName());
 
     private final BlockingQueue<String> commandQueue;
 
     // Singleton instance
     private static volatile CommandInterface instance;
 
     private CommandInterface() {
         this.commandQueue = new LinkedBlockingQueue<>();
     }
 
     /**
      * Gets the singleton instance of CommandInterface.
      * @return CommandInterface instance.
      */
     public static CommandInterface getInstance() {
         if (instance == null) {
             synchronized (CommandInterface.class) {
                 if (instance == null) {
                     instance = new CommandInterface();
                 }
             }
         }
         return instance;
     }
 
     /**
      * Submits a command to the queue.
      * @param command Command string, non-null, non-empty.
      * @throws IllegalArgumentException if command is null or empty.
      */
     public void submitCommand(String command) {
         if (command == null || command.isBlank()) {
             throw new IllegalArgumentException("Command cannot be null or empty");
         }
         try {
             commandQueue.put(command);
             LOGGER.log(Level.FINE, "Command submitted: {0}", command);
         } catch (InterruptedException e) {
             Thread.currentThread().interrupt();
             LOGGER.log(Level.SEVERE, "Interrupted while submitting command", e);
         }
     }
 
     /**
      * Retrieves and removes the next command from the queue, waiting if necessary.
      * @return The next command string.
      * @throws InterruptedException if interrupted while waiting.
      */
     public String takeCommand() throws InterruptedException {
         String command = commandQueue.take();
         LOGGER.log(Level.FINE, "Command taken: {0}", command);
         return command;
     }
 
     /**
      * Retrieves and removes the next command from the queue, or returns null if none available.
      * @return The next command string or null if queue empty.
      */
     public String pollCommand() {
         String command = commandQueue.poll();
         if (command != null) {
             LOGGER.log(Level.FINE, "Command polled: {0}", command);
         }
         return command;
     }
 
     /**
      * Returns current number of queued commands.
      * @return size of command queue.
      */
     public int getPendingCommandCount() {
         return commandQueue.size();
     }
 }
 