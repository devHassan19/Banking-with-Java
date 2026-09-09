package com.ga.Banking;

public class Customer extends User {

    public Customer(String name, String password, String id) {
        super(name, PasswordEncryptor.encrypt(password), id);
    }

    public String getFileName() {
        return "Customer-" + getName() + "-" + getId();
    }
}
