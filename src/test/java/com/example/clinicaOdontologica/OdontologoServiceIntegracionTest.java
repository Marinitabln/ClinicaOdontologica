package com.example.clinicaOdontologica;

import com.example.clinicaOdontologica.model.DTO.OdontologoDTO;

import com.example.clinicaOdontologica.service.IOdontologoService;
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
public class OdontologoServiceIntegracionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IOdontologoService sujetoPrueba;


    public OdontologoDTO odontoPrueba() throws Exception{
        OdontologoDTO odontologoDto = new OdontologoDTO();
        odontologoDto.setNombre("Marina");
        odontologoDto.setApellido("Blanco");
        odontologoDto.setMatricula("AB767VD");
        sujetoPrueba.crear(odontologoDto);
        return odontologoDto;
    }

    @Test
    public void registrarOdontologo() throws Exception {
        OdontologoDTO od = odontoPrueba();
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Jsons.asJsonString(od)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void buscarTodosLosOdontologos() throws Exception{
        odontoPrueba();
        odontoPrueba();
        mockMvc.perform(MockMvcRequestBuilders.get("/odontologos")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void buscarOdontologo() throws Exception{
        OdontologoDTO od = odontoPrueba();
        mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/{id}", od.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void buscarOdontologoNoExistente() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/{id}", 2000)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void eliminarOdontologo() throws Exception{
        odontoPrueba();
        mockMvc.perform(MockMvcRequestBuilders.delete("/odontologos/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

}
