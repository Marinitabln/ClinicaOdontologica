package com.example.clinicaOdontologica.service;

import com.example.clinicaOdontologica.exception.BadRequestException;
import com.example.clinicaOdontologica.exception.ResourceNotFoundException;
import com.example.clinicaOdontologica.model.DTO.OdontologoDTO;
import com.example.clinicaOdontologica.model.DTO.PacienteDTO;
import com.example.clinicaOdontologica.model.DTO.TurnoDTO;
import com.example.clinicaOdontologica.model.Turno;
import com.example.clinicaOdontologica.repository.ITurnoRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class TurnoService implements ITurnoService {


    private final ITurnoRepository turnoRepository;

    @Autowired
    public TurnoService(ITurnoRepository turnoRepository){
        this.turnoRepository = turnoRepository;
    }

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    ObjectMapper mapper;

    private final Logger logger = Logger.getLogger(TurnoService.class);

    @Override
    public TurnoDTO crear(TurnoDTO turnoDTO) throws BadRequestException, ResourceNotFoundException {

        TurnoDTO resultado;
        Turno turno = mapper.convertValue(turnoDTO, Turno.class);

        if (turnoDTO.getPaciente().getId() != null && turnoDTO.getOdontologo().getId() != null &&
                turnoDTO.getFechaTurno().isAfter(LocalDate.now())){

            OdontologoDTO o = odontologoService.buscar(turno.getOdontologo().getId());
            PacienteDTO p = pacienteService.buscar(turno.getPaciente().getId());

            // verifica si el turno está disponible
            if(turnoDisponible(o.getId(), turno.getFechaTurno())){
                resultado = mapper.convertValue(turnoRepository.save(turno), TurnoDTO.class);
                logger.info("Se registró el turno exitosamente");
            }else{
                throw new ResourceNotFoundException("El turno no se puede registrar. El odontológo "+ o.getApellido() + " ya tiene un turno esa fecha");
            }
        } else {
            // Los datos no son correctos
           throw new BadRequestException("Error en los datos ingresados");
        }
        return resultado;
    }


    @Override
    public TurnoDTO buscar(Long id) throws ResourceNotFoundException {
        Optional<Turno> turno = turnoRepository.findById(id);
        TurnoDTO turnoDTO;
        if(turno.isPresent()){
            turnoDTO = mapper.convertValue(turno, TurnoDTO.class);
            logger.info("Se consultó el turno del paciente " + turnoDTO.getPaciente().getApellido());
        }else{
            throw new ResourceNotFoundException("No se encontró el turno consultado");
        }
        return turnoDTO;
    }

    @Override
    public TurnoDTO modificar(TurnoDTO turnoDTO) throws BadRequestException, ResourceNotFoundException {

        Turno turno = mapper.convertValue(turnoDTO, Turno.class);
        if (turno.getPaciente().getId() != null && turno.getOdontologo().getId() != null &&
                turno.getFechaTurno().isAfter(LocalDate.now())){
            // No está en el repository
            if (odontologoService.buscar(turno.getOdontologo().getId()) == null ||
                    pacienteService.buscar(turno.getPaciente().getId()) == null) {
                throw new ResourceNotFoundException("Tu paciente u odontólogo no existe");
            }
            turnoRepository.save(turno);
            logger.info("Se modificó el turno exitosamente");
        } else {
            // Los datos no son correctos
            throw new BadRequestException("Error en los datos ingresados");
        }
        return mapper.convertValue(turno, TurnoDTO.class);
    }

    @Override
    public boolean eliminar(Long id) throws ResourceNotFoundException {
        boolean respuesta;
        Optional<Turno> turnoEncontrado = turnoRepository.findById(id);
        if(turnoEncontrado.isEmpty()){
            turnoRepository.deleteById(id);
            respuesta = true;
            logger.info("Se eliminó el turno exitosamente");
        }else{
            throw new ResourceNotFoundException("No se encontró el turno que se quiere eliminar");
        }

        return respuesta;
    }

    @Override
    public Set<TurnoDTO> buscarTodos() throws ResourceNotFoundException {
        List<Turno> turnos = turnoRepository.findAll();
        Set<TurnoDTO> turnosDto = new HashSet<>();
        if(turnos.size()>0) {
            //recorrer la lista de objetos turnos y convertirlos en objetos de tipo turnosDto
            for (Turno turno : turnos) {
                turnosDto.add(mapper.convertValue(turno, TurnoDTO.class));
            }
            logger.info("Se consultaron todos los turnos registrados");
        }else{
            throw new ResourceNotFoundException("No hay turnos registrados");
        }
        return turnosDto;
    }

    private boolean turnoDisponible(Long idOdontologo, LocalDate fechaTurno) {
        boolean respuesta = true;
        List<Turno> listaTurnos = turnoRepository.findAll();

        for (Turno t: listaTurnos){
            TurnoDTO turnoDTO = mapper.convertValue(t,TurnoDTO.class);
            if (turnoDTO.getOdontologo().getId().equals(idOdontologo) && turnoDTO.getFechaTurno().equals(fechaTurno)){
                respuesta = false;
            }
        }
        return respuesta;
    }
}
