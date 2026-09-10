package com.ga.Banking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Optional;
import java.util.Scanner;

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
            writer.write("CPR:" + customer.getCpr() + "\n");
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

    public static Optional<Customer> findCustomerByCpr(String cpr) {
        File dir = new File(DIRECTORY);
        File[] files = dir.listFiles((d, name) ->
                name.startsWith("Customer-") && name.endsWith(".txt"));

        if (files == null) return Optional.empty();

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;
                String name = null;
                String password = null;
                String custId = null;
                String custCpr = null;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("ID:")) {
                        custId = line.substring(3).trim();
                    } else if (line.startsWith("Name:")) {
                        name = line.substring(5).trim();
                    } else if (line.startsWith("Password:")) {
                        password = line.substring(9).trim();
                    } else if (line.startsWith("CPR:")) {
                        custCpr = line.substring(4).trim();
                    }
                }

                if (custCpr != null && custCpr.equals(cpr)) {

                    if (custId != null && name != null && password != null) {

                        Customer customer =
                                new Customer(custId, name, "temp", custCpr);

                        customer.setEncryptedPassword(password);

                        return Optional.of(customer);
                    }
                }

            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }

        return Optional.empty();
    }
    public static String generateNewId() {
        File dir = new File(DIRECTORY);

        int maxNumber = 0;

        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                try {
                    Scanner scanner = new Scanner(file);

                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine().trim();

                        if (line.startsWith("ID:Cus-")) {
                            String number = line.substring("ID:Cus-".length());

                            int idNumber = Integer.parseInt(number);

                            if (idNumber > maxNumber) {
                                maxNumber = idNumber;
                            }

                            break;
                        }
                    }

                    scanner.close();

                } catch (Exception e) {
                    // تجاهل الملفات التي فيها ID غير صحيح
                }
            }
        }

        return String.format("Cus-%03d", maxNumber + 1);
    }
}
