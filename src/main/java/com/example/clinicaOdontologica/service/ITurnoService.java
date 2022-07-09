package com.example.clinicaOdontologica.service;


import com.example.clinicaOdontologica.model.DTO.TurnoDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ITurnoService {

   TurnoDTO crear(TurnoDTO turnoDTO) throws Exception;

    TurnoDTO buscar(Long id) throws Exception;

    TurnoDTO modificar(TurnoDTO turnoDTO) throws Exception;

    boolean eliminar(Long id) throws Exception;

    Set<TurnoDTO> buscarTodos() throws Exception;
}
