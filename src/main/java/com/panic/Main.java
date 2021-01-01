package com.panic;

import com.google.gson.GsonBuilder;

public class Main {

    public static void main(String[] args) {
        Blockchain.addBlock("genesis");
        String serial = new GsonBuilder().setPrettyPrinting().create().toJson(Blockchain.blockchain.get(0));
        System.out.println(serial);
    }
}
