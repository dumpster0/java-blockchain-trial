package com.panic;

import java.security.Security;

public class Main {

    public static void main(String[] args) {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Wallet a = new Wallet();
        Wallet b = new Wallet();
        Wallet c = new Wallet();
        CoinbaseWallet coinbase = new CoinbaseWallet();     //origin wallet used to carry out genesis transaction

        //genesis transaction has to be created manually since the global UTXOs set is empty to start with (using coinbase.send would fail)
        //would be cleaner to override Wallet.send in the inherited CoinbaseWallet to handle this, but screw it
        Transaction genesisTransaction = new Transaction(coinbase.pubkey, a.pubkey, 100f, null);
        genesisTransaction.generateSignature(coinbase.privateKey);
        genesisTransaction.transactionId = "0";
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.receiver, genesisTransaction.amount, genesisTransaction.transactionId));;
        Blockchain.UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

        Block genesisBlock = new Block("0");
        genesisBlock.addTransaction(genesisTransaction);
        Blockchain.addBlock(genesisBlock);

        Block block1 = new Block(genesisBlock.hash);
        System.out.println("\nA's balance is: " + a.getBalance());
        block1.addTransaction(a.send(b.pubkey, 40f));
        block1.addTransaction(a.send(c.pubkey, 30f));
        Blockchain.addBlock(block1);
        System.out.println("\nA's balance is: " + a.getBalance());
        System.out.println("B's balance is: " + b.getBalance());
        System.out.println("C's balance is: " + b.getBalance());
    }
}
