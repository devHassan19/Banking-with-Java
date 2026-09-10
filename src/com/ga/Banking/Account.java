package com.ga.Banking;

public abstract class Account {
    protected String AcountId;
    protected double balance;
    protected Customer owner;

    public Account(String acountId, Customer owner) {
        AcountId = acountId;
        this.balance = 0;
        this.owner = owner;
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
}
