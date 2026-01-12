package com.edgarlop;

import com.edgarlop.router.Router;
import com.edgarlop.server.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TcpServer
 */
public class TcpServer {

   public static void main(String[] args) {
        int port = 8080;
        System.out.println("HTTP Server running  on port "+port+"...");

        Router router = new Router();
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
           // server is listening for connections
            while (true) {
                Socket clientSocket = serverSocket.accept(); 

                threadPool.submit(new ClientHandler(clientSocket, router));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
   } 
}
