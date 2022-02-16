package com.example.BarangayServicesclient;

import java.util.logging.Logger;

public class Logging {
    final private static Logger logger = Logger.getLogger(Logging.class.getName());

    public static void printInfoLog(String message){
        logger.info(message);
    }
}
