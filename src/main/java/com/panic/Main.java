package com.panic;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Blockchain.addBlock("genesis");

        //test repl
        Scanner inp = new Scanner(System.in);
        Scanner dataInput = new Scanner(System.in);
        boolean exit = false;
        int choice;
        while(!exit) {
            System.out.println("operation:\n\t1. Add Block\n\t2. Display Entire Chain\n\t3. Validate Chain\n\t4. Exit");
            choice = inp.nextInt();
            switch (choice) {
                case 1 : {
                    System.out.print("\nEnter data to be inserted: ");
                    String data = dataInput.nextLine();
                    Blockchain.addBlock(data);
                    break;
                }
                case 2 : {
                    Blockchain.displayChain();
                    break;
                }
                case 3 : {
                    if(Blockchain.validateChain()) {
                        System.out.println("Chain is valid");
                    }
                    else {
                        System.out.println("Chain is invalid");
                    }
                    break;
                }
                case 4 : {
                    exit = true;
                    break;
                }
                default : break;
            }
        }

    }
}
