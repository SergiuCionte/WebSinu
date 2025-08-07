package com.PSapplication.PSapplication.Generator;

import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;

import java.io.File;

public interface FileGenerator {
    File generateFile(UserDetailsDTO userDetailsDTO);
}
