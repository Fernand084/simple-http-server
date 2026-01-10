package com.edgarlop;

import com.edgarlop.http.HttpRequest;
import com.edgarlop.http.HttpResponse;
import com.edgarlop.router.Router;

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

        Router router = new Router();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
           // server is listening for connections
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Blocks thread and awaits for a client to connect

                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
                );

                HttpRequest request = new HttpRequest(reader);
                HttpResponse response = router.route(request);


                clientSocket.getOutputStream().write(response.toBytes());
                clientSocket.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
   } 
}
