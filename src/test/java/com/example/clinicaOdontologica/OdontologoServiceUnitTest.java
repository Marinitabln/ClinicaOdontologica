package com.example.clinicaOdontologica;


import com.example.clinicaOdontologica.model.DTO.OdontologoDTO;
import com.example.clinicaOdontologica.service.IOdontologoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OdontologoServiceUnitTest {

    @Autowired
    private IOdontologoService sujetoPrueba;

    public OdontologoDTO odontoPrueba() throws Exception{
        OdontologoDTO odontologo1 = new OdontologoDTO();
        odontologo1.setId(1L);
        odontologo1.setNombre("Marina");
        odontologo1.setApellido("Blanco");
        odontologo1.setMatricula("AB767VD");
        sujetoPrueba.crear(odontologo1);
        return odontologo1;
    }

    @Test
    public void _01_deberiaCrearExitosamenteRegistroDeOdontologo() throws Exception{

        //DADOS
        //CUANDO
        OdontologoDTO odontologo1 = odontoPrueba();

        //ENTONCES
        //Agregar assertions
        assertNotNull(odontologo1);
    }

    @Test
    public void _02_deberiaConsultarExitosamenteTodosLosOdontologos() throws Exception{
        //DADOS
        odontoPrueba();
        odontoPrueba();
        //CUANDO
        Set<OdontologoDTO> resultado = sujetoPrueba.buscarTodos();

        //ENTONCES
        //Agregar assertions
        assertTrue(resultado.size() > 0 );

    }

    @Test
    public void _03_deberiaConsultarExitosamenteOdontologoPorId() throws Exception{
        //DADOS
        OdontologoDTO o = odontoPrueba();

        //CUANDO
        OdontologoDTO resultado = sujetoPrueba.buscar(o.getId());

        //ENTONCES
        //Agregar assertions
        assertNotNull(resultado);
    }

    @Test
    public void _04_deberiaEditarExitosamenteOdontologo() throws Exception {
        //DADOS
        OdontologoDTO o = odontoPrueba();

        //CUANDO
        o.setNombre("Marina");
        o.setApellido("Torrez");
        o.setMatricula("AAAABBB");
        OdontologoDTO resultado = sujetoPrueba.modificar(o);

        //ENTONCES
        //Agregar assertions
        assertEquals(resultado.getApellido(),"Torrez");
        assertEquals(resultado.getMatricula(), "AAAABBB");
    }

    @Test
    public void _05_deberiaEliminarExitosamenteOdontologoPorId() throws Exception{
        //DADOS
        OdontologoDTO o = odontoPrueba();

        //CUANDO
        boolean resultado= sujetoPrueba.eliminar(o.getId());

        //ENTONCES
        //Agregar assertions
        assertTrue(resultado);
    }


}
