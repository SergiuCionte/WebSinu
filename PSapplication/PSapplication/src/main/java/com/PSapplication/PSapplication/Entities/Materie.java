package com.PSapplication.PSapplication.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "materie")
public class Materie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "materie_id",nullable = false)
    private Long materie_id;
    @Column(name = "nume_materie",nullable = true)
    private String nume_materie;

    @OneToOne(mappedBy = "materie", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Examen examen;





    @ManyToMany(mappedBy = "materieList", cascade = { CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore // Break circular reference
    private Set<User> userList=new HashSet<>();

}