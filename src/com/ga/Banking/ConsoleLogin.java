package com.ga.Banking;

import java.util.Scanner;

public class ConsoleLogin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== Bank System =====");
            System.out.println("1- Login");
            System.out.println("2- Create Account)");
            System.out.println("3- Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("Login");
                    System.out.println("This Features under maintenance");
                    System.out.println("See you Soon");
                    running = false;
                    break;


                case "2":
                    System.out.println("\n===== Choose Account =====");
                    System.out.println("1- Customer");
                    System.out.println("2- Banker");
                    System.out.print("Choice: ");
                    String userType = scanner.nextLine().trim();

                    if (userType.equals("1")) {
                        System.out.println("\n===== Create New Customer =====");

                        System.out.print("Enter Your ID: ");
                        String id = scanner.nextLine().trim();

                        System.out.print("Enter Your Name:");
                        String name = scanner.nextLine().trim();

                        System.out.print("Enter Your Password: ");
                        String password = scanner.nextLine().trim();

                        Customer newCustomer = new Customer(id, name, password);
                        CustomerFileManager.saveCustomer(newCustomer);

                        System.out.println("Created New Customer successfully : !");
                        System.out.println("File name: " + newCustomer.getFileName() + ".txt");

                    } else if (userType.equals("2")) {
                        System.out.println("Banker");
                        System.out.println("This Features under maintenance");
                        System.out.println("See you Soon");
                        running = false;

                    } else {
                        System.out.println("Invalid choice.");
                    }
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
}