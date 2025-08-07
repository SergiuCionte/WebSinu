package com.PSapplication.PSapplication.Validators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MaterieValidatorImpl implements MaterieValidator {

    private static final String ID_REGEX = "^[0-9]+$"; // Regex for numeric IDs
    private static final String NAME_REGEX = "^[a-zA-Z]+$"; // Regex for names containing only letters

    public boolean validateMaterieId(String materieId) {
        Pattern pattern = Pattern.compile(ID_REGEX);
        Matcher matcher = pattern.matcher(materieId);
        return matcher.matches();
    }

    public boolean validateMaterieName(String materieName) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(materieName);
        return matcher.matches();
    }

    public boolean validateLaboratorId(String laboratorId) {
        Pattern pattern = Pattern.compile(ID_REGEX);
        Matcher matcher = pattern.matcher(laboratorId);
        return matcher.matches();
    }

    public boolean validateLaboratorName(String laboratorName) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(laboratorName);
        return matcher.matches();
    }
}

