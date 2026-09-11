package com.ga.Banking;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleLogin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== Bank System =====");
            System.out.println("1- Login");
            System.out.println("2- Create New Account");
            System.out.println("3- Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter CPR: ");
                    String loginCpr = scanner.nextLine().trim();

                    Optional<Customer> foundCustomer = CustomerFileManager.findCustomerByCpr(loginCpr);
//                    Optional<Banker> foundBanker = BankerFileManager.findBankerById(loginId);

                    if (foundCustomer.isPresent()) {
                        Customer loggedCustomer = foundCustomer.get();

                        int attempts = 5;

                        while (attempts > 0) {

                            System.out.print("Enter Password: ");
                            String inputPassword = scanner.nextLine().trim();

                            if (loggedCustomer.checkPassword(inputPassword)) {

                                System.out.println(
                                        "Login successful! Welcome " + loggedCustomer.getName()
                                );

                                showCustomerMenu(loggedCustomer, scanner);
                                break;

                            } else {

                                attempts--;

                                System.out.println("Incorrect password!");

                                if (attempts > 0) {
                                    System.out.println(
                                            "Try again! You have " + attempts + " attempts left."
                                    );
                                } else {
                                    System.out.println("Too many incorrect attempts!");
                                }
                            }
                        }


//                    } else if (foundBanker.isPresent()) {
//                        Banker loggedBanker = foundBanker.get();
//
//                        System.out.print("Enter Password: ");
//                        String inputPassword = scanner.nextLine().trim();
//
//                        if (loggedBanker.checkPassword(inputPassword)) {
//                            System.out.println("Login successful! Welcome " + loggedBanker.getName());
//                            showBankerMenu(loggedBanker, scanner);
//                        } else {
//                            System.out.println("Incorrect password!");
//                        }

                    } else {
                        System.out.println("User not found!");
                    }
                    break;


                case "2":
//                    System.out.println("\n===== Choose Account =====");
//                    System.out.println("1- Customer");
//                    System.out.println("2- Banker");
//                    System.out.print("Choice: ");
//                    String userType = scanner.nextLine().trim();
//
//                    if (userType.equals("1")) {
                    System.out.println("\n===== Create New Customer =====");

                    System.out.print("Enter Your CPR: ");
                    String cpr = scanner.nextLine().trim();

                    System.out.print("Enter Your Name:");
                    String name = scanner.nextLine().trim();

                    System.out.print("Enter Your Password: ");
                    String password = scanner.nextLine().trim();

                    String id = CustomerFileManager.generateNewId();

                    Customer newCustomer = new Customer(id, name, password, cpr);
                    CustomerFileManager.saveCustomer(newCustomer);

                    System.out.println("Created New Customer successfully : !");
//                    System.out.println("File name: " + newCustomer.getFileName() + ".txt");

//                    } else if (userType.equals("2")) {
//                        System.out.println("Banker");
//                        System.out.println("This Features under maintenance");
//                        System.out.println("See you Soon");
//                        running = false;

//                    } else {
//                        System.out.println("Invalid choice.");
//                    }
                    break;

                case "3":
                    System.out.println("Exit !! .. GoodBey ");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    private static void showCustomerMenu(Customer customer, Scanner scanner) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("1- Withdraw");
            System.out.println("2- Deposit");
            System.out.println("3- Transfer");
            System.out.println("4- View Transactions");
            System.out.println("5- Logout");
            System.out.print("Choose: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1": {
                    Account acc = selectAccount(customer, scanner);
                    if (acc == null) {
                        System.out.println("Invalid account selection.");
                        break;
                    }
                    System.out.print("Enter amount to withdraw: ");
                    double amount = parseAmount(scanner.nextLine().trim());

                    if (acc.withdraw(amount)) {
                        CustomerFileManager.saveCustomer(customer);
                    }
                    break;
                }
                case "2": {
                    Account acc = selectAccount(customer, scanner);
                    if (acc == null) {
                        System.out.println("Invalid account selection.");
                        break;
                    }
                    System.out.print("Enter amount to deposit: ");
                    double amount = parseAmount(scanner.nextLine().trim());

                    if (acc.deposit(amount)) {
                        CustomerFileManager.saveCustomer(customer);
                    }
                    break;
                }
                case "3": {
                    System.out.println("-- Source account --");
                    Account from = selectAccount(customer, scanner);
                    if (from == null) {
                        System.out.println("Invalid account selection.");
                        break;
                    }
                    System.out.println("-- Destination account --");
                    Account to = selectAccount(customer, scanner);
                    if (to == null) {
                        System.out.println("Invalid account selection.");
                        break;
                    }
                    System.out.print("Enter amount to transfer: ");
                    double amount = parseAmount(scanner.nextLine().trim());

                    if (from.transferFunds(amount, to)) {
                        CustomerFileManager.saveCustomer(customer);
                    }
                    break;
                }
                case "4":
                    TransFileManager.printCustomerTransactions(customer);
                    break;
                case "5":
                    System.out.println("Logged out successfully.");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static double parseAmount(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void showBankerMenu(Banker banker, Scanner scanner) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n===== Banker Menu =====");
            System.out.println("1- View Customer Accounts");
            System.out.println("2- Freeze/Unfreeze Account");
            System.out.println("3- Logout");
            System.out.print("Choose: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    System.out.println("View Customer Accounts - not implemented yet");
                    break;
                case "2":
                    System.out.println("Freeze/Unfreeze - not implemented yet");
                    break;
                case "3":
                    System.out.println("Logged out successfully.");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static Account selectAccount(Customer customer, Scanner scanner) {
        System.out.println("Choice:");
        System.out.println("1- Saving");
        System.out.println("2- Checking");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();

        for (Account acc : customer.getAccounts()) {
            if (choice.equals("1") && acc instanceof Saving) return acc;
            if (choice.equals("2") && acc instanceof Checking) return acc;
        }
        return null;
    }
}