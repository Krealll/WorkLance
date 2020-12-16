package com.krealll.worklance.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {

    private static final int HASH_VALUE_BYTES = 16;
    private static final int NUMBER_OF_DIGITS_IN_HASH = 32;

    public static String encrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        byte[] digest;
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(password.getBytes());
        digest = messageDigest.digest();
        BigInteger num = new BigInteger(1, digest);
        String md5Hex = num.toString(HASH_VALUE_BYTES);
        while (md5Hex.length() < NUMBER_OF_DIGITS_IN_HASH) {
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }
}
