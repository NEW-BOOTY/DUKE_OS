/*
 * Copyright (c) Devin B. Royal. All Rights Reserved.
 */


import java.io.*;
import java.net.*;

public class SimpleProxyServer {
    public static void main(String[] args) throws IOException {
        int localPort = 8888; // Proxy will listen here
        ServerSocket serverSocket = new ServerSocket(localPort);
        System.out.println("Proxy server running on port " + localPort);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleRequest(clientSocket)).start();
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            String requestLine = in.readLine();
            if (requestLine != null && requestLine.startsWith("GET ")) {
                // Forward to your app (or external server)
                URL url = new URL("http://localhost:8080" + requestLine.split(" ")[1]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                out.write("HTTP/1.1 200 OK\r\n\r\n");
                while ((line = responseReader.readLine()) != null) {
                    out.write(line + "\n");
                }
                responseReader.close();
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
