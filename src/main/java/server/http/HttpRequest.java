package server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpRequest
 */
public class HttpRequest {

    private final String method;
    private final String path;
    private final String version;
    private final Map<String, String> headers = new HashMap<>();

    public HttpRequest(BufferedReader reader) throws IOException {
        // request line
        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IOException("Empty request");
        }

        String[] parts = requestLine.split(" ");
        this.method = parts[0];
        this.path = parts[1];
        this.version = parts[2];

        //headers
        String line;
        while((line = reader.readLine()) != null && !line.isEmpty()){
            String[] headerParts = line.split(":",2);
            headers.put(
                headerParts[0].trim(),
                headerParts[1].trim()
            );
        }
    }
    

    public String getMethod(){
        return method;
    }

    public String getPath(){
        return path;
    }

    public String getVersion(String name){
        return headers.get(name);
    }

    public Map<String, String> getHeaders(){
        return headers;
    }
}
