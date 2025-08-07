package com.PSapplication.PSapplication.Validators;


import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NotaExamenValidatorImpl implements NotaExamenValidator {

    @Override
    public boolean validateNotaExamenId(Long notaExamenId) {
        // You can implement specific validation logic here if needed
        // For example, ensuring notaExamenId is not null or negative
        return notaExamenId != null && notaExamenId > 0;
    }

    @Override
    public boolean validateCalificativExamen(Long calificativExamen) {
        return calificativExamen != null && calificativExamen >= 0 && calificativExamen <= 10;
    }
}
