package com.panic;

public class TransactionInput {
    public String transactionOutputId;  //which transaction output is being used as input
    public TransactionOutput UTXO;  //UTXO for given transactionOutputId

    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }
}
