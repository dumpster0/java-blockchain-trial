package com.panic;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

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

    //sign the given data with the given private private key (Elliptic Curve DSA)
    public static byte[] applyECDSASig(PrivateKey privatekey, String data) {
        byte[] output = new byte[0];

        try {
            Signature dsa = Signature.getInstance("ECDSA");
            dsa.initSign(privatekey);
            dsa.update(data.getBytes());
            output = dsa.sign();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    //verify data wrt the given pubKey and signature
    public static boolean verifyECDSASig(PublicKey pubKey, String data, byte[] signed) {
        try {
            Signature dsaVerify = Signature.getInstance("ECDSA");
            dsaVerify.initVerify(pubKey);
            dsaVerify.update(data.getBytes());
            return dsaVerify.verify(signed);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String keyToString(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    
}
