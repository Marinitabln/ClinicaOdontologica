package com.example.clinicaOdontologica.controller;

import com.example.clinicaOdontologica.exception.BadRequestException;
import com.example.clinicaOdontologica.exception.ResourceNotFoundException;
import com.example.clinicaOdontologica.model.DTO.PacienteDTO;

import com.example.clinicaOdontologica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;

    @PostMapping()
    public ResponseEntity<?> registrarPaciente(@RequestBody PacienteDTO paciente) throws BadRequestException {
        pacienteService.crear(paciente);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Set<PacienteDTO>> buscarPacientes() throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPacientePorId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.buscar(id));
    }

    @PutMapping()
    public ResponseEntity<?> actualizarPaciente(@RequestBody PacienteDTO paciente) throws ResourceNotFoundException {
        pacienteService.modificar(paciente);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        pacienteService.eliminar(id);
        return ResponseEntity.ok("Se eliminó correctamente al paciente");
    }
}
