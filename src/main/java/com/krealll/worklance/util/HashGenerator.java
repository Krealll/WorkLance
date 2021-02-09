package com.krealll.worklance.util;

import com.krealll.worklance.exception.HashGeneratorException;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

    private final static String ENCODING = "UTF-8";
    private static final int HASH_VALUE_BYTES = 16;
    private static final String PASSWORD_ALGORITHM = "MD5";
    private static final int PASSWORD_HASH_LENGTH = 32;

    public final static String TOKEN_ENCRYPTION="SHA-256";
    public final static int SELECTOR_LENGTH=12;
    public final static int VALIDATOR_LENGTH=64;

    public static String generate(String inputString, String algorithm, int hashLength) throws HashGeneratorException {
        try {
            MessageDigest messageDigest;
            byte[] digest;
            messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(inputString.getBytes(ENCODING));
            digest = messageDigest.digest();
            BigInteger num = new BigInteger(1, digest);
            String md5Hex = num.toString(HASH_VALUE_BYTES);
            while (md5Hex.length() < hashLength) {
                md5Hex = "0" + md5Hex;
            }
            return md5Hex;
        } catch (NoSuchAlgorithmException| UnsupportedEncodingException e) {
            throw new HashGeneratorException(e);
        }
    }

    public static String generatePassword(String inputString) throws HashGeneratorException {
        return generate(inputString,PASSWORD_ALGORITHM, PASSWORD_HASH_LENGTH);
    }
}
