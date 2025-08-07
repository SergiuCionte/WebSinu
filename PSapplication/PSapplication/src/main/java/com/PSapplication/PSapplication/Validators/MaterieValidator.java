package com.PSapplication.PSapplication.Validators;

public interface MaterieValidator {
    boolean validateMaterieId(String materieId);
    boolean validateMaterieName(String materieName);
    boolean validateLaboratorId(String laboratorId);
    boolean validateLaboratorName(String laboratorName);
}