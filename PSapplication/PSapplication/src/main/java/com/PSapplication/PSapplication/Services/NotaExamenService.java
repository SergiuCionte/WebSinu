package com.PSapplication.PSapplication.Services;

import com.PSapplication.PSapplication.DTOs.NotaExamenDetailsDTO;

import java.util.List;

public interface NotaExamenService {

    Long createNotaExamen(NotaExamenDetailsDTO notaExamenDetailsDTO);

    NotaExamenDetailsDTO getNotaExamenById(Long id);

    List<NotaExamenDetailsDTO> getAllNotaExamen();

    void updateNotaExamen(NotaExamenDetailsDTO notaExamenDetailsDTO);

    void deleteNotaExamen(Long NotaExamenId);
}