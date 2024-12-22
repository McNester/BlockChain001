package blockchain001;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    private static byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String generateHash(String data){
        return MD5.generateHash(data);
        // return bytesToHex(digest(data.getBytes()));
    }

    
}
