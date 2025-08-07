package com.PSapplication.PSapplication.DTOs;

import com.PSapplication.PSapplication.Entities.Materie;
import com.PSapplication.PSapplication.Entities.NotaExamen;
import lombok.*;


import java.util.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDetailsDTO {



    private UUID id;

    private String role;

    private String username;

    private String parola;

    private String email;


    private Set<Materie> materieList=new HashSet<>() ;

    {
        for (int i = 0; i < 5; i++) {
            materieList.add(new Materie());
        }
    }

 //   private NotaColocviu notaColocviu;

    private List<NotaExamen> notaExamenList=new ArrayList<>();
}
