package com.example.clinicaOdontologica.model.DTO;

import com.example.clinicaOdontologica.model.Odontologo;
import com.example.clinicaOdontologica.model.Paciente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


//@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class TurnoDTO {

    private Long id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDate fechaTurno;


}
