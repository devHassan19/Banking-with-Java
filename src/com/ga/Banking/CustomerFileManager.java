package com.ga.Banking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Optional;

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

    public static Optional<Customer> findCustomerById(String id) {
        File dir = new File(DIRECTORY);
        File[] files = dir.listFiles((d, name) -> name.startsWith("Customer-") && name.endsWith(".txt"));

        if (files == null) return Optional.empty();

        for (File file : files) {
            String fileName = file.getName().replace(".txt", "");
            String[] parts = fileName.split("-");

            if (parts.length == 3 && parts[2].equals(id)) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    String name = null, password = null, custId = null;

                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("ID:")) custId = line.substring(3);
                        else if (line.startsWith("Name:")) name = line.substring(5);
                        else if (line.startsWith("Password:")) password = line.substring(9);
                    }

                    if (custId != null && name != null && password != null) {
                        Customer customer = new Customer(custId, name, "temp");
                        customer.setEncryptedPassword(password);
                        return Optional.of(customer);
                    }

                } catch (IOException e) {
                    System.out.println("Error reading file: " + e.getMessage());
                }
            }
        }

        return Optional.empty();
    }

}
