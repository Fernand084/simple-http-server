package com.edgarlop;

import com.edgarlop.router.Router;
import com.edgarlop.server.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * TcpServer
 */
public class TcpServer {

   public static void main(String[] args) {
        int port = 8080;
        System.out.println("HTTP Server running  on port "+port+"...");

        Router router = new Router();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
           // server is listening for connections
            while (true) {
                Socket clientSocket = serverSocket.accept(); 

                Thread thread = new Thread(
                    new ClientHandler(clientSocket, router)
                );
                thread.start();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
   } 
}
