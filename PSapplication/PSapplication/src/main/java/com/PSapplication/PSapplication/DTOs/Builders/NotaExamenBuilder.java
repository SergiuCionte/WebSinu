package com.PSapplication.PSapplication.DTOs.Builders;

import com.PSapplication.PSapplication.DTOs.NotaExamenDetailsDTO;
import com.PSapplication.PSapplication.Entities.NotaExamen;

public class NotaExamenBuilder {

    private NotaExamenBuilder(){

    }



    public static NotaExamenDetailsDTO toNotaExamenDetailsDTO(NotaExamen notaExamen){
        return NotaExamenDetailsDTO.builder()
                .nota_examen_id(notaExamen.getNota_examen_id())
                .calificativ_examen(notaExamen.getCalificativ_examen())
                .examen(notaExamen.getExamen())
                .user(notaExamen.getUser())
                .build();

    }

    public static NotaExamen toEntity(NotaExamenDetailsDTO notaExamenDetailsDTO){
        return NotaExamen.builder()
                .nota_examen_id(notaExamenDetailsDTO.getNota_examen_id())
                .calificativ_examen(notaExamenDetailsDTO.getCalificativ_examen())
                .examen(notaExamenDetailsDTO.getExamen())
                .user(notaExamenDetailsDTO.getUser())
                .build();
    }
}
