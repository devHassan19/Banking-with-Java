package com.ga.Banking;

import java.util.ArrayList;

public class Customer extends User {
    protected String cpr;
    //    private Card card;
    private CardType saveCard;
    private CardType chekCard;
    private ArrayList<Account> accounts;

    public Customer(String id, String name, String password, String cpr, CardType saveCard, CardType chekCard) {
        super(id, name, PasswordEncryptor.encrypt(password));
        this.cpr = cpr;
        this.saveCard = saveCard;
        this.chekCard = chekCard;
        accounts = new ArrayList<>();
        String sav = "Sav-" + getId();
        String chek = "Chek-" + getId();
        Long saveNum = CustomerFileManager.genCardNum();
        Long chekNum = CustomerFileManager.genCardNum();
        Card saveing = new Card("Card Num: " + saveNum, saveCard);
        Card checking = new Card("Card Num: " + chekNum, chekCard);
        Account savingsAccount = new Saving(sav, this, saveing);
        Account checkingAccount = new Checking(chek, this, checking);
        accounts.add(savingsAccount);
        accounts.add(checkingAccount);
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public CardType getSaveCard() {
        return saveCard;
    }

    public CardType getChekCard() {
        return chekCard;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public String getFileName() {
        return "Customer-" + getName() + "-" + getId();
    }

//    public Card getCard() {
//        return card;
//    }


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

