package com.PSapplication.PSapplication.Validators;


import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NotaColocviuValidatorImpl implements NotaColocviuValidator {

    @Override
    public boolean validateNotaId(Long notaId) {
        // You can implement specific validation logic here if needed
        // For example, ensuring notaId is not null or negative
        return notaId != null && notaId > 0;
    }

    @Override
    public boolean validateCalificativColocviu(Long calificativColocviu) {
        // You can implement specific validation logic here if needed
        // For example, ensuring calificativColocviu is within a certain range
        return calificativColocviu != null; // Placeholder, implement actual validation as needed
    }

    @Override
    public boolean validateLaboratorId(Long laboratorId) {
        // You can implement specific validation logic here if needed
        // For example, ensuring laboratorId is not null or negative
        return laboratorId != null && laboratorId > 0;
    }
}
