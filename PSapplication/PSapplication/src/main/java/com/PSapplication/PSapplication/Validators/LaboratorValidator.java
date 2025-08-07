package com.PSapplication.PSapplication.Validators;

public interface LaboratorValidator {
    boolean validateLaboratorId(String laboratorId);
    boolean validateLaboratorName(String laboratorName);
    boolean validateNota(String nota);
    boolean validateMaterieId(String materieId);
    boolean validateNotaId(String notaId);
}