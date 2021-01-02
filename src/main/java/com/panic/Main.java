package com.panic;

import java.security.Security;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Blockchain.addBlock("genesis");

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Wallet a = new Wallet();
        Wallet b = new Wallet();
        System.out.println(StringUtils.keyToString(a.pubkey));
        System.out.println(StringUtils.keyToString(b.pubkey));

        Transaction trans = new Transaction(a.pubkey, b.pubkey, 70, null);
    }
}
