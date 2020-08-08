package com.findo.colegio.service;

import com.findo.colegio.document.Alumno;
import com.findo.colegio.document.Curso;
import com.findo.colegio.document.Inscripcion;
import com.findo.colegio.dto.FechaDTO;
import com.findo.colegio.repository.AlumnoRepository;
import com.findo.colegio.repository.CursoRepository;
import com.findo.colegio.repository.InscripcionRepository;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColegioService {

    public static boolean isAlumno(Alumno alumno) {

        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        System.out.print("\"id\":\""+alumno.getId()+"\",");
        System.out.print("\"nombre\":\""+alumno.getNombre()+"\",");
        System.out.print("\"apellido\":\""+alumno.getApellido()+"\",");
        System.out.print("\"libreta\":\""+alumno.getLibreta()+"\",");
        System.out.println("\"fechaNacimiento\":\""+alumno.getFechaNacimiento()+"\"");


        if(alumno.getId()<=0 || alumno.getId()>99999999 ||
                alumno.getNombre().isEmpty() || alumno.getNombre().length()>20 ||
                alumno.getApellido().isEmpty() || alumno.getApellido().length()>20 ||
                alumno.getLibreta().isEmpty() || alumno.getLibreta().length()>20)
        {
            System.out.println("Alumno ERROR");
            System.out.print("-------------------------------------------------------");
            System.out.println("-----------------------------------------------------");
            return false;
        }
        else
        {
            System.out.println("Alumno OK");
            System.out.print("-------------------------------------------------------");
            System.out.println("-----------------------------------------------------");
            return true;
        }

    }

    public static boolean isCurso(Curso curso) {

        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        System.out.print("\"id\":\""+curso.getId()+"\",");
        System.out.print("\"nombre\":\""+curso.getNombre()+"\",");
        System.out.print("\"fechaInicio\":\""+curso.getFechaInicio()+"\",");
        System.out.print("\"fechaFin\":\""+curso.getFechaFin()+"\",");
        System.out.println("\"horasSemanales\":\""+curso.getHorasSemanales()+"\"");


        if(curso.getId()<=0 || curso.getId()>99999999 ||
                curso.getNombre().isEmpty() || curso.getNombre().length()>20 ||
                curso.getHorasSemanales()<=0 || curso.getHorasSemanales()>20 ||
                curso.getFechaInicio().isBefore(LocalDate.now()) || curso.getFechaFin().isBefore(curso.getFechaInicio())
                )
        {
            System.out.println("Curso ERROR");
            return false;
        }
        else
        {
            System.out.println("Curso OK");
            return true;
        }

    }

    public static boolean isInscripcion(Inscripcion inscripcion) {

        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        System.out.print("\"id\":\""+inscripcion.getId()+"\",");
        System.out.print("\"idAlumno\":\""+inscripcion.getIdAlumno()+"\",");
        System.out.println("\"idCurso\":\""+inscripcion.getIdCurso()+"\",");

        if(inscripcion.getId()<=0 || inscripcion.getId()>99999999 ||
                inscripcion.getIdAlumno()<=0 || inscripcion.getIdAlumno()>99999999 ||
                inscripcion.getIdCurso()<=0 || inscripcion.getIdCurso()>99999999 )
        {
            System.out.println("Inscripcion ERROR");
            return false;
        }
        else
        {
            System.out.println("Inscripcion OK");
            return true;
        }

    }

    @Autowired
    AlumnoRepository alumnoRepository;

    public boolean crearAlumno(Alumno alumno) {
        Optional<Alumno> alumnoExistente = alumnoRepository.findById(alumno.getId());
        if (!alumnoExistente.isPresent()) {
            alumnoRepository.save(alumno);
            return true;
        }
        else {
            System.out.println("Alumno existente");
            System.out.print("-------------------------------------------------------");
            System.out.println("-----------------------------------------------------");
            return false;
        }
    }

    @Autowired
    CursoRepository cursoRepository;

    public boolean crearCurso(Curso curso) {
        Optional<Curso> cursoExistente = cursoRepository.findById(curso.getId());
        if (!cursoExistente.isPresent()) {
            cursoRepository.save(curso);
            return true;
        }
        else {
            System.out.println("Curso Existente");
            System.out.print("-------------------------------------------------------");
            System.out.println("-----------------------------------------------------");
            return false;
        }
    }

    @Autowired
    InscripcionRepository inscripcionRepository;

    public boolean crearInscripcion(Inscripcion inscripcion) {
        Optional<Inscripcion> inscripcionExistente = inscripcionRepository.findById(inscripcion.getId());
        if (!inscripcionExistente.isPresent()) {
            inscripcionRepository.save(inscripcion);
            return true;
        }
        else {
            System.out.println("Inscripcion Existente");
            System.out.print("-------------------------------------------------------");
            System.out.println("-----------------------------------------------------");
            return false;
        }
    }

   // public int countAlumnos(Integer idCurso) {
    //    return inscripcionRepository.countByCurso(true);}

    public void horasSemanalesTotales(FechaDTO fecha) {

        List<Curso> cursoExistente = cursoRepository.findAll();
        int horasSemanalesTotales = 0;
        int cantidadDeAlumnos = 0;

        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        for ( int i=0; i<cursoExistente.size(); i++) {
            if(fecha.getFecha().isBefore(cursoExistente.get(i).getFechaInicio()) ||
                    fecha.getFecha().isAfter(cursoExistente.get(i).getFechaFin())){

            }
            else{
                horasSemanalesTotales+=cursoExistente.get(i).getHorasSemanales();
                System.out.println("Nombre: "+ cursoExistente.get(i).getNombre());


                //cantidadDeAlumnos+=countAlumnos(cursoExistente.get(i).getId());


            }

        }

        System.out.println("\nhorasSemanalesTotales = " + horasSemanalesTotales);
        System.out.println("\ncantidadDeAlumnos = " + cantidadDeAlumnos);
        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");


    }

}
