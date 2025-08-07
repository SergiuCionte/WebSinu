package com.PSapplication.PSapplication.DTOs;

import com.PSapplication.PSapplication.Entities.Materie;
import com.PSapplication.PSapplication.Entities.NotaExamen;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamenDetailsDTO {


    private Long examen_id;

    private String examen_subject;



   // private Long prezente;

    //private TipExamen tipExamen;


    private Materie materie;

    private List<NotaExamen> notaExamenList=new ArrayList<>();
}
