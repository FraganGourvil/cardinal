package com.ohooki.cardinal.utils;

import java.time.LocalDateTime;

public class UtilsMethods {

    public static String prefix = "[CARDINAL]";

    public static void info(String message) {

        System.out.println(prefix + " " + getNow() + " > " + message);

    }

    public static String getNow() {

        return LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond();

    }

}
