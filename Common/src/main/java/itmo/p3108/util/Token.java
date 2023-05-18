package itmo.p3108.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Token {
    public static String encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            return no.toString(16).substring(1, 35);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
