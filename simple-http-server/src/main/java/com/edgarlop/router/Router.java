package com.edgarlop.router;

import java.time.LocalDateTime;
import java.util.HashMap;
import com.edgarlop.util.Logger;

import com.edgarlop.http.HttpRequest;
import com.edgarlop.http.HttpResponse;

public class Router {

    public HttpResponse route(HttpRequest request) {
        HttpResponse response = new HttpResponse();

        if (request.getMethod().equals("GET")) {
            response = handleGet(request, response);
        } else if (request.getMethod().equals("POST")) {
            handlePost(request, response);
        } else {
            response.setStatus(405, "Method Not Allowed");
            response.setBody("Method not supported");
        }
        return response;
    }

    private HttpResponse handleGet(HttpRequest request, HttpResponse response) {
        HashMap<String, String> routes = new HashMap<>();

        routes.put("/", "Welcome to my Java HTTP Server!");
        routes.put("/hello", "Hello from Java!");
        routes.put("/time", LocalDateTime.now().toString());
        if (!routes.containsKey(request.getPath())) {
            Logger.warn("Route not found: " + request.getPath());
            response = HttpResponse.notFound();
        } else {
            response.setBody(routes.get(request.getPath()));
        }
        return response;
    }

    private void handlePost(HttpRequest request, HttpResponse response) {
        if (request.getPath().equals("/echo")) {
            response.setBody("You sent: " + request.getBody());
        } else {
            HttpResponse.notFound();
        }
    }
}
