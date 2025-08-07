package com.microservice.microservice.DTO;


import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalificativDto {
    //  private UUID id;
    private String title;
    private String description;
    private String username;

    private Long calificativ;

    private UUID id;

    private String examen;
    // private Set<Materie> materieList=new HashSet<>() ;

}
