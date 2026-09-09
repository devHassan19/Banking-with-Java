package com.ga.Banking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class CustomerFileManager {

    private static final String DIRECTORY = "data/customers/";

    public static void saveCustomer(Customer customer) {
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = DIRECTORY + customer.getFileName() + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("ID:" + customer.getId() + "\n");
            writer.write("Name:" + customer.getName() + "\n");
            writer.write("Password:" + customer.getPassword() + "\n");
        } catch (IOException e) {
            System.out.println("Error !! : " + e.getMessage());
        }
    }

    public static void printAllCustomers() {
        File dir = new File(DIRECTORY);
        File[] files = dir.listFiles((d, name) -> name.startsWith("Customer-") && name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No Customers Found");
            return;
        }

        System.out.println("===== Customers List =====");
        for (File file : files) {
            System.out.println("\nFile: " + file.getName());
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
}
