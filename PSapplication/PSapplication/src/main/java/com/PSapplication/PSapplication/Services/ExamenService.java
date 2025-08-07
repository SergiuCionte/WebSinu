package com.PSapplication.PSapplication.Services;

import com.PSapplication.PSapplication.DTOs.ExamenDTO;
import com.PSapplication.PSapplication.DTOs.ExamenDetailsDTO;
import com.PSapplication.PSapplication.DTOs.MaterieDTO;
import com.PSapplication.PSapplication.DTOs.MaterieDetailsDTO;
import com.PSapplication.PSapplication.Entities.Materie;

import java.util.List;

public interface ExamenService {

    Long createExamen(ExamenDetailsDTO examenDetailsDTO);

    ExamenDetailsDTO getExamenById(Long examenId);

    List<ExamenDetailsDTO>getAllExams();

    void updateExamen(ExamenDetailsDTO examenDetailsDTO);

    void deleteExamen(Long examenId);
}