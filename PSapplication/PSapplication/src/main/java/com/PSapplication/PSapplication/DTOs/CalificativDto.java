package com.PSapplication.PSapplication.DTOs;


import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalificativDto implements Serializable {
    //  private UUID id;
    private String title;
    private String description;
    private String username;

    private UUID id;
    private Long calificativ;

  //  private String examen;
    // private Set<Materie> materieList=new HashSet<>() ;

}