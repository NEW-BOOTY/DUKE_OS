/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.io_cortex;

 import com.sun.net.httpserver.HttpExchange;
 import com.sun.net.httpserver.HttpHandler;
 import com.sun.net.httpserver.HttpServer;
 
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.net.InetSocketAddress;
 import java.net.URI;
 import java.nio.charset.StandardCharsets;
 import java.util.Objects;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 /**
  * RestInterface provides an embedded lightweight HTTP REST server
  * to expose IO operations over network for remote control.
  */
 public final class RestInterface {
 
     private static final Logger LOGGER = Logger.getLogger(RestInterface.class.getName());
 
     private final HttpServer server;
     private final IOManager ioManager;
     private final ExecutorService executorService;
 
     /**
      * Constructs RestInterface binding to specified port.
      * @param port Port to listen on, must be > 0.
      * @param ioManager Non-null IOManager instance.
      * @throws IOException if server cannot be started.
      */
     public RestInterface(int port, IOManager ioManager) throws IOException {
         if (port <= 0) {
             throw new IllegalArgumentException("Port must be positive");
         }
         this.ioManager = Objects.requireNonNull(ioManager, "ioManager cannot be null");
         this.server = HttpServer.create(new InetSocketAddress(port), 0);
         this.executorService = Executors.newCachedThreadPool();
 
         server.createContext("/device/send", new SendHandler());
         server.createContext("/device/receive", new ReceiveHandler());
         server.setExecutor(executorService);
         server.start();
 
         LOGGER.log(Level.INFO, "REST server started on port {0}", port);
     }
 
     /**
      * Stops the server and shuts down executor.
      */
     public void stop() {
         server.stop(0);
         executorService.shutdownNow();
         LOGGER.info("REST server stopped");
     }
 
     /**
      * Handler for sending data to device via POST /device/send
      * JSON body expected: { "deviceId": "device1", "data": "base64encoded" }
      */
     private class SendHandler implements HttpHandler {
 
         @Override
         public void handle(HttpExchange exchange) throws IOException {
             if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                 sendResponse(exchange, 405, "Method Not Allowed");
                 return;
             }
 
             try (InputStream is = exchange.getRequestBody()) {
                 byte[] requestBody = is.readAllBytes();
                 String bodyStr = new String(requestBody, StandardCharsets.UTF_8);
 
                 String deviceId = extractJsonValue(bodyStr, "deviceId");
                 String dataEncoded = extractJsonValue(bodyStr, "data");
 
                 if (deviceId == null || dataEncoded == null) {
                     sendResponse(exchange, 400, "Invalid JSON payload");
                     return;
                 }
 
                 byte[] data = java.util.Base64.getDecoder().decode(dataEncoded);
 
                 ioManager.sendData(deviceId, data);
 
                 sendResponse(exchange, 200, "Data sent successfully");
             } catch (IllegalArgumentException e) {
                 sendResponse(exchange, 400, "Base64 decoding failed");
             } catch (IOException e) {
                 sendResponse(exchange, 500, "IO Error: " + e.getMessage());
             } catch (Exception e) {
                 sendResponse(exchange, 500, "Internal Server Error");
                 LOGGER.log(Level.SEVERE, "Exception in SendHandler", e);
             }
         }
     }
 
     /**
      * Handler for receiving data from device via GET /device/receive?deviceId=device1
      */
     private class ReceiveHandler implements HttpHandler {
 
         @Override
         public void handle(HttpExchange exchange) throws IOException {
             if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                 sendResponse(exchange, 405, "Method Not Allowed");
                 return;
             }
 
             URI requestURI = exchange.getRequestURI();
             String query = requestURI.getQuery();
             String deviceId = null;
 
             if (query != null) {
                 for (String param : query.split("&")) {
                     String[] keyValue = param.split("=", 2);
                     if (keyValue.length == 2 && "deviceId".equals(keyValue[0])) {
                         deviceId = java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                         break;
                     }
                 }
             }
 
             if (deviceId == null || deviceId.isBlank()) {
                 sendResponse(exchange, 400, "Missing deviceId query parameter");
                 return;
             }
 
             try {
                 byte[] data = ioManager.receiveData(deviceId);
                 String base64Data = java.util.Base64.getEncoder().encodeToString(data);
                 String jsonResponse = "{\"deviceId\":\"" + deviceId + "\",\"data\":\"" + base64Data + "\"}";
 
                 sendResponse(exchange, 200, jsonResponse, "application/json");
             } catch (IOException e) {
                 sendResponse(exchange, 500, "IO Error: " + e.getMessage());
             } catch (Exception e) {
                 sendResponse(exchange, 500, "Internal Server Error");
                 LOGGER.log(Level.SEVERE, "Exception in ReceiveHandler", e);
             }
         }
     }
 
     private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
         sendResponse(exchange, statusCode, response, "text/plain; charset=utf-8");
     }
 
     private void sendResponse(HttpExchange exchange, int statusCode, String response, String contentType) throws IOException {
         byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
         exchange.getResponseHeaders().set("Content-Type", contentType);
         exchange.sendResponseHeaders(statusCode, bytes.length);
         try (OutputStream os = exchange.getResponseBody()) {
             os.write(bytes);
         }
     }
 
     /**
      * Simple JSON value extractor for flat JSON { "key":"value", ... }
      * Not a full JSON parser. For production replace with Jackson or Gson.
      * @param json JSON string
      * @param key Key to find
      * @return Value string or null if not found.
      */
     private static String extractJsonValue(String json, String key) {
         String pattern = "\"" + key + "\"\\s*:\\s*\"([^\"]*)\"";
         java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
         java.util.regex.Matcher m = p.matcher(json);
         if (m.find()) {
             return m.group(1);
         }
         return null;
     }
 }
 