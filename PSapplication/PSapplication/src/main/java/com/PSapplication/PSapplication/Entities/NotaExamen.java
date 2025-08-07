package com.PSapplication.PSapplication.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notaExamen")
public class NotaExamen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nota_examen_id",nullable = false)
    private Long nota_examen_id;
    @Column(name = "calificativ_examen",nullable = true)
    private Long calificativ_examen;


    @ManyToOne
    @JsonIgnoreProperties("notaExamenList")
    @JoinColumn(name = "examen_id", referencedColumnName = "examen_id")
    private Examen examen;



    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

}