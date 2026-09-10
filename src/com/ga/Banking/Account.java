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

    public void deposit(double amount) {
        this.balance += amount;
        transactions.add(new Transactions("Deposit", amount));

    }


    public void withdraw(double amount) {
        this.balance -= amount;
        transactions.add(new Transactions("Withdraw", amount));
    }

    public void transferFunds(double amount , Account account) {
        this.balance -= amount;
        account.deposit(amount);
        transactions.add(new Transactions("TransferFunds", amount));

    }

    public void printTransactions() {
        for (Transactions transaction : transactions) {
            System.out.println(transaction);
        }
    }
    public static void main(String[] args) {
        Customer customer1 = new Customer("152202020.2", "customer", "password", "cpr");
        Account ss = new Saving(customer1.getId() , customer1);
        Account ch = new Checking(customer1.getId() , customer1);

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
