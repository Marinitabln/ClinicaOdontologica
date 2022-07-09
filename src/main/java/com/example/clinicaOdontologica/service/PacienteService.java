package com.example.clinicaOdontologica.service;

import com.example.clinicaOdontologica.exception.BadRequestException;
import com.example.clinicaOdontologica.exception.ResourceNotFoundException;
import com.example.clinicaOdontologica.model.DTO.PacienteDTO;

import com.example.clinicaOdontologica.model.Paciente;
import com.example.clinicaOdontologica.repository.IPacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PacienteService implements IPacienteService {

    private IPacienteRepository pacienteRepository;
    private DomicilioService domicilioService;

    @Autowired
    public PacienteService(IPacienteRepository pacienteRepository, DomicilioService domicilioService){
        this.pacienteRepository = pacienteRepository;
        this.domicilioService = domicilioService;
    }

    @Autowired
    ObjectMapper mapper;

    private final Logger logger = Logger.getLogger(PacienteService.class);

    @Override
    public PacienteDTO crear(PacienteDTO pacienteDTO) throws BadRequestException {
        Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);
        if(pacienteDTO != null) {
            paciente.setFechaIngreso(new Date());
            pacienteRepository.save(paciente);
            logger.info("Se registró al paciente " + paciente.getApellido());
        }else{
            throw new BadRequestException("Los datos ingresados no son válidos");
        }
        return mapper.convertValue(paciente, PacienteDTO.class);
    }

    @Override
    public PacienteDTO buscar(Long id) throws ResourceNotFoundException {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        PacienteDTO pacienteDTO = null;
        if(paciente.isPresent()){
            pacienteDTO = mapper.convertValue(paciente, PacienteDTO.class);
            logger.info("Se encontró al paciente "+ pacienteDTO.getApellido());
        }else{
            throw new ResourceNotFoundException("El paciente consultado no existe");
        }
        return pacienteDTO;
    }

    @Override
    public PacienteDTO modificar(PacienteDTO pacienteDTO) throws ResourceNotFoundException {
        Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);
        Optional<Paciente> pacienteAActualizar = pacienteRepository.findById(paciente.getId());
        if(pacienteAActualizar.isPresent()){
            pacienteAActualizar.get().setNombre(paciente.getNombre());
            pacienteAActualizar.get().setApellido(paciente.getApellido());
            pacienteAActualizar.get().setDni(paciente.getDni());
            pacienteAActualizar.get().setDomicilio(domicilioService.modificarDomicilio(paciente.getDomicilio()));
            pacienteAActualizar.get().setFechaIngreso(paciente.getFechaIngreso());

            pacienteRepository.save(pacienteAActualizar.get());
            logger.info("Se modificó al paciente "+ paciente.getApellido());
        }else{
            throw new ResourceNotFoundException("No se encontró al paciente que se intenta modificar");
        }
        return mapper.convertValue(pacienteAActualizar.get(), PacienteDTO.class);
    }


    @Override
    public boolean eliminar(Long id) throws ResourceNotFoundException {
        boolean respuesta;
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(id);
        if(pacienteEncontrado.isPresent()){
            pacienteRepository.deleteById(id);
            respuesta = true;
            logger.info("Se eliminó al paciente con id" + id);
        }else{
            throw new ResourceNotFoundException("No se encontró al paciente que se intenta eliminar");
        }
        return respuesta;
    }

    @Override
    public Set<PacienteDTO> buscarTodos() throws ResourceNotFoundException {
        List<Paciente> pacientes = pacienteRepository.findAll();
        Set<PacienteDTO> pacientesDto = new HashSet<>();

        if(pacientes.size()>0) {

            //recorrer la lista de objetos pacientes y convertirlos en objetos de tipo pacientesDto
            for (Paciente paciente : pacientes) {
                pacientesDto.add(mapper.convertValue(paciente, PacienteDTO.class));
            }
            logger.info("Se consultaron todods los pacientes registrados");
        }else{
            throw new ResourceNotFoundException("No hay pacientes registrados");
        }
        return pacientesDto;
    }

}
