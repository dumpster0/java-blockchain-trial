package com.panic;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {
    public PublicKey pubkey;
    private PrivateKey privateKey;

    public Wallet() {
        generateKeyPair();
    }

    //generate a random key pair
    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA");    //get instance of ECDSA keypair generator
            SecureRandom randomGen = SecureRandom.getInstance("SHA1PRNG");  //cryptographically secure random number generator (more unpredictable)
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");   //specifications for generating elliptic curve parameters

            keyGen.initialize(ecSpec, randomGen);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            pubkey = keyPair.getPublic();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
