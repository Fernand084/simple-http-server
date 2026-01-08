package com.edgarlop.router;

import com.edgarlop.http.HttpRequest;
import com.edgarlop.http.HttpResponse;
import java.time.LocalDateTime;

/**
 * Router
 */
public class Router {

    public HttpResponse route(HttpRequest request){
        HttpResponse response = new HttpResponse();

        if (!request.getMethod().equals("GET")) {
            response.setStatus(405, "Method Not Allowed");
            response.setBody("Only GET is supported");
            return response;
        }

        switch (request.getPath()) {
            case "/":
                response.setBody("Welcome to my Java HTTP Server!");
                break;
            case "/hello":
                response.setBody("Hello from Java!");
                break;
            case "/time":
                response.setBody("Server time: "+LocalDateTime.now());
                break;

            default:
                response.setStatus(404,"Not Found");
                response.setBody("404 - Page not found");
        }

        return response;
    }
}
