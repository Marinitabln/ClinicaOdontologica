package com.example.clinicaOdontologica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "odontologos")
@Getter @Setter
public class Odontologo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String apellido;
    @Column
    private String nombre;
    @Column
    private String matricula;

    @OneToMany(mappedBy = "odontologo")//, fetch = FetchType.LAZY
    @JsonIgnore //cdo se convierta en JSON, que ignore esta propiedad
    private Set<Turno> turnos = new HashSet<>();


}
