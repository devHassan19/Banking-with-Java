package com.ga.Banking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class readFile {
    public static void main(String[] args) {

        String fileName = "data/Customer-123123-jhhhgf.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error !! : " + e.getMessage());
        }
    }
}