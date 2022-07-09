package com.example.clinicaOdontologica.controller;

import com.example.clinicaOdontologica.model.DTO.OdontologoDTO;

import com.example.clinicaOdontologica.service.IOdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    IOdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<?> registrarOdontologo(@RequestBody OdontologoDTO o) throws Exception{
        odontologoService.crear(o);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Set<OdontologoDTO>> buscarOdontologos() throws Exception {
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDTO> buscarOdontologoPorId(@PathVariable("id") Long id) throws Exception{
        return ResponseEntity.ok(odontologoService.buscar(id));
    }

    @PutMapping
    public ResponseEntity<OdontologoDTO> editarOdontologo(@RequestBody OdontologoDTO o) throws Exception{
        return ResponseEntity.ok(odontologoService.modificar(o));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable("id") Long id) throws Exception {
        odontologoService.eliminar(id);
        return ResponseEntity.ok("Odontólogo eliminado correctamente");
    }

}
