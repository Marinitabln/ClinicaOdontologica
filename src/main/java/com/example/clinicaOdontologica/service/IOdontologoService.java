package com.example.clinicaOdontologica.service;

import com.example.clinicaOdontologica.model.DTO.OdontologoDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface IOdontologoService {

    OdontologoDTO crear(OdontologoDTO odontologoDTO)throws Exception;

    OdontologoDTO buscar(Long id) throws Exception;

    OdontologoDTO modificar(OdontologoDTO odontologoDTO) throws Exception;

    boolean eliminar(Long id) throws Exception;

    Set<OdontologoDTO> buscarTodos() throws Exception;
}
