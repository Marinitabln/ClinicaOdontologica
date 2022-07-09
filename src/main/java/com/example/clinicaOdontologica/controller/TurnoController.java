package com.example.clinicaOdontologica.controller;

import com.example.clinicaOdontologica.model.DTO.TurnoDTO;
import com.example.clinicaOdontologica.service.ITurnoService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    ITurnoService turnoService;

    @Autowired
    ObjectMapper mapper;

    @PostMapping
    public ResponseEntity<?> registrarTurno(@RequestBody TurnoDTO turnoDto) throws Exception {
        turnoService.crear(turnoDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Set<TurnoDTO>> listarTurnos() throws Exception {
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscarTurnoPorId(@PathVariable("id") Long id) throws Exception{
        TurnoDTO turnoEncontrado = mapper.convertValue(turnoService.buscar(id), TurnoDTO.class);
        return ResponseEntity.ok(turnoEncontrado);
    }

    @PutMapping
    public ResponseEntity<?> editarTurno(@RequestBody TurnoDTO turnoDto) throws Exception{
        turnoService.modificar(turnoDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> eliminarTurnoPorId(@PathVariable("id") Long id) throws Exception{
        turnoService.eliminar(id);
        return ResponseEntity.ok("Se eliminó correctamente el turno");
    }


}
