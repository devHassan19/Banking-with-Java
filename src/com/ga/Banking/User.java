package com.ga.Banking;

public abstract class User {
protected  String id;
protected  String name;
protected  String password;

    public User( String id , String name, String password) {
        this.name = name;
        this.password = password;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setEncryptedPassword(String encryptedPassword){
        this.password = encryptedPassword;
    }
    public boolean checkPassword(String password){
        String hashedInput = PasswordEncryptor.encrypt(password);
        return this.password.equals(hashedInput);
    }
}
