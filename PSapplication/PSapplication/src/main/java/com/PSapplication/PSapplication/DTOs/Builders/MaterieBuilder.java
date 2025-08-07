package com.PSapplication.PSapplication.DTOs.Builders;

import com.PSapplication.PSapplication.DTOs.MaterieDTO;
import com.PSapplication.PSapplication.DTOs.MaterieDetailsDTO;
import com.PSapplication.PSapplication.Entities.Materie;
import lombok.*;



@Builder
public class MaterieBuilder {

    private  MaterieBuilder(){

    }

    public static MaterieDTO toMaterieDTO(Materie materie){
        return MaterieDTO.builder()
                .materie_id(materie.getMaterie_id())
                .nume_materie(materie.getNume_materie())
                .build();
    }

    public static MaterieDetailsDTO toMaterieDetailsDTO(Materie materie){
        return MaterieDetailsDTO.builder()
                .materie_id(materie.getMaterie_id())
                .nume_materie(materie.getNume_materie())
               // .laborator(materie.getLaborator())
                .examen(materie.getExamen())
                .userList(materie.getUserList())

                .build();
    }

    public static Materie toEntity(MaterieDetailsDTO materieDetailsDTO){
        return Materie.builder()
                .materie_id(materieDetailsDTO.getMaterie_id())
                .nume_materie(materieDetailsDTO.getNume_materie())
               // .laborator(materieDetailsDTO.getLaborator())
                .examen(materieDetailsDTO.getExamen())
                .userList(materieDetailsDTO.getUserList())

                .build();
    }

}