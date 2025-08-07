package com.PSapplication.PSapplication.DTOs;

import com.PSapplication.PSapplication.Entities.Examen;
import com.PSapplication.PSapplication.Entities.User;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterieDetailsDTO {


    private Long materie_id;

    private String nume_materie;

  //  private Laborator laborator;

    private Examen examen;



    private Set<User> userList;
}
