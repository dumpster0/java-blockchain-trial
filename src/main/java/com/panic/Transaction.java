package com.panic;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
    public String transactionId;
    public PublicKey sender;
    public PublicKey receiver;
    public float amount;
    public byte[] signature;    //unique signature generated from both pubkeys and amount, used to verify the sender

    public ArrayList<TransactionInput> inputs = new ArrayList<>();  //inputs (UTXOs) into the transaction
    public ArrayList<TransactionOutput> outputs = new ArrayList<>();    //outputs from transaction

    private static int sequence = 0;

    public Transaction(PublicKey from, PublicKey to, float amount, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.receiver = to;
        this.amount = amount;
        this.inputs = inputs;
    }

    private String calcHash() {
        ++sequence;
        return StringUtils.sha256(StringUtils.keyToString(sender) +StringUtils.keyToString(receiver) + Float.toString(amount) + sequence);
    }

    //create signature using given privatekey
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtils.keyToString(sender) + StringUtils.keyToString(receiver) + Float.toString(amount);
        signature = StringUtils.applyECDSASig(privateKey, data);
    }

    //verify signature using sender pubkey and data
    public boolean verifySignature() {
        String data = StringUtils.keyToString(sender) + StringUtils.keyToString(receiver) + Float.toString(amount);
        return StringUtils.verifyECDSASig(sender, data, signature);
    }

    //go through transaction, check for validity, update outputs, update Blockchain.UTXOs
    public boolean processTransaction() {
        if (!verifySignature()) {
            System.out.println("Transaction signature not verified");
            return false;
        }

        //update the values of UTXO for each transactioninput in inputs
        for(TransactionInput i : inputs) {
            i.UTXO = Blockchain.UTXOs.get(i.transactionOutputId);
        }

        //TODO : continue here

        return true;
    }
}
