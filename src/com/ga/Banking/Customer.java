package com.ga.Banking;

public class Customer extends User {
    protected String cpr;

//    Customers may have a checking account, a savings account, or both.
//    Customers should be able to set a password for their account(s).

    public Customer(String id , String name, String password , String cpr) {
        super(id , name, PasswordEncryptor.encrypt(password));
        this.cpr = cpr;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getFileName() {
        return "Customer-" + getName() + "-" + getId();
    }
}
