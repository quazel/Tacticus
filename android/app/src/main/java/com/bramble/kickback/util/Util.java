package com.bramble.kickback.util;

public class Util {

    public static String formatPhoneNumber(String phoneNumber) {
        int lastIndex = phoneNumber.length() - 1;
        return phoneNumber.substring(0, lastIndex - 10) + "-" + phoneNumber.substring(lastIndex - 10, lastIndex - 7) + "-" + phoneNumber.substring(lastIndex - 7, lastIndex - 4) + "-" + phoneNumber.substring(lastIndex - 4);
    }

}
