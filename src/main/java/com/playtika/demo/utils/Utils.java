package com.playtika.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class Utils {

    private static final int THREAD_PLACEHOLDER_SIZE = 18;
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("mm:ss:SSS");

    public static void log(String message, Object... objects) {
        String threadName = Thread.currentThread().getName();
        String formattedThread = toSize(threadName.split("-")[0], THREAD_PLACEHOLDER_SIZE);
        System.out.println(time() + " [" + formattedThread + "] " +  buildMessage(message, objects));
    }

    private static String time() {
        return TIME_FORMAT.format(new Date());
    }

    private static String toSize(String str, int size) {
        if (str.length() > size) {
            return str.substring(0, size);
        } else if (str.length() < size) {
            return str + IntStream.range(0, size - str.length())
                .mapToObj(ignore -> " ")
                .collect(joining());
        }
        return str;
    }

    private static String buildMessage(String message, Object... objects) {
        return Stream.of(objects)
            .reduce(message, (msg, obj) -> msg.replaceFirst("\\{\\}", obj.toString()), (a,b) -> a);
    }
}
