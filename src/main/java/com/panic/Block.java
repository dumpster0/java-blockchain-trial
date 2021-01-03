package com.panic;

import java.util.ArrayList;
import java.util.Date;

public class Block {
    //basic block data structure

    public String hash;     //current block's hash
    public String prevHash;     //previous block's hash
    private String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<>();
    private final long timestamp;
    private int nonce;  //used to facilitate mining

    public Block (String prevHash) {
        this.prevHash = prevHash;
        this.timestamp = new Date().getTime();
        this.hash = calcHash();
    }

    //calculate block's hash
    public String calcHash() {
        return StringUtils.sha256(prevHash + Long.toString(timestamp) + Integer.toString(nonce) + merkleRoot);
    }

    //since prevHash, data, and timestamp are set and unchanged, we need a number that can be changed
    //multiple times so the hash calculated in every iteration is different
    //for this we use nonce
    public void mineBlock() {
        merkleRoot = StringUtils.getMerkleRoot(transactions);
        String target = new String(new char[Constants.difficulty]).replace('\0', '0');
        while (!hash.substring(0, Constants.difficulty).equals(target)) {
            ++nonce;
            hash = calcHash();
        }
        System.out.println("mined");
    }

    //add transaction to block
    public boolean addTransaction(Transaction transaction) {
        if(transaction == null) {
            return false;
        }

        if(prevHash != "0" && !transaction.processTransaction()) {
            System.out.println("Transaction failed to process");
            return false;
        }

        transactions.add(transaction);
        return true;
    }
}
