package com.panic;

import java.util.Date;

public class Block {
    //basic block data structure

    public String hash;     //current block's hash
    public String prevHash;     //previous block's hash
    private final String data;
    private final long timestamp;
    private int nonce;  //used to facilitate mining

    public Block (String data, String prevHash) {
        this.data = data;
        this.prevHash = prevHash;
        this.timestamp = new Date().getTime();
        this.hash = calcHash();
    }

    //calculate block's hash
    public String calcHash() {
        return StringUtils.sha256(prevHash + Long.toString(timestamp) + Integer.toString(nonce) + data);
    }

    //since prevHash, data, and timestamp are set and unchanged, we need a number that can be changed
    //multiple times so the hash calculated in every iteration is different
    //for this we use nonce
    public void mineBlock() {
        String target = new String(new char[Constants.difficulty]).replace('\0', '0');
        while (!hash.substring(0, Constants.difficulty).equals(target)) {
            ++nonce;
            hash = calcHash();
        }
        System.out.println("mined");
    }
}
