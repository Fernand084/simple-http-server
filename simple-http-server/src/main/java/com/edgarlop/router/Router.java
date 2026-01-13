package com.edgarlop.router;

import com.edgarlop.http.HttpRequest;
import com.edgarlop.http.HttpResponse;

public class Router {

    public HttpResponse route(HttpRequest request) {
        HttpResponse response = new HttpResponse();

        if (request.getMethod().equals("GET")) {
            handleGet(request, response);
        } else if (request.getMethod().equals("POST")) {
            handlePost(request, response);
        } else {
            response.setStatus(405, "Method Not Allowed");
            response.setBody("Method not supported");
        }

        return response;
    }

    private void handleGet(HttpRequest request, HttpResponse response) {
        switch (request.getPath()) {
            case "/":
                response.setBody("Welcome to my Java HTTP Server");
                break;
            case "/hello":
                response.setBody("Hello from Java!");
                break;
            default:
                response.setStatus(404, "Not Found");
                response.setBody("404 - Page not found");
        }
    }

    private void handlePost(HttpRequest request, HttpResponse response) {
        if (request.getPath().equals("/echo")) {
            response.setBody("You sent: " + request.getBody());
        } else {
            response.setStatus(404, "Not Found");
            response.setBody("POST endpoint not found");
        }
    }
}

