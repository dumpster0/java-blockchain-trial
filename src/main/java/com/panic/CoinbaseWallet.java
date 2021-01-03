package com.panic;

import java.security.PrivateKey;

//created inherited CoinbaseWallet since the genesis block needs to be manually signed by the origin wallet
//for this the origin wallet's private key needs to be public
public class CoinbaseWallet extends Wallet{
    public PrivateKey privateKey = getPrivateKey();
}
