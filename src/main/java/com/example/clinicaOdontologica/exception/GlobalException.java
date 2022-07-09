package com.example.clinicaOdontologica.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    public static Logger logger = Logger.getLogger(GlobalException.class);

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> resourceNotFound(ResourceNotFoundException notFound){
        logger.debug(notFound.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound.getMessage());
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> badRequest(BadRequestException badReq){
        logger.debug(badReq.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badReq.getMessage());
    }
}