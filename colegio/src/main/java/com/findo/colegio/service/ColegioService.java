package com.findo.colegio.service;

import com.findo.colegio.document.Alumno;
import com.findo.colegio.document.Curso;
import com.findo.colegio.repository.AlumnoRepository;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

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
                curso.getFechaFin().isBefore(curso.getFechaInicio())
                )
        {
            System.out.println("Curso ERROR");
            System.out.print("-------------------------------------------------------");
            System.out.println("-----------------------------------------------------");
            return false;
        }
        else
        {
            System.out.println("Curso OK");
            System.out.print("-------------------------------------------------------");
            System.out.println("-----------------------------------------------------");
            return true;
        }

    }
/*
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "test");
    }

    @Bean
    public MongoClient mongo() {
        return new MongoClient("localhost");
    }

    */
    @Autowired
    AlumnoRepository alumnoRepository;



    public void saveDna(boolean isAlumno, Alumno alumno) {
       // Alumno alumno = new Alumno();
        //alumno.setMutante(isMutant);
        //alumno.setDna(dna.getDna());
        Optional<Alumno> alumnoExistente = alumnoRepository.findById(alumno.getId());
        if (!alumnoExistente.isPresent()) {
            alumnoRepository.save(alumno);
        }
        else {
            System.out.println("ADN DUPLICADO");
        }
    }

}
