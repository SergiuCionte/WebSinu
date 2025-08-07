package com.PSapplication.PSapplication.DTOs;

import com.PSapplication.PSapplication.Entities.Examen;
import com.PSapplication.PSapplication.Entities.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotaExamenDetailsDTO {


    private Long nota_examen_id;

    private Long calificativ_examen;




    private Examen examen;



    private User user;
}