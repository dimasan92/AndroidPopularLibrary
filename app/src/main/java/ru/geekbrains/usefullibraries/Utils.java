package ru.geekbrains.usefullibraries;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Utils {

    public static String MD5(String s) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(s.getBytes(), 0, s.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }

    public static String SHA1(String s) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(s.getBytes(), 0, s.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
}
