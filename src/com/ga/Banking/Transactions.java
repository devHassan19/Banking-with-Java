package com.ga.Banking;

//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transactions {
    private String operation;
    private double amount;
    private LocalDateTime date;
    private String accountId;

    public Transactions(String operation, double amount, String accountId) {
        this.operation = operation;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.accountId = accountId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public double getAmount() {
        return amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return "Transactions{" +
                "operation='" + operation + '\'' +
                ", amount=" + amount +
                ", account='" + accountId + '\'' +
                ", date=" + date.format(formatter) +
                '}';
    }


}
