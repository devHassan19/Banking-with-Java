package com.ga.Banking;

import java.util.ArrayList;

public class Customer extends User {
    protected String cpr;
    private Card card;
    private ArrayList<Account> accounts;

    public Customer(String id, String name, String password, String cpr , Card card) {
        super(id, name, PasswordEncryptor.encrypt(password));
        this.cpr = cpr;
        this.card = card;
        accounts = new ArrayList<>();
        String sav = "Sav-" + getId();
        String chek = "Chek-" + getId();
        Account savingsAccount = new Saving(sav, this , card);
        Account checkingAccount = new Checking(chek, this , card);
        accounts.add(savingsAccount);
        accounts.add(checkingAccount);
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public String getFileName() {
        return "Customer-" + getName() + "-" + getId();
    }

    public Card getCard() {
        return card;
    }

    public void fetchAccounts() {
        for (Account account : accounts) {
            account.getAcountId();
        }
    }
    public static void main(String[] args) {
//        Customer customer = new Customer("152202020.2", "customer", "password", "cpr");
//        for (Account account : customer.getAccounts()) {
//            System.out.println(account.getAcountId());





        }


}

