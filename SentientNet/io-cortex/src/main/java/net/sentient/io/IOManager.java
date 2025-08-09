/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.io_cortex;

 import java.io.Closeable;
 import java.io.IOException;
 import java.util.Map;
 import java.util.Objects;
 import java.util.concurrent.ConcurrentHashMap;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 /**
  * IOManager manages multiple DeviceBridge instances,
  * providing lifecycle management and multiplexed access.
  */
 public final class IOManager implements Closeable {
 
     private static final Logger LOGGER = Logger.getLogger(IOManager.class.getName());
 
     private final Map<String, DeviceBridge> devices = new ConcurrentHashMap<>();
 
     /**
      * Opens and registers a new device connection.
      * @param deviceId Unique device identifier.
      * @throws IOException if device cannot be opened or already exists.
      */
     public void registerDevice(String deviceId) throws IOException {
         Objects.requireNonNull(deviceId, "deviceId cannot be null");
         if (devices.containsKey(deviceId)) {
             throw new IOException("Device already registered: " + deviceId);
         }
         DeviceBridge bridge = new DeviceBridge(deviceId);
         devices.put(deviceId, bridge);
         LOGGER.log(Level.INFO, "Device registered: {0}", deviceId);
     }
 
     /**
      * Sends data to a registered device.
      * @param deviceId Unique device identifier.
      * @param data Non-null byte array data.
      * @throws IOException if device is not registered or write fails.
      */
     public void sendData(String deviceId, byte[] data) throws IOException {
         Objects.requireNonNull(deviceId, "deviceId cannot be null");
         Objects.requireNonNull(data, "data cannot be null");
 
         DeviceBridge bridge = devices.get(deviceId);
         if (bridge == null) {
             throw new IOException("Device not registered: " + deviceId);
         }
         bridge.write(data);
         LOGGER.log(Level.FINE, "Data sent to device {0}, size: {1}", new Object[]{deviceId, data.length});
     }
 
     /**
      * Receives data from a registered device.
      * @param deviceId Unique device identifier.
      * @return Non-null byte array of data read.
      * @throws IOException if device is not registered or read fails.
      */
     public byte[] receiveData(String deviceId) throws IOException {
         Objects.requireNonNull(deviceId, "deviceId cannot be null");
 
         DeviceBridge bridge = devices.get(deviceId);
         if (bridge == null) {
             throw new IOException("Device not registered: " + deviceId);
         }
         byte[] data = bridge.read();
         LOGGER.log(Level.FINE, "Data received from device {0}, size: {1}", new Object[]{deviceId, data.length});
         return data;
     }
 
     /**
      * Closes and unregisters a device.
      * @param deviceId Unique device identifier.
      * @throws IOException if device is not registered or close fails.
      */
     public void unregisterDevice(String deviceId) throws IOException {
         Objects.requireNonNull(deviceId, "deviceId cannot be null");
 
         DeviceBridge bridge = devices.remove(deviceId);
         if (bridge == null) {
             throw new IOException("Device not registered: " + deviceId);
         }
         bridge.close();
         LOGGER.log(Level.INFO, "Device unregistered and closed: {0}", deviceId);
     }
 
     /**
      * Closes all devices and clears registry.
      * @throws IOException if any device fails to close.
      */
     @Override
     public void close() throws IOException {
         IOException exception = null;
         for (Map.Entry<String, DeviceBridge> entry : devices.entrySet()) {
             try {
                 entry.getValue().close();
                 LOGGER.log(Level.INFO, "Device closed: {0}", entry.getKey());
             } catch (IOException e) {
                 LOGGER.log(Level.SEVERE, "Failed to close device: " + entry.getKey(), e);
                 if (exception == null) {
                     exception = new IOException("One or more devices failed to close.");
                 }
             }
         }
         devices.clear();
         if (exception != null) {
             throw exception;
         }
     }
 }
 