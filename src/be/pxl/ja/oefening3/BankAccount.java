package be.pxl.ja.oefening3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class BankAccount {
    private final BufferedWriter logger;
    private final String accountNumber;
    private final AtomicInteger balance;
    private final AtomicInteger totalIn = new AtomicInteger(0);
    private final AtomicInteger totalOut = new AtomicInteger(0);

    public BankAccount(String accountNumber, int initialBalance, BufferedWriter logger) {
        this.accountNumber = accountNumber;
        this.balance = new AtomicInteger(initialBalance);
        this.logger = logger;
    }

    public void deposit(int amount, String user) throws IOException {
        this.balance.getAndAdd(amount);
        this.totalIn.getAndAdd(amount);
        logger.write(user + " deposited " + amount + "\n");
    }

    public void withdraw(int amount, String user) throws IOException {
        if (amount <= balance.get()) {
            this.balance.getAndAdd(-amount);
            this.totalOut.getAndAdd(amount);
        } else {
            amount = 0;
        }
        logger.write(user + " withdrawn " + amount + "\n");
    }

    public int getTotalIn() {
        return totalIn.get();
    }

    public int getTotalOut() {
        return totalOut.get();
    }

    public double getBalance() {
        return balance.get();
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
