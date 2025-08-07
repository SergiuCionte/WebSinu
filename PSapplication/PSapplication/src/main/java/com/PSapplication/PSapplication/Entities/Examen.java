package com.PSapplication.PSapplication.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "examen")
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "examen_id",nullable = false)
    private Long examen_id;
    @Column(name = "subject",nullable = false)
    private String examen_subject;

    @Column(name = "absente",nullable = true)
    private Long prezente;
    //@Column(name = "tip_examen",nullable = false)
    //private TipExamen tipExamen;


    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "materie_id", referencedColumnName = "materie_id")
    private Materie materie;

    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("examen")

    private List<NotaExamen> notaExamenList;


}
