package com.PSapplication.PSapplication.Generator;

import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;
import com.PSapplication.PSapplication.Entities.NotaExamen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileGenerator implements FileGenerator {
    @Override
    public File generateFile(UserDetailsDTO user) {
        File csvFile = null;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_info.csv"))) {
            writer.write("Username,Email,Lista de note\n");
            writer.write(user.getUsername() + "," + user.getEmail() + ",");
            for (NotaExamen nota : user.getNotaExamenList()) {
                writer.write(nota.getCalificativ_examen().toString() + ";");
            }
            System.out.println("CSV file generated successfully.");
            csvFile = new File("user_info.csv");
        } catch (IOException e) {
            System.err.println("Error generating CSV file: " + e.getMessage());
        }
        return csvFile;
    }
}
