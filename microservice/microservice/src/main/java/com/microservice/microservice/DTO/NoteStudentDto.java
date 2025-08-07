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
public class NoteStudentDto {
  //  private UUID id;
  private String title;
  private String description;

  private UUID id;

  private String email;

  private String parola;
  private String username;

  private String path;
    // private Set<Materie> materieList=new HashSet<>() ;

}
