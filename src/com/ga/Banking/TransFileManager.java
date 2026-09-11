package com.ga.Banking;

import java.io.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
            writer.write("Account:" + transaction.getAccountId() + "\n"); // جديد
            writer.write("Amount:" + transaction.getAmount() + "\n");
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
            System.out.println("No Customers Found .");
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

}