package com.bramble.kickback.util;

public class Util {

    public static String formatPhoneNumber(String phoneNumber) {
        int lastIndex = phoneNumber.length() - 1;
        return "+"+ phoneNumber.substring(0, lastIndex - 9) + " (" +
               phoneNumber.substring(lastIndex - 9, lastIndex - 6) + ") " +
               phoneNumber.substring(lastIndex - 6, lastIndex - 3) + "-" +
               phoneNumber.substring(lastIndex - 3);
    }

}
