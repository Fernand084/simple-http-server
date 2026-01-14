package com.edgarlop.server;

import com.edgarlop.http.HttpRequest;
import com.edgarlop.http.HttpResponse;
import com.edgarlop.router.Router;

import java.io.BufferedReader;
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

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            while (true) {
                HttpRequest request;

                try {
                    request = new HttpRequest(reader);
                } catch (Exception e) {
                    break; // cliente cerró conexión
                }

                HttpResponse response = router.route(request);

                // Keep-Alive logic
                String connectionHeader = request.getHeader("Connection");
                boolean close =
                        connectionHeader != null &&
                        connectionHeader.equalsIgnoreCase("close");

                if (close) {
                    response.addHeader("Connection", "close");
                } else {
                    response.addHeader("Connection", "keep-alive");
                }

                socket.getOutputStream().write(response.toBytes());
                socket.getOutputStream().flush();

                if (close) {
                    break;
                }
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

