package com.PSapplication.PSapplication.Validators;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExamenValidatorImpl implements ExamenValidator {
    private static final String ID_REGEX = "^[0-9]+$"; // Regex for numeric IDs
    private static final String NAME_REGEX = "^[a-zA-Z]+$"; // Regex for names containing only letters

    @Override
    public boolean validateExamenId(String examenId) {
        // Validate if examenId matches the ID_REGEX
        return examenId.matches(ID_REGEX);
    }

    @Override
    public boolean validateExamenName(String examenName) {
        // Validate if examenName matches the NAME_REGEX
        return examenName.matches(NAME_REGEX);
    }

    @Override
    public boolean validateNota(String nota) {
        // You can define specific validation rules for nota if needed
        // For example, if nota is a number between 0 and 10
        return true; // Placeholder, implement actual validation as needed
    }

    @Override
    public boolean validateMaterieId(String materieId) {
        // Validate if materieId matches the ID_REGEX
        return materieId.matches(ID_REGEX);
    }

    @Override
    public boolean validateNotaExamenId(String notaExamenId) {
        // Validate if notaExamenId matches the ID_REGEX
        return notaExamenId.matches(ID_REGEX);
    }
}