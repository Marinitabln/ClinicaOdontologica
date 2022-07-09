package com.example.clinicaOdontologica.service;


import com.example.clinicaOdontologica.exception.BadRequestException;
import com.example.clinicaOdontologica.exception.ResourceNotFoundException;
import com.example.clinicaOdontologica.model.DTO.OdontologoDTO;
import com.example.clinicaOdontologica.model.Odontologo;

import com.example.clinicaOdontologica.repository.IOdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OdontologoService implements IOdontologoService{


    private final IOdontologoRepository odontologoRepository;

    @Autowired
    public OdontologoService(IOdontologoRepository odontologoRepository){
        this.odontologoRepository = odontologoRepository;
    }

    @Autowired
    ObjectMapper mapper;

    private final Logger logger = Logger.getLogger(OdontologoService.class);


    @Override
    public OdontologoDTO crear(OdontologoDTO odontologoDTO) throws BadRequestException {
        Odontologo odontologo = mapper.convertValue(odontologoDTO, Odontologo.class);
        if(odontologoDTO != null) {
            odontologoRepository.save(odontologo);
            logger.info("Se registró al odontólogo " + odontologo.getApellido());
        }else{
            throw new BadRequestException("Los datos ingresados no son válidos");
        }
        return mapper.convertValue(odontologo, OdontologoDTO.class);
    }

    @Override
    public OdontologoDTO buscar(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        OdontologoDTO odontologoDTO;
        if(odontologo.isPresent()){
            odontologoDTO = mapper.convertValue(odontologo, OdontologoDTO.class);
            logger.info("Se encontró al odontólogo "+ odontologoDTO.getApellido());
        }else{
            throw new ResourceNotFoundException("El odontólogo consultado no existe");
        }
        return odontologoDTO;
    }

    @Override
    public OdontologoDTO modificar(OdontologoDTO odontologoDTO) throws ResourceNotFoundException {

        Odontologo odontologo = mapper.convertValue(odontologoDTO, Odontologo.class);
        Optional<Odontologo> odontologoAActualizar = odontologoRepository.findById(odontologo.getId());
        if(odontologoAActualizar.isPresent()){
            odontologoAActualizar.get().setNombre(odontologo.getNombre());
            odontologoAActualizar.get().setApellido(odontologo.getApellido());
            odontologoAActualizar.get().setMatricula(odontologo.getMatricula());
            odontologoRepository.save(odontologo);
            logger.info("Se modificó al odontólogo "+ odontologo.getApellido());
        }else{
            throw new ResourceNotFoundException("No se encontró al odontógolo que se intenta modificar");
        }
        return mapper.convertValue(odontologo, OdontologoDTO.class);
    }

    @Override
    public boolean eliminar(Long id) throws ResourceNotFoundException {
        boolean respuesta;
        Optional<Odontologo> odontologoEncontrado = odontologoRepository.findById(id);
        if(odontologoEncontrado.isPresent()){
            odontologoRepository.deleteById(id);
            respuesta = true;
            logger.info("Se eliminó al odontólogo con id" + id);
        }else{
            throw new ResourceNotFoundException("No se encontró al odontologo que se intenta eliminar");
        }
        return respuesta;
    }

    @Override
    public Set<OdontologoDTO> buscarTodos() throws ResourceNotFoundException {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        Set<OdontologoDTO> odontologosDto = new HashSet<>();

        if(odontologos.size()>0) {
            //recorrer la lista de objetos pacientes y convertirlos en objetos de tipo pacientesDto
            for (Odontologo odontologo : odontologos) {
                odontologosDto.add(mapper.convertValue(odontologo, OdontologoDTO.class));
            }
            logger.info("Se consultaron todos los odontólogos registrados");
        }else{
            throw new ResourceNotFoundException("No existen registros de odontólogos");
        }
        return odontologosDto;
    }

   }

