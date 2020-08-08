package com.findo.colegio.controller;


import com.findo.colegio.document.Alumno;
import com.findo.colegio.document.Curso;
import com.findo.colegio.service.ColegioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.findo.colegio.service.ColegioService.isAlumno;
import static com.findo.colegio.service.ColegioService.isCurso;

@RestController
public class ColegioController {

    @PostMapping(value = "/alumno", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> esAlumno(@RequestBody Alumno alumno) {
        try {
            if(isAlumno(alumno))
            {
                colegioService.saveDna(true,alumno);
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
            if(isCurso(curso))
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

    @Autowired
    ColegioService colegioService;

}



