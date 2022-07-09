package com.example.clinicaOdontologica;

import com.example.clinicaOdontologica.model.DTO.OdontologoDTO;
import com.example.clinicaOdontologica.model.DTO.PacienteDTO;
import com.example.clinicaOdontologica.model.Domicilio;
import com.example.clinicaOdontologica.service.IPacienteService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PacienteServiceUnitTest {

    @Autowired
    private IPacienteService sujetoPrueba;

    public PacienteDTO pacientePrueba() throws Exception{
        PacienteDTO paciente = new PacienteDTO();
        paciente.setId(1L);
        paciente.setNombre("Juan Manuel");
        paciente.setApellido("Torrez");
        paciente.setDni("34746728");

        Domicilio domicilio = new Domicilio();
        domicilio.setId(1L);
        domicilio.setCalle("Sarmiento");
        domicilio.setNumero("95");
        domicilio.setLocalidad("Lujan");
        domicilio.setProvincia("Mendoza");
        paciente.setDomicilio(domicilio);
        sujetoPrueba.crear(paciente);
        return paciente;
    }

    @Test
    public void _01_deberiaCrearExitosamenteUnPaciente() throws Exception {
        //DADOS
        //CUANDO
        PacienteDTO paciente = pacientePrueba();

        //ENTONCES
        //Agregar assertions
        assertTrue(paciente != null);

    }

    @Test
    public void _02_deberiaConsultarExitosamenteTodosLosPacientes() throws Exception{
        //DADOS
        pacientePrueba();
        pacientePrueba();
        pacientePrueba();
        //CUANDO
        Set<PacienteDTO> resultado = sujetoPrueba.buscarTodos();

        //ENTONCES
        //Agregar assertions
        assertTrue(resultado.size() > 0 );

    }

    @Test
    public void _03_deberiaConsultarExitosamentePacientePorId() throws Exception{
        //DADOS
        PacienteDTO pacienteDTO = pacientePrueba();

        //CUANDO
        PacienteDTO resultado = sujetoPrueba.buscar(pacienteDTO.getId());

        //ENTONCES
        //Agregar assertions
        assertNotNull(resultado);
    }

    @Test
    public void _04_deberiaEditarExitosamentePaciente() throws Exception {
        //DADOS
        PacienteDTO pacienteDTO = pacientePrueba();

        //CUANDO
        pacienteDTO.setNombre("Marina");
        pacienteDTO.setApellido("Morassutti");
        pacienteDTO.setDni("33822150");
        PacienteDTO resultado = sujetoPrueba.modificar(pacienteDTO);

        //ENTONCES
        //Agregar assertions
        assertEquals(resultado.getNombre(), "Marina");
        assertEquals(resultado.getApellido(),"Morassutti");
        assertEquals(resultado.getDni(), "33822150");
    }

    @Test
    public void _05_deberiaEliminarExitosamentePacientePorId() throws Exception{
        //DADOS
        PacienteDTO pacienteDTO = pacientePrueba();

        //CUANDO
        boolean resultado= sujetoPrueba.eliminar(pacienteDTO.getId());

        //ENTONCES
        //Agregar assertions
        assertTrue(resultado);
    }


}
