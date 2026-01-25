package com.edgarlop.util;

import java.util.HashMap;
import java.util.Map;

/*
    Simple Json parser
    * do not support nested objects
    * do not support arrays
    * 
*/

public class JsonParser {
    public static Map<String, String> parse(String json) {
        Map<String, String> map = new HashMap<>();

        json = json.trim();
        json = json.substring(1, json.length() - 1); // remove {}

        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] kv = pair.split(":");
            String key = kv[0].trim().replace("\"", "");
            String value = kv[1].trim().replace("\"", "");
            map.put(key, value);
        }
        return map;
    }
}
