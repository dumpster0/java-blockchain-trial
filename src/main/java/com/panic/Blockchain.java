package com.panic;

import java.util.ArrayList;

public class Blockchain {
    //tracks the complete blockchain data structure

    //blockchain as arraylist of blocks
    public static ArrayList<Block> blockchain = new ArrayList<Block>();

    //add new block
    public static void addBlock(String data) {
        String prevHash = "0"; //assign preHash 0 by default

        //assign prevHash the hash of the previous block if the previous block exists
        //so prevHash remains 0 only for the genesis block
        if(blockchain.size() > 0) {
            prevHash = blockchain.get(blockchain.size() - 1).hash;
        }

        Block newBlock = new Block(data, prevHash);
        newBlock.mineBlock();
        blockchain.add(newBlock);
    }

    public static Boolean validateChain() {
        Block currBlock;
        Block prevBlock;
        int diff = Constants.difficulty;

        //loop over entire blockchain
        for(int i = 1; i < blockchain.size(); ++i) {
            prevBlock = blockchain.get(i - 1);
            currBlock = blockchain.get(i);

            //check if the given value of hash in the current block really is the hash
            //of the block
            if(!(currBlock.hash.equals(currBlock.calcHash()))) {
                System.out.println("Block number " + i + "'s hash incorrect");
                return false;
            }

            //check if the given value of prevHash in the current block really is the hash
            //of the previous block
            if(!(currBlock.prevHash.equals(prevBlock.hash))) {
                System.out.println("Block number " + i + "'s prevHash not equal to block number " + (i-1) + "'s hash");
                return false;
            }

            //check if block has really been mined
            //not necessary since addBlock only adds a block after mining it, but meh
            String target = new String(new char[diff]).replace('\0', '0');
            if(!target.equals(currBlock.hash.substring(0, diff))) {
                System.out.println("Block number " + i + " is unmined");
                return false;
            }
        }

        return true;
    }
}
