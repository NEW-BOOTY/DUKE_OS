/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.io_cortex;

 import java.io.Closeable;
 import java.io.IOException;
 import java.util.Objects;
 import java.util.concurrent.atomic.AtomicBoolean;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 /**
  * DeviceBridge provides a robust, thread-safe interface
  * for communication with hardware devices via native drivers or
  * standardized protocols.
  */
 public final class DeviceBridge implements Closeable {
 
     private static final Logger LOGGER = Logger.getLogger(DeviceBridge.class.getName());
 
     private final String deviceId;
     private final AtomicBoolean isOpen;
 
     /**
      * Opens connection to device identified by deviceId.
      * @param deviceId Unique identifier of device.
      * @throws IOException on failure to open device connection.
      */
     public DeviceBridge(String deviceId) throws IOException {
         this.deviceId = Objects.requireNonNull(deviceId, "deviceId cannot be null");
         this.isOpen = new AtomicBoolean(false);
         open();
     }
 
     /**
      * Opens the device connection.
      * @throws IOException if device cannot be opened.
      */
     private void open() throws IOException {
         // Placeholder for actual device opening logic.
         // This might involve JNI calls, serial communication setup, or socket connection.
         try {
             // Simulated open logic
             isOpen.set(true);
             LOGGER.log(Level.INFO, "Device {0} connection opened.", deviceId);
         } catch (Exception e) {
             throw new IOException("Failed to open device: " + deviceId, e);
         }
     }
 
     /**
      * Writes data to the device.
      * @param data Non-null byte array data to send.
      * @throws IOException if device not open or write fails.
      */
     public void write(byte[] data) throws IOException {
         if (!isOpen.get()) {
             throw new IOException("Device is not open");
         }
         Objects.requireNonNull(data, "data cannot be null");
         try {
             // Actual device write logic here.
             // For example, native call or stream write.
             LOGGER.log(Level.FINE, "Writing {0} bytes to device {1}", new Object[]{data.length, deviceId});
         } catch (Exception e) {
             throw new IOException("Failed to write to device: " + deviceId, e);
         }
     }
 
     /**
      * Reads data from the device.
      * @return Non-null byte array of data read.
      * @throws IOException if device not open or read fails.
      */
     public byte[] read() throws IOException {
         if (!isOpen.get()) {
             throw new IOException("Device is not open");
         }
         try {
             // Actual device read logic here.
             // Placeholder returning empty byte array.
             LOGGER.log(Level.FINE, "Reading data from device {0}", deviceId);
             return new byte[0];
         } catch (Exception e) {
             throw new IOException("Failed to read from device: " + deviceId, e);
         }
     }
 
     /**
      * Closes the device connection.
      * @throws IOException if closing fails.
      */
     @Override
     public void close() throws IOException {
         if (isOpen.compareAndSet(true, false)) {
             try {
                 // Actual device close logic here.
                 LOGGER.log(Level.INFO, "Device {0} connection closed.", deviceId);
             } catch (Exception e) {
                 throw new IOException("Failed to close device: " + deviceId, e);
             }
         }
     }
 
     /**
      * Returns the device identifier.
      * @return deviceId string.
      */
     public String getDeviceId() {
         return deviceId;
     }
 
     /**
      * Returns whether device connection is open.
      * @return true if open, else false.
      */
     public boolean isOpen() {
         return isOpen.get();
     }
 }
 