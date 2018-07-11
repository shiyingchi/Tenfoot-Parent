package com.project.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class UUIDKey {

    private static Object lock = new Object();

    private final static String str = "1234567890";

    private final static int pixLen = str.length();

    private static volatile int one = 0;

    private static volatile int two = 0;

    private static volatile int three = 0;

    private static volatile int four = 0;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    private static String dateString = sdf.format(new Date());

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static String getUUIDNO(){
        synchronized (lock){
            four++;
            if (four == pixLen) {
                four = 0;
                three++;
                if (three == pixLen) {
                    three = 0;
                    two++;
                    if (two == pixLen) {
                        two = 0;
                        one++;
                        if (one == pixLen) {
                            one = 0;
                        }
                    }
                }
            }
            return dateString + str.charAt(one) + str.charAt(two) + str.charAt(three) + str.charAt(four);
        }

    }

}
