package com.example.clinicaOdontologica.service;

import com.example.clinicaOdontologica.model.Domicilio;
import org.springframework.stereotype.Service;

@Service
public class DomicilioService{

    public Domicilio modificarDomicilio(Domicilio domicilio) {
        if(domicilio.getCalle() != null){
            domicilio.setCalle(domicilio.getCalle());
        }
        if(domicilio.getNumero() != null){
            domicilio.setNumero(domicilio.getNumero());
        }
        if(domicilio.getLocalidad() != null){
            domicilio.setLocalidad(domicilio.getLocalidad());
        }
        if(domicilio.getProvincia() != null){
            domicilio.setProvincia(domicilio.getProvincia());
        }
        return domicilio;

    }

}
