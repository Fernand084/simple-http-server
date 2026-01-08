package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * TcpServer
 */
public class TcpServer {

   public static void main(String[] args) {
        int port = 8080;
        System.out.println("Starting TCP server on port "+port+"...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
           // server is listening for connections
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Blocks thread and awaits for a client to connect
                System.out.println("Client connected: "+clientSocket.getInetAddress());

                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
                );

                OutputStream out = clientSocket.getOutputStream();

                // 1 read request line
                String requestLine = reader.readLine();
                if (requestLine == null || requestLine.isEmpty()) {
                    clientSocket.close();
                    continue;
                }
                System.out.println("Request: "+requestLine);

                // 2 parse request line
                String[] parts = requestLine.split(" ");
                String method = parts[0];
                String path = parts[1];

                // 3 read and discard headers
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    // ignored headers
                }

                // 4 routing
                String responseBody;
                int statusCode;
                String statusText;

                if (!method.equals("GET")) {
                    statusCode = 405;
                    statusText = "Method not allowed";
                    responseBody = "Only GET is supported";
                }else{
                    switch (path) {
                        case "/":
                            statusCode = 200;
                            statusText = "OK";
                            responseBody = "Welcome to my Java HTTP Server!";
                            break;
                        case "/hello":
                            statusCode = 200;
                            statusText = "OK";
                            responseBody = "Hello from Java!";
                            break;
                        case "/time":
                            statusCode = 200;
                            statusText = "OK";
                            responseBody = "Server time: " + LocalDateTime.now();
                            break;
                        default:
                            statusCode = 404;
                            statusText = "Not Found";
                            responseBody = "404 - Page NOT found";
                    }
                }


                // 5 build http response
                String response =
                    "HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                    "Content-Type: text/plain\r\n" +
                    "Content-length: " + responseBody.length() + "\r\n" +
                    "\r\n" +
                    responseBody;

                // 6 send response
                out.write(response.getBytes());
                out.flush();
                
                clientSocket.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
   } 
}
