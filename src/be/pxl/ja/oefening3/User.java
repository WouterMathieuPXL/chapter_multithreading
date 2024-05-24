package be.pxl.ja.oefening3;

import java.io.IOException;
import java.util.Random;

public class User extends Thread {
    String name;
    BankAccount bankAccount;
    int numberOfTransactions;
    int limitValue;
    Random rand = new Random();
    int completedTransactions = 0;

    public User(String name, BankAccount bankAccount, int numberOfTransactions, int limitValue) {
        this.name = name;
        this.bankAccount = bankAccount;
        this.numberOfTransactions = numberOfTransactions;
        this.limitValue = limitValue;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfTransactions; i++) {
            try {
                if (rand.nextDouble() < 0.5) {
                    bankAccount.withdraw(rand.nextInt(limitValue), name);
                } else {
                    bankAccount.deposit(rand.nextInt(limitValue), name);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            completedTransactions++;

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}