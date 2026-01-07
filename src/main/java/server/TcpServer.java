package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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

                String line;
                System.out.println("---- Incoming data ----");

                // Read line by line until the client stop sending data
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    System.out.println(line);
                }

                System.out.println("---- End of data ----");
                clientSocket.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
   } 
}
