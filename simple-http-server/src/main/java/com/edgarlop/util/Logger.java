package com.edgarlop.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger
 */
public class Logger {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
    public static void info(String msg) {
        log("INFO", msg);
    }

    public static void warn(String msg) {
        log("WARN", msg);
    }

    public static void error(String msg){
        log("ERROR", msg);
    }

    private static void log(String level, String msg){
        String time = LocalDateTime.now().format(FORMAT);
        System.out.println("["+ time + "] [" + level + "] "+ msg);
    }
}
