package com.insight.backend;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class DatabaseSeederApplication {

    public static void main(String[] args) {
        // Ihre Seeding-Logik hier
        System.out.println("Seeding the database...");
        
        // CSV-Daten einlesen
        List<Object[]> categories = readCSV("fixtures/dummy-categories.csv");

        // Ausgabe der eingelesenen Daten
        for (Object[] entry : categories) {
            System.out.println(entry[0] + ", Value: " + entry[1]);
        }
		
        List<Object[]> questions = readCSV("fixtures/dummy-questions.csv");
        for (Object[] entry : questions) {
            System.out.println("ID: " + entry[0] + ", Value: " + entry[1]);
        }

        System.out.println("Database seeded.");
    }

    public static List<Object[]> readCSV(String csvFile) {
        String line;
        String csvSplitBy = ",";

        List<Object[]> data = new ArrayList<>();

        try {
            Resource resource = new ClassPathResource(csvFile);
            FileInputStream file = new FileInputStream(resource.getFile());

            try (BufferedReader br = new BufferedReader(new InputStreamReader(file))) {
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(csvSplitBy);

                    if (isInteger(columns[0])) {
                        data.add(new Object[]{Integer.parseInt(columns[0]), columns[1]});
                    } else if (isFloat(columns[0]) && !columns[1].isEmpty()) {
                        data.add(new Object[]{Float.parseFloat(columns[0]), columns[1]});
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
