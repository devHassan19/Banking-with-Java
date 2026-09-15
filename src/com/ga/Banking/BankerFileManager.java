package com.ga.Banking;

import java.io.*;
import java.util.Optional;

public class BankerFileManager {
    private static final String DIRECTORY = "data/banker/";

    public static void saveBanker(Banker banker) {
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = DIRECTORY + banker.getFileName() + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("ID:" + banker.getId() + "\n");
            writer.write("CPR:" + banker.getCpr() + "\n");
            writer.write("Name:" + banker.getName() + "\n");
            writer.write("Password:" + banker.getPassword() + "\n");

        } catch (IOException e) {
            System.out.println("Error !! : " + e.getMessage());
        }
    }

    public static Optional<Banker> findBankerByCpr(String cpr) {

        File dir = new File(DIRECTORY);

        File[] files = dir.listFiles((d, name) ->
                name.startsWith("Banker-") && name.endsWith(".txt"));

        if (files == null) {
            return Optional.empty();
        }

        for (File file : files) {

            try (BufferedReader reader =
                         new BufferedReader(new FileReader(file))) {

                String line;

                String name = null;
                String password = null;
                String bankerId = null;
                String bankerCpr = null;

                java.util.Map<String, Double> balances =
                        new java.util.HashMap<>();


                while ((line = reader.readLine()) != null) {

                    if (line.startsWith("ID:")) {

                        bankerId = line.substring("ID:".length()).trim();

                    } else if (line.startsWith("Name:")) {

                        name = line.substring("Name:".length()).trim();

                    } else if (line.startsWith("Password:")) {

                        password = line.substring("Password:".length()).trim();

                    } else if (line.startsWith("CPR:")) {

                        bankerCpr = line.substring("CPR:".length()).trim();
                    }
                }


                // Check CPR
                if (bankerCpr != null && bankerCpr.equals(cpr)) {

                    if (bankerId != null &&
                            name != null &&
                            password != null) {


                        // Create Banker
                        Banker banker = new Banker(
                                bankerId,
                                name,
                                "temp",
                                bankerCpr
                        );

                        // Restore encrypted password
                        banker.setEncryptedPassword(password);
                        return Optional.of(banker);
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

}
