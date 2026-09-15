package com.ga.Banking;

public class Banker extends User {
    protected String cpr;

    public Banker(String id, String name, String password, String cpr) {
        super(id, name, PasswordEncryptor.encrypt(password));
        this.cpr = cpr;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getFileName() {
        return "Banker-" + getName() + "-" + getId();
    }


public static void main(String[] args) {
    Banker banker = new Banker("1", "Admin", "123456", "123");
    BankerFileManager.saveBanker(banker);
    System.out.println(banker.getFileName());
}
}
