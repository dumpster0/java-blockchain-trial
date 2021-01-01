package com.panic;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class StringUtils {
    //string related helper methods

    //return sha256 hash of input
    public static String sha256(String inp) {
        //try-catch needed since getInstance() throws exception
        try {
            MessageDigest digestInstance = MessageDigest.getInstance("SHA-256");
            byte[] hash = digestInstance.digest(inp.getBytes(StandardCharsets.UTF_8));

            BigInteger bigHash = new BigInteger(1, hash);
            StringBuilder hashString = new StringBuilder(bigHash.toString(16));

            //append leading zeros
            hashString.setCharAt(0, '0');
            while(hashString.length() < 64) {
                hashString.insert(0, "0");
            }

            return hashString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
