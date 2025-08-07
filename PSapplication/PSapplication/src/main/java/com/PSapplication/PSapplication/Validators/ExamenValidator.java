package com.PSapplication.PSapplication.Validators;

public interface ExamenValidator {
    boolean validateExamenId(String examenId);
    boolean validateExamenName(String examenName);
    boolean validateNota(String nota);
    boolean validateMaterieId(String materieId);
    boolean validateNotaExamenId(String notaExamenId);
}
