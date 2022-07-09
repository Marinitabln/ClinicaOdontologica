package com.example.clinicaOdontologica.service;


import com.example.clinicaOdontologica.model.DTO.TurnoDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface IService<T> {

   T crear(T t);

    T buscar(Long id);
    //Optional<T> buscar(Long id);

    T modificar(T t);

    boolean eliminar(Long id);

    Set<T> buscarTodos();
}
