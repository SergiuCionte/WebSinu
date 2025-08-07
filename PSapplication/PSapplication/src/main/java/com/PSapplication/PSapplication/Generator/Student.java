package com.PSapplication.PSapplication.Generator;

import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;
import org.springframework.stereotype.Component;

import java.io.File;


public class Student {
    private FileGenerator fileGenerator;

    public Student(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }

    public File generateFile(UserDetailsDTO userDetailsDTO) {

        return fileGenerator.generateFile(userDetailsDTO);
    }


}
