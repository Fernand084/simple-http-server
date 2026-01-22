package com.edgarlop.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private final String method;
    private final String path;
    private final String version;
    private final Map<String, String> headers = new HashMap<>();
    private String body = "";

    public HttpRequest(BufferedReader reader) throws IOException {
        // 1️⃣ Request line
        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new BadRequestException("Empty Request");
        }

        String[] parts = requestLine.split(" ");

        if (parts.length != 3) {
            throw new BadRequestException("Invalid request line");
        }
        this.method = parts[0];
        this.path = parts[1];
        this.version = parts[2];

        // 2️⃣ Headers
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            String[] headerParts = line.split(":", 2);
            headers.put(
                    headerParts[0].trim(),
                    headerParts[1].trim());
        }

        // 3️⃣ Body (solo si existe Content-Length)
        String contentLengthHeader = headers.get("Content-Length");
        if (contentLengthHeader != null) {
            int contentLength = Integer.parseInt(contentLengthHeader);
            char[] bodyChars = new char[contentLength];
            reader.read(bodyChars, 0, contentLength);
            body = new String(bodyChars);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getBody() {
        return body;
    }
}
