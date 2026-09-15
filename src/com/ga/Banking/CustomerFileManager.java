package com.ga.Banking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
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

            // Saving Account Card
            Account savingAccount = customer.getAccounts().get(0);
            writer.write("SavingCardNumber:" +
                    savingAccount.getCard().getCardNumber() + "\n");
            writer.write("SavingCardType:" +
                    savingAccount.getCard().getCardType().name() + "\n");


            // Checking Account Card
            Account checkingAccount = customer.getAccounts().get(1);
            writer.write("CheckingCardNumber:" +
                    checkingAccount.getCard().getCardNumber() + "\n");
            writer.write("CheckingCardType:" +
                    checkingAccount.getCard().getCardType().name() + "\n");

            for (Account account : customer.getAccounts()) {
                writer.write("Account:" +
                        account.getAcountId() + ":" +
                        account.getBalance() + "\n");
            }
            writer.write("Account Status:" + customer.getAccounts().getFirst().isActive() + "\n");

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

        if (files == null) {
            return Optional.empty();
        }

        for (File file : files) {

            try (BufferedReader reader =
                         new BufferedReader(new FileReader(file))) {

                String line;

                String name = null;
                String password = null;
                String custId = null;
                String custCpr = null;

                String savingCardNumber = null;
                String checkingCardNumber = null;

                CardType saveCard = null;
                CardType chekCard = null;

                java.util.Map<String, Double> balances =
                        new java.util.HashMap<>();


                while ((line = reader.readLine()) != null) {

                    if (line.startsWith("ID:")) {

                        custId = line.substring("ID:".length()).trim();

                    } else if (line.startsWith("Name:")) {

                        name = line.substring("Name:".length()).trim();

                    } else if (line.startsWith("Password:")) {

                        password = line.substring("Password:".length()).trim();

                    } else if (line.startsWith("CPR:")) {

                        custCpr = line.substring("CPR:".length()).trim();

                    } else if (line.startsWith("SavingCardNumber:")) {

                        savingCardNumber =
                                line.substring("SavingCardNumber:".length()).trim();

                    } else if (line.startsWith("SavingCardType:")) {

                        String cardType =
                                line.substring("SavingCardType:".length()).trim();

                        saveCard = CardType.valueOf(cardType);

                    } else if (line.startsWith("CheckingCardNumber:")) {

                        checkingCardNumber =
                                line.substring("CheckingCardNumber:".length()).trim();

                    } else if (line.startsWith("CheckingCardType:")) {

                        String cardType =
                                line.substring("CheckingCardType:".length()).trim();

                        chekCard = CardType.valueOf(cardType);

                    } else if (line.startsWith("Account:")) {

                        String[] parts =
                                line.substring("Account:".length()).split(":");

                        if (parts.length == 2) {

                            balances.put(
                                    parts[0].trim(),
                                    Double.parseDouble(parts[1].trim())
                            );
                        }
                    }
                }


                // Check CPR
                if (custCpr != null && custCpr.equals(cpr)) {

                    if (custId != null &&
                            name != null &&
                            password != null &&
                            savingCardNumber != null &&
                            checkingCardNumber != null &&
                            saveCard != null &&
                            chekCard != null) {


                        // Create Customer
                        Customer customer = new Customer(
                                custId,
                                name,
                                "temp",
                                custCpr,
                                saveCard,
                                chekCard
                        );


                        // Restore encrypted password
                        customer.setEncryptedPassword(password);


                        // Restore account balances
                        for (Account acc : customer.getAccounts()) {

                            Double bal =
                                    balances.get(acc.getAcountId());

                            if (bal != null) {
                                acc.restoreBalance(bal);
                            }
                        }


                        return Optional.of(customer);
                    }
                }

            } catch (IOException e) {

                System.out.println(
                        "Error reading file: " + e.getMessage()
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid CardType in file: " + file.getName()
                );
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

                }
            }
        }

        return String.format("Cus-%03d", maxNumber + 1);
    }

    public static long genCardNum() {
        return 1000000000000000L + (long) (Math.random() * 9000000000000000L);
    }

    //    For Testing only
    public static void removeAllCustomers() {
        File dir = new File(DIRECTORY);

        File[] files = dir.listFiles((d, name) ->
                name.startsWith("Customer-") && name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No Customers Found");
            return;
        }

        for (File file : files) {
            if (file.delete()) {
                System.out.println("Deleted: " + file.getName());
            } else {
                System.out.println("Failed to delete: " + file.getName());
            }
        }

        System.out.println("All Customers Deleted");
    }

    public static ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();

        File dir = new File(DIRECTORY);

        File[] files = dir.listFiles((d, name) ->
                name.startsWith("Customer-") && name.endsWith(".txt"));

        if (files == null) {
            return customers;
        }

        for (File file : files) {

            try (BufferedReader reader =
                         new BufferedReader(new FileReader(file))) {

                String line;

                String name = null;
                String password = null;
                String custId = null;
                String custCpr = null;

                String savingCardNumber = null;
                String checkingCardNumber = null;

                CardType saveCard = null;
                CardType chekCard = null;

                java.util.Map<String, Double> balances =
                        new java.util.HashMap<>();

                while ((line = reader.readLine()) != null) {

                    if (line.startsWith("ID:")) {
                        custId = line.substring("ID:".length()).trim();

                    } else if (line.startsWith("Name:")) {
                        name = line.substring("Name:".length()).trim();

                    } else if (line.startsWith("Password:")) {
                        password = line.substring("Password:".length()).trim();

                    } else if (line.startsWith("CPR:")) {
                        custCpr = line.substring("CPR:".length()).trim();

                    } else if (line.startsWith("SavingCardNumber:")) {
                        savingCardNumber =
                                line.substring("SavingCardNumber:".length()).trim();

                    } else if (line.startsWith("SavingCardType:")) {
                        saveCard = CardType.valueOf(
                                line.substring("SavingCardType:".length()).trim()
                        );

                    } else if (line.startsWith("CheckingCardNumber:")) {
                        checkingCardNumber =
                                line.substring("CheckingCardNumber:".length()).trim();

                    } else if (line.startsWith("CheckingCardType:")) {
                        chekCard = CardType.valueOf(
                                line.substring("CheckingCardType:".length()).trim()
                        );

                    } else if (line.startsWith("Account:")) {

                        String[] parts =
                                line.substring("Account:".length()).split(":");

                        if (parts.length == 2) {
                            balances.put(
                                    parts[0].trim(),
                                    Double.parseDouble(parts[1].trim())
                            );
                        }
                    }
                }

                if (custId != null &&
                        name != null &&
                        password != null &&
                        custCpr != null &&
                        savingCardNumber != null &&
                        checkingCardNumber != null &&
                        saveCard != null &&
                        chekCard != null) {

                    Customer customer = new Customer(
                            custId,
                            name,
                            "temp",
                            custCpr,
                            saveCard,
                            chekCard
                    );

                    customer.setEncryptedPassword(password);

                    for (Account acc : customer.getAccounts()) {

                        Double balance =
                                balances.get(acc.getAcountId());

                        if (balance != null) {
                            acc.restoreBalance(balance);
                        }
                    }

                    customers.add(customer);
                }

            } catch (IOException | IllegalArgumentException e) {
                System.out.println(
                        "Error reading customer: " + file.getName()
                );
            }
        }

        return customers;
    }

//    public static void main(String[] args) {
//        Long x;
//        x = genCardNum();
//        System.out.println(x);
//    }
}
