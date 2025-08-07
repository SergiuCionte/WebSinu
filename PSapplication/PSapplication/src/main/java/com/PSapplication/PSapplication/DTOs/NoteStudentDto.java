package com.PSapplication.PSapplication.DTOs;

import com.PSapplication.PSapplication.Entities.Materie;
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
public class NoteStudentDto implements Serializable {
   // private UUID id;
    private String title;
    private String description;

    private UUID id;

    private String email;

    private String parola;
    private String username;

    private String path;
   // private Set<Materie> materieList=new HashSet<>() ;

}
