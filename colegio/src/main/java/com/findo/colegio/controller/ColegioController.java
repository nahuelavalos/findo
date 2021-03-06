package com.findo.colegio.controller;


import com.findo.colegio.document.Alumno;
import com.findo.colegio.document.Curso;
import com.findo.colegio.document.Inscripcion;
import com.findo.colegio.dto.FechaDTO;
import com.findo.colegio.dto.JovenesDTO;
import com.findo.colegio.service.ColegioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.findo.colegio.service.ColegioService.*;

@RestController
public class ColegioController {

    @PostMapping(value = "/alumno", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> esAlumno(@RequestBody Alumno alumno) {
        try {
            if(isAlumno(alumno) && colegioService.crearAlumno(alumno)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.toString());
        }
    }

    @PostMapping(value = "/curso", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> esCurso(@RequestBody Curso curso) {
        try {
            if(isCurso(curso) && colegioService.crearCurso(curso)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.toString());
        }
    }

    @PostMapping(value = "/inscripcion", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> esInscripcion(@RequestBody Inscripcion inscripcion) {
        try {
            if(colegioService.isInscripcion(inscripcion)) {
                if(colegioService.crearInscripcion(inscripcion)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.toString());
        }
    }

    @GetMapping(value = "/fecha", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStats(@RequestBody FechaDTO fecha) {
        return ResponseEntity.status(HttpStatus.OK).body(colegioService.fecha(fecha));
    }

    @GetMapping(value = "/jovenes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStats(@RequestBody JovenesDTO jovenes) {
        if(colegioService.isValidRequestJovenes(jovenes)){
            if(colegioService.cursoExistente(jovenes)){
                return ResponseEntity.status(HttpStatus.OK).body(colegioService.jovenes(jovenes));
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Autowired
    ColegioService colegioService;

}



