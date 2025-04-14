package kr.co.wikibook.gallery.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Base64;

public class HashUtils {

    public static String generateSalt(int size) {
        char[] resultArr = new char[size];
        Random random = new Random();

        String options = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "1234567890" + "!@#$%^&*()";

        for (int i = 0; i < resultArr.length; i++) {
            resultArr[i] = options.charAt(random.nextInt(options.length()));
        }

        return new String(resultArr);
    }

    public static String generateHash(String value, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            String passwordSalted = value + salt;

            byte[] hashBytes = md.digest(passwordSalted.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("해싱 중 오류가 발생했습니다.");
        }
    }
}
