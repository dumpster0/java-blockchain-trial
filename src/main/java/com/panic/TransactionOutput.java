package com.panic;

import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey receiver;
    public float amount;
    public String parentTransactionId;

    public TransactionOutput(PublicKey receiver, float amount, String parentTransactionId) {
        this.receiver = receiver;
        this.amount = amount;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtils.sha256(StringUtils.keyToString(receiver) + Float.toString(amount) + parentTransactionId);
    }

    public boolean checkOwnership(PublicKey pubkey) {
        return (receiver == pubkey);
    }
}