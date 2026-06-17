package com.wm.automation.utils;

import java.util.Random;
import java.util.UUID;

public class RandomDataUtils {

    private static final Random RANDOM = new Random();
    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String ALPHANUMERIC = ALPHA + NUMERIC;

    private RandomDataUtils() {}

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHA.charAt(RANDOM.nextInt(ALPHA.length())));
        }
        return sb.toString();
    }

    public static String randomAlphanumeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
        }
        return sb.toString();
    }

    public static String randomNumeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        sb.append(NUMERIC.charAt(RANDOM.nextInt(9) + 1)); // avoid leading 0
        for (int i = 1; i < length; i++) {
            sb.append(NUMERIC.charAt(RANDOM.nextInt(NUMERIC.length())));
        }
        return sb.toString();
    }

    public static String randomEmail() {
        return randomString(8).toLowerCase() + "@" + randomString(6).toLowerCase() + ".com";
    }

    public static String randomPhoneNumber() {
        return "(" + randomNumeric(3) + ") " + randomNumeric(3) + "-" + randomNumeric(4);
    }

    public static String randomZipCode() {
        return randomNumeric(5);
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
