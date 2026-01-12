package com.edgarlop.server;

import com.edgarlop.http.HttpRequest;
import com.edgarlop.http.HttpResponse;
import com.edgarlop.router.Router;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * ClientHandler
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final Router router;

    public ClientHandler(Socket clientSocket, Router router){
        this.clientSocket = clientSocket;
        this.router = router;
    }

    @Override
    public void run(){
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
            );

            HttpRequest request = new HttpRequest(reader);
            HttpResponse response = router.route(request);

            clientSocket.getOutputStream().write(response.toBytes());
            clientSocket.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
