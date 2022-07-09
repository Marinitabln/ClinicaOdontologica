package com.example.clinicaOdontologica.service;

import com.example.clinicaOdontologica.model.DTO.PacienteDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface IPacienteService {

    PacienteDTO crear(PacienteDTO pacienteDTO) throws Exception;

    PacienteDTO buscar(Long id) throws Exception ;

    PacienteDTO modificar(PacienteDTO pacienteDTO) throws Exception;

    boolean eliminar(Long id) throws Exception;

    Set<PacienteDTO> buscarTodos() throws Exception;
}
