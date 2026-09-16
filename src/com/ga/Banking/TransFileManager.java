package com.ga.Banking;

import java.io.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TransFileManager {

    private static final String DIRECTORY = "data/transactions/";

    public static void saveTransaction(Customer customer, Transactions transaction) {

        File dir = new File(DIRECTORY);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = DIRECTORY + customer.getFileName() + "_transactions.txt";

        try (FileWriter writer = new FileWriter(fileName, true)) {

            writer.write("Operation:" + transaction.getOperation() + "\n");
            writer.write("Amount:" + transaction.getAmount() + "\n");
            writer.write("Account:" + transaction.getAccountId() + "\n"); // جديد
            writer.write("Balance:" + transaction.getBalance() + "\n");
            writer.write("Date:" + transaction.getDate().format(
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            ) + "\n");

            writer.write("-------------------------\n");

        } catch (IOException e) {
            System.out.println("Error !! : " + e.getMessage());
        }
    }

    public static void printCustomerTransactions(Customer customer) {
        String fileName = DIRECTORY + customer.getFileName() + "_transactions.txt";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("No Transactions Found .");
            return;
        }

        System.out.println("===== Transactions for " + customer.getName() + " =====");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error!: " + e.getMessage());
        }
    }

    public static ArrayList<Transactions> readTransactions(Customer customer) {

        ArrayList<Transactions> transactions = new ArrayList<>();

        String fileName = DIRECTORY + customer.getFileName() + "_transactions.txt";
        File file = new File(fileName);

        if (!file.exists()) {
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            String operation = null;
            double amount = 0;
            String accountId = null;
            double balance = 0;
            LocalDateTime date = null;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("Operation:")) {
                    operation = line.substring("Operation:".length()).trim();

                } else if (line.startsWith("Amount:")) {
                    amount = Double.parseDouble(
                            line.substring("Amount:".length()).trim()
                    );

                } else if (line.startsWith("Account:")) {
                    accountId = line.substring("Account:".length()).trim();

                } else if (line.startsWith("Balance:")) {
                    balance = Double.parseDouble(
                            line.substring("Balance:".length()).trim()
                    );

                } else if (line.startsWith("Date:")) {

                    date = LocalDateTime.parse(
                            line.substring("Date:".length()).trim(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    );

                } else if (line.startsWith("----------------")) {

                    Transactions transaction =
                            new Transactions(operation, amount, accountId, balance);

                    transaction.setDate(date);

                    transactions.add(transaction);
                }
            }

        } catch (IOException e) {
            System.out.println("Error!: " + e.getMessage());
        }

        return transactions;
    }


    public static void removeAllTransactions() {
        File dir = new File(DIRECTORY);

        File[] files = dir.listFiles((d, name) ->
                name.startsWith("Customer-") && name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No Transactions Found");
            return;
        }

        for (File file : files) {
            if (file.delete()) {
                System.out.println("Deleted: " + file.getName());
            } else {
                System.out.println("Failed to delete: " + file.getName());
            }
        }

        System.out.println("All Transactions Deleted");
    }

}