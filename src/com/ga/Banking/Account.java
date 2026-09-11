package com.ga.Banking;

import java.util.ArrayList;

public abstract class Account {
    protected String AcountId;
    protected double balance;
    protected Customer owner;
    private ArrayList<Transactions> transactions;

    public Account(String acountId, Customer owner) {
        AcountId = acountId;
        this.balance = 0;
        this.owner = owner;
        this.transactions = new ArrayList<>();
    }

    public Account(String acountId, ArrayList<Transactions> transactions) {
        AcountId = acountId;
        this.transactions = transactions;
        this.balance = 0;
    }

    public String getAcountId() {
        return AcountId;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getOwner() {
        return owner;
    }

    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return false;
        }

        this.balance += amount;
        Transactions transaction = new Transactions("Deposit", amount,this.AcountId);
        transactions.add(transaction);
        TransFileManager.saveTransaction(owner, transaction);

        System.out.println("Deposit successful. New balance: " + this.balance);
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return false;
        }
        if (amount > this.balance) {
            System.out.println("Insufficient balance.");
            return false;
        }

        this.balance -= amount;
        Transactions transaction = new Transactions("Withdraw", amount, this.AcountId);
        transactions.add(transaction);
        TransFileManager.saveTransaction(owner, transaction);

        System.out.println("Withdraw successful. New balance: " + this.balance);
        return true;
    }

    public boolean transferFunds(double amount, Account account) {
        if (account == this) {
            System.out.println("Cannot transfer to the same account.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return false;
        }
        if (amount > this.balance) {
            System.out.println("Insufficient balance.");
            return false;
        }

        this.balance -= amount;
        account.deposit(amount);

        Transactions transaction = new Transactions("TransferFunds", amount, this.AcountId);
        transactions.add(transaction);
        TransFileManager.saveTransaction(owner, transaction);

        System.out.println("Transfer successful.");
        return true;
    }

    public void restoreBalance(double balance) {
        this.balance = balance;
    }

    public void printTransactions() {
        for (Transactions transaction : transactions) {
            System.out.println(transaction);
        }
    }
    public static void main(String[] args) {
        Customer customer7 = new Customer("123123123", "customer", "password", "cpr");
        Account ss = new Saving(customer7.getId() , customer7);
        Account ch = new Checking(customer7.getId() , customer7);

        System.out.println(ss.getBalance());
        System.out.println(ch.getBalance());
        ss.deposit(10);
        System.out.println(ss.getBalance());
        ss.transferFunds(5,ch);
        System.out.println(ss.getBalance());
        System.out.println(ch.getBalance());

        ss.printTransactions();
        System.out.println("****************");
        ch.printTransactions();

    }

}
