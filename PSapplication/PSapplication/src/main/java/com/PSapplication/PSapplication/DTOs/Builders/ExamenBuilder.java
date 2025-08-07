package com.PSapplication.PSapplication.DTOs.Builders;

import com.PSapplication.PSapplication.DTOs.ExamenDTO;
import com.PSapplication.PSapplication.DTOs.ExamenDetailsDTO;
import com.PSapplication.PSapplication.Entities.Examen;
import lombok.Builder;

@Builder
public class ExamenBuilder {

    private ExamenBuilder(){

    }

    public static ExamenDTO toExamenDTO(Examen examen){
        return ExamenDTO.builder()
                .examen_id(examen.getExamen_id())
                .examen_subject(examen.getExamen_subject())
              //  .tipExamen(examen.getTipExamen())
                .build();
    }

    public static ExamenDetailsDTO toExamenDetailsDTO(Examen examen){
        return ExamenDetailsDTO.builder()
                .examen_id(examen.getExamen_id())
                .examen_subject(examen.getExamen_subject())
               // .nota_examen(examen.getNota_examen())
                //.prezente(examen.getPrezente())
                .materie(examen.getMaterie())
                .notaExamenList(examen.getNotaExamenList())
               // .tipExamen(examen.getTipExamen())
                .build();

    }

    public static Examen toEntity(ExamenDetailsDTO examenDetailsDTO){
        return Examen.builder()
                .examen_id(examenDetailsDTO.getExamen_id())
                .examen_subject(examenDetailsDTO.getExamen_subject())
               // .nota_examen(examenDetailsDTO.getNota_examen())
              //  .prezente(examenDetailsDTO.getPrezente())
                .materie(examenDetailsDTO.getMaterie())
                .notaExamenList(examenDetailsDTO.getNotaExamenList())
                //.tipExamen(examenDetailsDTO.getTipExamen())
                .build();
    }
}
