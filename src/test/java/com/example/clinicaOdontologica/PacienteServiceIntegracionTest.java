package com.example.clinicaOdontologica;


import com.example.clinicaOdontologica.model.DTO.PacienteDTO;
import com.example.clinicaOdontologica.model.Domicilio;
import com.example.clinicaOdontologica.service.IPacienteService;

import com.example.clinicaOdontologica.util.Jsons;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PacienteServiceIntegracionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPacienteService sujetoPrueba;


    public PacienteDTO pacientePrueba() throws Exception{
        PacienteDTO pacienteDto = new PacienteDTO();
        pacienteDto.setNombre("Marina");
        pacienteDto.setApellido("Blanco");
        pacienteDto.setDni("33822150");

        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Sarmiento");
        domicilio.setNumero("95");
        domicilio.setLocalidad("Luján de Cuyo");
        domicilio.setProvincia("Mendoza");
        pacienteDto.setDomicilio(domicilio);

        sujetoPrueba.crear(pacienteDto);
        return pacienteDto;
    }

    @Test
    public void registrarPaciente() throws Exception {
        PacienteDTO pdto = pacientePrueba();
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Jsons.asJsonString(pdto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void buscarTodosLosPacientes() throws Exception{
        pacientePrueba();
        pacientePrueba();
        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void buscarPaciente() throws Exception{
        PacienteDTO pdto = pacientePrueba();
        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/{id}", pdto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void eliminarPaciente() throws Exception{
        pacientePrueba();
        mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

}
