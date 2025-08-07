package com.PSapplication.PSapplication.Generator;

import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;
import com.PSapplication.PSapplication.Entities.NotaExamen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TxtFileGenerator implements FileGenerator {
    @Override
    public File generateFile(UserDetailsDTO user) {
        File txtFile = null;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_info.txt"))) {
            writer.write("Username: " + user.getUsername() + "\n");
            writer.write("Email: " + user.getEmail() + "\n");
            writer.write("Lista de note:\n");
            for (NotaExamen nota : user.getNotaExamenList()) {
                writer.write("- " + nota.getCalificativ_examen().toString() + "\n");
            }
            System.out.println("TXT file generated successfully.");
            txtFile = new File("user_info.txt");
        } catch (IOException e) {
            System.err.println("Error generating TXT file: " + e.getMessage());
        }
        return txtFile;
    }
}
