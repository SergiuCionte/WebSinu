package com.PSapplication.PSapplication.Validators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LaboratorValidatorImpl implements LaboratorValidator {
    private static final String ID_REGEX = "^[0-9]+$"; // Regex for numeric IDs
    private static final String NAME_REGEX = "^[a-zA-Z]+$"; // Regex for names containing only letters

    @Override
    public boolean validateLaboratorId(String laboratorId) {
        return laboratorId.matches(ID_REGEX);
    }

    @Override
    public boolean validateLaboratorName(String laboratorName) {
        return laboratorName.matches(NAME_REGEX);
    }

    @Override
    public boolean validateNota(String nota) {
        // You can define specific validation rules for nota if needed
        return true; // Placeholder, implement actual validation as needed
    }

    @Override
    public boolean validateMaterieId(String materieId) {
        return materieId.matches(ID_REGEX);
    }

    @Override
    public boolean validateNotaId(String notaId) {
        return notaId.matches(ID_REGEX);
    }
}
