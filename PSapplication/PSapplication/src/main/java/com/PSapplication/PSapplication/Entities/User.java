package com.PSapplication.PSapplication.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "users")
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private UUID id;

    @Column(name = "role",nullable = true)
    private String role;
    @Column(name = "username",nullable = true)
    private String username;
    @Column(name = "parola",nullable = true)
    private String parola;
    @Column(name = "email",nullable = true)
    private String email;


    @ManyToMany(fetch = FetchType.LAZY )
    @JoinTable(
            name = "user_materie",
            joinColumns = { @JoinColumn(name = "user_id",referencedColumnName = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "materie_id",referencedColumnName = "materie_id") }
    )
    @JsonIgnore // Break circular reference
    private Set<Materie> materieList=new HashSet() ;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference

    private List<NotaExamen> notaExamenList;

}
