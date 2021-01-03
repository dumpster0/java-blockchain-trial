package com.panic;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public PublicKey pubkey;
    private PrivateKey privateKey;

    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<>();   //UTXOs held by the wallet

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

    //get balance held by wallet by iterating through the universal UTXOs set and checking if the UTXO belongs to the wallet
    //if it does, the UTXO is added to a local UTXOs set that tracks the UTXOs held b wallet
    public float getBalance() {
        float total = 0;
        for(Map.Entry<String, TransactionOutput> entry : Blockchain.UTXOs.entrySet()) {
            TransactionOutput UTXO = entry.getValue();
            if(UTXO.checkOwnership(pubkey)) {
                UTXOs.put(UTXO.id, UTXO);
                total += UTXO.amount;
            }
        }
        return total;
    }

    //create new transaction sending some amount to a receiver
    public Transaction send(PublicKey receiver, float amount) {
        if (getBalance() < amount) {
            System.out.println("Not enough funds");
            return null;
        }

        ArrayList<TransactionInput> inputs = new ArrayList<>(); //list of inputs to be given to new transaction

        //loop over local UTXOs set and add UTXOs to input until the total reaches the amount to be sent
        float total = 0;
        for(Map.Entry<String, TransactionOutput> entry : UTXOs.entrySet()) {
            TransactionOutput UTXO = entry.getValue();
                total += UTXO.amount;
                inputs.add(new TransactionInput(UTXO.id));
                if(total > amount) {
                    break;
                }
        }

        //create the new transaction and sign using private key
        Transaction newTransact = new Transaction(pubkey, receiver, amount, inputs);
        newTransact.generateSignature(privateKey);

        //remove inputs sent to new transaction from UTXOs (since they have now been spent
        for(TransactionInput input : inputs) {
            UTXOs.remove(input.transactionOutputId);
        }

        return newTransact;
    }

    //to be used by CoinbaseWallet
    protected PrivateKey getPrivateKey() {
        return privateKey;
    }

}
