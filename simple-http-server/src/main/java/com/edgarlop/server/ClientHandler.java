package com.edgarlop.server;

import com.edgarlop.http.BadRequestException;
import com.edgarlop.http.HttpRequest;
import com.edgarlop.http.HttpResponse;
import com.edgarlop.router.Router;
import com.edgarlop.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final Router router;

    public ClientHandler(Socket socket, Router router) {
        this.socket = socket;
        this.router = router;
    }

    @Override
    public void run() {
        try {
            socket.setSoTimeout(10_000); // 10 segundos

            Logger.info("New connection from " + socket.getRemoteSocketAddress());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            while (true) {
                HttpRequest request = new HttpRequest(reader);
                HttpResponse response;

                try {
                    response = router.route(request);
                } catch (BadRequestException e) {
                    Logger.warn(e.getMessage());
                    response = HttpResponse.notFound();
                } catch (Exception e) {
                    Logger.error(e.toString());
                    response = HttpResponse.internalServerError();
                }

                // Keep-Alive logic
                String connectionHeader = request.getHeader("Connection");
                boolean close = connectionHeader != null &&
                        connectionHeader.equalsIgnoreCase("close");

                if (close) {
                    response.addHeader("Connection", "close");
                } else {
                    response.addHeader("Connection", "keep-alive");
                }

                socket.getOutputStream().write(response.toBytes());
                socket.getOutputStream().flush();
                Logger.info(
                        request.getMethod() + " " +
                                request.getPath() + " -> " +
                                response.getStatusCode());
            }
        } catch (Exception e) {
            Logger.info("Connection closed: " + socket.getRemoteSocketAddress());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("Can't close Socket");
            }
        }
    }
}
