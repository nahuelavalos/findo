package com.findo.colegio.controller;


import com.findo.colegio.document.Alumno;
import com.findo.colegio.document.Curso;
import com.findo.colegio.document.Inscripcion;
import com.findo.colegio.dto.CursosActivosDTO;
import com.findo.colegio.dto.FechaDTO;
import com.findo.colegio.service.ColegioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static com.findo.colegio.service.ColegioService.*;

@RestController
public class ColegioController {

    @PostMapping(value = "/alumno", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> esAlumno(@RequestBody Alumno alumno) {
        try {
            if(isAlumno(alumno) && colegioService.crearAlumno(alumno))
            {
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
            if(isCurso(curso) && colegioService.crearCurso(curso))
            {
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
            if(isInscripcion(inscripcion) && colegioService.crearInscripcion(inscripcion))
            {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.toString());
        }
    }

    @GetMapping(value = "/activos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStats(@RequestBody FechaDTO fecha) {
        //CursosActivosDTO cursosActivos = new CursosActivosDTO(colegioService.countHumans(), colegioService.countMutants());
        colegioService.horasSemanalesTotales(fecha);
        return new ResponseEntity<>(HttpStatus.OK);
        //return ResponseEntity.status(HttpStatus.OK).body(cursosActivos);
    }

    @Autowired
    ColegioService colegioService;

}



