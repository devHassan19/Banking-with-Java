package com.ga.Banking;

//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transactions {
    private String operation;
    private double amount;
    private LocalDateTime date;

    public Transactions(String operation, double amount) {
        this.operation = operation;
        this.amount = amount;
        this.date = LocalDateTime.now();
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
                ", date=" + date.format(formatter) +
                '}';
    }


}
