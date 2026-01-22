package com.edgarlop.http;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpResponse
 */
public class HttpResponse {

    private int statusCode = 200;
    private String statusText = "OK";
    private String body = "";
    private final Map<String, String> headers = new HashMap<>();

    public HttpResponse() {
        headers.put("Content-Type", "text/plain");
    }

    public void setStatus(int code, String text) {
        this.statusCode = code;
        this.statusText = text;
    }

    public void setBody(String body) {
        this.body = body;
        headers.put("Content-Length", String.valueOf(body.getBytes(StandardCharsets.UTF_8).length));
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getBody() {
        return this.body;
    }

    public String getStatusText() {
        return this.statusText;
    }

    public byte[] toBytes() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ")
                .append(statusCode)
                .append(" ")
                .append(statusText)
                .append("\r\n");

        for (Map.Entry<String, String> h : headers.entrySet()) {
            sb.append(h.getKey())
                    .append(": ")
                    .append(h.getValue())
                    .append("\r\n");
        }

        sb.append("\r\n");
        sb.append(body);

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static HttpResponse notFound() {
        HttpResponse res = new HttpResponse();
        res.setStatus(404, "Not Found");
        res.setBody("Sorry, the page you're looking for does not exists.");
        return res;
    }

    public static HttpResponse internalServerError() {
        HttpResponse res = new HttpResponse();
        res.setStatus(500, "Internal Server Error");
        res.setBody("Something went wrong");
        return res;
    }

    public static HttpResponse badRequest() {
        HttpResponse res = new HttpResponse();
        res.setStatus(400, "Bad Request");
        res.setBody("This request is not valid!");
        return res;
    }

}
