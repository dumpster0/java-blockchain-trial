package com.panic;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class Blockchain {
    //tracks the complete blockchain data structure

    //blockchain as arraylist of blocks
    public static ArrayList<Block> blockchain = new ArrayList<>();
    //hashmap mapping transaction ids to unspent transaction outputs
    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<>();

    //add new block
    public static void addBlock(Block block) {
        block.mineBlock();
        blockchain.add(block);
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

            //loop over and verify each transaction in the current block
            for(int j = 0; j < currBlock.transactions.size(); ++j) {
                Transaction currTransaction = currBlock.transactions.get(j);

                if(!currTransaction.verifySignature()) {
                    System.out.println("Signature on transaction " + j + " invalid");
                    return false;
                }

                if(currTransaction.totalInput() != currTransaction.totalOutput()) {
                    System.out.println("Inputs not equal to outputs on transaction " + j);
                }

                //TODO : add other checks
            }
        }

        return true;
    }

    public static void displayChain() {
        for (Block block : blockchain) {
            String serial = new GsonBuilder().setPrettyPrinting().create().toJson(block);
            System.out.println(serial);
        }
    }
}
