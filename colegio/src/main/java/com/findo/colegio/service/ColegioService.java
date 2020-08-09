package com.findo.colegio.service;

import com.findo.colegio.document.Alumno;
import com.findo.colegio.document.Curso;
import com.findo.colegio.document.Inscripcion;
import com.findo.colegio.dto.FechaDTO;
import com.findo.colegio.dto.JovenesDTO;
import com.findo.colegio.repository.AlumnoRepository;
import com.findo.colegio.repository.CursoRepository;
import com.findo.colegio.repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class ColegioService {

    // ############################ EJERCICIO (1) ############################ //

    public static boolean isAlumno(Alumno alumno) {
        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        System.out.print("\"id\":\""+alumno.getId()+"\",");
        System.out.print("\"nombre\":\""+alumno.getNombre()+"\",");
        System.out.print("\"apellido\":\""+alumno.getApellido()+"\",");
        System.out.print("\"libreta\":\""+alumno.getLibreta()+"\",");
        System.out.println("\"fechaNacimiento\":\""+alumno.getFechaNacimiento()+"\"");


        if(//alumno.getId()<=0 || alumno.getId()>99999999 ||
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
            if(alumno.getId()!=null) {
                if (alumno.getId() <= 0 || alumno.getId() > 99999999) {
                    System.out.println("Alumno con ID INVALIDO");
                    return false;
                }
            }


            System.out.println("Alumno OK");
            System.out.print("-------------------------------------------------------");
            System.out.println("-----------------------------------------------------");
            return true;
        }

    }

    public boolean crearAlumno(Alumno alumno) {

        System.out.println("UNO");
        System.out.println(alumno.getId());

        if(alumno.getId()==null)
        {
            alumno.setId((int) (alumnoRepository.count() + 1));
            while(alumnoRepository.findById(alumno.getId()).isPresent()){
                alumno.setId(alumno.getId()+1);
            }
        }

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
    AlumnoRepository alumnoRepository;


    // ############################ EJERCICIO (2) ############################ //

    public static boolean isCurso(Curso curso) {

        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        System.out.print("\"id\":\""+curso.getId()+"\",");
        System.out.print("\"nombre\":\""+curso.getNombre()+"\",");
        System.out.print("\"fechaInicio\":\""+curso.getFechaInicio()+"\",");
        System.out.print("\"fechaFin\":\""+curso.getFechaFin()+"\",");
        System.out.println("\"horasSemanales\":\""+curso.getHorasSemanales()+"\"");


        if(//curso.getId()<=0 || curso.getId()>99999999 ||
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
            if(curso.getId()!=null) {
                if (curso.getId() <= 0 || curso.getId() > 99999999) {
                    System.out.println("Curso con ID INVALIDO");
                    return false;
                }
            }

            System.out.println("Curso OK");
            return true;
        }

    }

    public boolean crearCurso(Curso curso) {

        if(curso.getId()==null)
        {
            curso.setId((int) (cursoRepository.count() + 1));
            while(cursoRepository.findById(curso.getId()).isPresent()){
                curso.setId(curso.getId()+1);
            }
        }

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
    CursoRepository cursoRepository;


    // ############################ EJERCICIO (3) ############################ //

    public boolean isInscripcion(Inscripcion inscripcion) {

        Optional<Inscripcion> inscripcionExistente = inscripcionRepository.findById(inscripcion.getId());
        Optional<Alumno> alumnoExistente = alumnoRepository.findById(inscripcion.getIdAlumno());
        Optional<Curso> cursoExistente = cursoRepository.findById(inscripcion.getIdCurso());

        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        System.out.print("\"id\":\""+inscripcion.getId()+"\",");
        System.out.print("\"idAlumno\":\""+inscripcion.getIdAlumno()+"\",");
        System.out.println("\"idCurso\":\""+inscripcion.getIdCurso()+"\",");


        if(!inscripcionExistente.isPresent() && alumnoExistente.isPresent() && cursoExistente.isPresent()) {
            if (inscripcion.getId() <= 0 || inscripcion.getId() > 99999999 ||
                    inscripcion.getIdAlumno() <= 0 || inscripcion.getIdAlumno() > 99999999 ||
                    inscripcion.getIdCurso() <= 0 || inscripcion.getIdCurso() > 99999999) {
                System.out.println("Inscripcion ERROR");
                return false;
            } else {
                System.out.println("Inscripcion OK");
                return true;
            }
        }
        else{
            return false;
        }

    }

    public boolean crearInscripcion(Inscripcion inscripcion) {

        List<Inscripcion> inscripciones = inscripcionRepository.findAll();
        Optional<Alumno> alumnoExistente = alumnoRepository.findById(inscripcion.getIdAlumno());
        Optional<Curso> cursoExistente = cursoRepository.findById(inscripcion.getIdCurso());

        //Establece el LUNES como inicio de la SEMANA
        //Elegi FRANCIA porque es un estandar utilizado en la mayoria de los paises de Europa
        WeekFields weekFields = WeekFields.of(Locale.FRANCE);

        //Variables auxiliares para definir semana de INICIO y FIN a cada Curso Existente
        String auxInf = "";
        String auxSup = "";

        //Array utilizado para sumar el total de horas de cada Alumno por semana del anio
        final int SEMANA_ANIO=999999;
        int[] horas = new int[SEMANA_ANIO];

        //Inicializacion de variables
        auxInf = String.valueOf(cursoExistente.get().getFechaInicio().getYear());
        auxInf += String.valueOf(cursoExistente.get().getFechaInicio().get(weekFields.weekOfWeekBasedYear()));

        auxSup = String.valueOf(String.valueOf(cursoExistente.get().getFechaInicio().getYear()));
        auxSup += String.valueOf(cursoExistente.get().getFechaFin().get(weekFields.weekOfWeekBasedYear()));

        System.out.print("Curso: " + auxInf);
        System.out.println(" - " + auxSup);

        //Inicializacion del array
        for(int i=0; i<horas.length; i++)
        {
            if(i>=Integer.parseInt(auxInf) && i<=Integer.parseInt(auxSup)) {
                System.out.print("Entre: i="+i);
                horas[i] = cursoExistente.get().getHorasSemanales();
                System.out.println("Horas="+horas[i]);
            }
        }

        for ( int i=0; i<inscripciones.size(); i++) {
            if(alumnoExistente.get().getId()==inscripciones.get(i).getIdAlumno()){
                if(inscripciones.get(i).getIdCurso()==cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getId()){
                    System.out.println(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getNombre());
                    System.out.print(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaInicio()+" -> ");
                    System.out.print(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaInicio().getYear()+" -> ");
                    System.out.println(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaInicio().get(weekFields.weekOfWeekBasedYear()));
                    System.out.print(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaFin()+" -> ");
                    System.out.print(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaFin().getYear()+" -> ");
                    System.out.println(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaFin().get(weekFields.weekOfWeekBasedYear()));

                    //Seteo las variables auxiliares con la semana de INICIO y FIN de cada Curso en el que el alumno fue inscripto
                    auxInf = String.valueOf(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaInicio().getYear());
                    auxInf += String.valueOf(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaInicio().get(weekFields.weekOfWeekBasedYear()));

                    auxSup = String.valueOf(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaFin().getYear());
                    auxSup += String.valueOf(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaFin().get(weekFields.weekOfWeekBasedYear()));

                    System.out.println(auxInf);
                    System.out.println(auxSup);

                    //Logica de Negocio para cuando el Curso dura solo una semana
                    if(Integer.parseInt(auxInf)==Integer.parseInt(auxSup)) {
                        horas[Integer.parseInt(auxInf)]+=cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getHorasSemanales();
                        System.out.println("actual"+Integer.parseInt(auxInf));
                        if(horas[Integer.parseInt(auxInf)]>20){
                            System.out.println("1 - Paso las 20 horas semanales");
                            return false;}
                    }
                    //Logica de Negocio para cuando el Curso dura mas de una semana
                    else {
                        for(int j=Integer.parseInt(auxInf); j<=Integer.parseInt(auxSup);j++) {
                            horas[j]+=cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getHorasSemanales();
                            System.out.println(horas[j]);
                            if(horas[j]>20){
                                System.out.println("2 - Paso las 20 horas semanales");
                                return false;
                            }
                        }
                    }

                    System.out.println(horas[202036]);
                    System.out.println(horas[202037]);
                    System.out.println(horas[202038]);

                }
            }
        }

        //Procesar Inscripción
        inscripcionRepository.save(inscripcion);
        return true;
    }

    @Autowired
    InscripcionRepository inscripcionRepository;


    // ############################ EJERCICIO (4) ############################ //

    public boolean isValidRequestJovenes(JovenesDTO jovenes) {
        if(jovenes.getCurso()<=0 || jovenes.getCantidad()<=0){
            return false;
        } else {
            return true;
        }
    }

    public boolean cursoExistente(JovenesDTO jovenes) {

        Optional<Curso> cursoExistente = cursoRepository.findById(jovenes.getCurso());
        List<Inscripcion> inscripciones = inscripcionRepository.findAll();

        if (cursoExistente.isPresent()) {
            for (int i = 0; i < inscripciones.size(); i++) {
                if (cursoExistente.get().getId() == inscripciones.get(i).getIdCurso()) {
                    return true;
                }
            }
        }
        return false;
    }

    public String jovenes(JovenesDTO jovenes) {

        List<Inscripcion> inscripcionExistente = inscripcionRepository.findAll();
        String[][] alumnosOrdenados = new String[(int)alumnoRepository.count()][6];


        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        int cantidadK = jovenes.getCantidad();
        int cantidadAlumnos = 0;

        //JSON Response
        String response;

        for ( int i=0; i<inscripcionExistente.size(); i++) {
            if(jovenes.getCurso()==inscripcionExistente.get(i).getIdCurso()) {
                Optional<Alumno> alumnoExistente = alumnoRepository.findById(inscripcionExistente.get(i).getIdAlumno());

                for(int y=0; y<6; y++) {
                    alumnosOrdenados[cantidadAlumnos][0]=""+alumnoExistente.get().getId()+"";
                    alumnosOrdenados[cantidadAlumnos][1]=""+alumnoExistente.get().getNombre()+"";
                    alumnosOrdenados[cantidadAlumnos][2]=""+alumnoExistente.get().getApellido()+"";
                    alumnosOrdenados[cantidadAlumnos][3]=""+alumnoExistente.get().getLibreta()+"";
                    alumnosOrdenados[cantidadAlumnos][4]=""+alumnoExistente.get().getFechaNacimiento()+"";
                    alumnosOrdenados[cantidadAlumnos][5]=""+ChronoUnit.YEARS.between(alumnoExistente.get().getFechaNacimiento(), LocalDate.now())+"";
                }

                System.out.println("[\"id\":\""+alumnosOrdenados[cantidadAlumnos][0]+"\"," +
                        "\"nombre\":\""+alumnosOrdenados[cantidadAlumnos][1]+"\"," +
                        "\"apellido\":\""+alumnosOrdenados[cantidadAlumnos][2]+"\"," +
                        "\"libreta\":\""+alumnosOrdenados[cantidadAlumnos][3]+"\"," +
                        "\"fechaNacimiento\":\""+alumnosOrdenados[cantidadAlumnos][4]+"\"," +
                        "\"edad\":\""+alumnosOrdenados[cantidadAlumnos][5]+"\"]");

                cantidadAlumnos++;
            }
        }

        //Ordeno en forma ascendente para responde los primeros K alumnos
        ordenarAlumnosBurbujeo(alumnosOrdenados, cantidadAlumnos);

        //Si el curso tiene menos de K alumnos muestro todos
        if(cantidadAlumnos<cantidadK) {
            cantidadK=cantidadAlumnos;
        }

        //Armo el JSON Response
        response = "{";
        for(int i=0; i<cantidadK; i++){
            response+="{\"id\":\""+alumnosOrdenados[i][0]+"\"," +
                    "\"nombre\":\""+alumnosOrdenados[i][1]+"\"," +
                    "\"apellido\":\""+alumnosOrdenados[i][2]+"\"," +
                    "\"libreta\":\""+alumnosOrdenados[i][3]+"\"," +
                    "\"fechaNacimiento\":\""+alumnosOrdenados[i][4]+"\"," +
                    "\"edad\":\""+alumnosOrdenados[i][5]+"\"}";
            if(i!=cantidadK-1){
                response+=",";
            }
        }
        response+="}";

        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        return response;
    }

    public void ordenarAlumnosBurbujeo(String[][] arrayAlumnos, int cantidadAlumnosArray)
    {
        for(int i=0; i<cantidadAlumnosArray-1; i++)
        {
            for(int j=0; j<cantidadAlumnosArray-1; j++) {

                String[] aux = new String[6];

                if (LocalDate.parse(arrayAlumnos[j][4].substring(0, 10)).isBefore(LocalDate.parse(arrayAlumnos[j + 1][4].substring(0, 10)))) {
                    System.out.println(arrayAlumnos[j][1] + " X " + arrayAlumnos[j + 1][1]);
                    aux[0] = arrayAlumnos[j][0];
                    aux[1] = arrayAlumnos[j][1];
                    aux[2] = arrayAlumnos[j][2];
                    aux[3] = arrayAlumnos[j][3];
                    aux[4] = arrayAlumnos[j][4];
                    aux[5] = arrayAlumnos[j][5];
                    arrayAlumnos[j][0] = arrayAlumnos[j + 1][0];
                    arrayAlumnos[j][1] = arrayAlumnos[j + 1][1];
                    arrayAlumnos[j][2] = arrayAlumnos[j + 1][2];
                    arrayAlumnos[j][3] = arrayAlumnos[j + 1][3];
                    arrayAlumnos[j][4] = arrayAlumnos[j + 1][4];
                    arrayAlumnos[j][5] = arrayAlumnos[j + 1][5];
                    arrayAlumnos[j + 1][0] = aux[0];
                    arrayAlumnos[j + 1][1] = aux[1];
                    arrayAlumnos[j + 1][2] = aux[2];
                    arrayAlumnos[j + 1][3] = aux[3];
                    arrayAlumnos[j + 1][4] = aux[4];
                    arrayAlumnos[j + 1][5] = aux[5];

                }
            }
        }
    }


    // ############################ EJERCICIO (5) ############################ //

    public String fecha(FechaDTO fecha) {

        List<Curso> cursos = cursoRepository.findAll();
        List<Inscripcion> inscripciones = inscripcionRepository.findAll();

        Integer horasSemanalesTotales = 0;
        Integer cantidadDeAlumnos = 0;
        long edadDeAlumnosPromedio = 0;

        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        //Array para almacenar id de alumnos inscriptos utilizado para evitar repetidos
        Integer[] alumnosInscriptos = new Integer[inscripciones.size()];
        //Array para almacenar Edades
        long[] edades = new long[inscripciones.size()];

        //JSON Response
        String response;

        //Busco los cursos vigentes
        for ( int i=0; i<cursos.size(); i++) {
            if(fecha.getFecha().isBefore(cursos.get(i).getFechaInicio()) ||
                    fecha.getFecha().isAfter(cursos.get(i).getFechaFin())){
                    //"Curso no vigente"
            }
            else {
                //Acumulo las horas semanales de los cursos vigentes
                horasSemanalesTotales+=cursos.get(i).getHorasSemanales();
                System.out.println("Nombre: "+ cursos.get(i).getNombre());

                //Busco los alumnos inscriptos a los cursos vigentes
                for ( int j=0; j<inscripciones.size(); j++) {
                    System.out.println(cursos.get(i).getId());
                    System.out.println(inscripciones.get(j).getIdCurso());

                    if(cursos.get(i).getId()==inscripciones.get(j).getIdCurso()) {

                        boolean alumnoRepetido = false;

                        //Evito que haya alumnos repetidos por haberse inscripto a mas de un curso vigente
                        Optional<Alumno> alumno = alumnoRepository.findById(inscripciones.get(j).getIdAlumno());
                        for(int k=0; k<inscripciones.size(); k++) {
                            //if(stats[k][0]==alumno.get().getId())
                                if(alumnosInscriptos[k]==alumno.get().getId())
                            {
                                alumnoRepetido = true;
                            }
                        }
                        System.out.println("IdAlumno"+inscripciones.get(j).getIdAlumno());

                        //Almaceno las edades de todos los alumnos inscriptos al menos a un curso vigente
                        if(!alumnoRepetido){
                            alumnosInscriptos[cantidadDeAlumnos]=alumno.get().getId();
                            edades[cantidadDeAlumnos]=ChronoUnit.YEARS.between(alumno.get().getFechaNacimiento(), LocalDate.now());

                            cantidadDeAlumnos++;
                        };

                        alumnoRepetido = false;
                    }

                }

            }

        }

        //Sumo todas las edades almacenadas
        for(int i=0; i<cantidadDeAlumnos; i++) {
            edadDeAlumnosPromedio+=edades[i];
        }

        //Saco el promedio de todas las edades almacenadas
        if(edadDeAlumnosPromedio>0) {
            edadDeAlumnosPromedio /= cantidadDeAlumnos;
        }

        System.out.println("\nhorasSemanalesTotales = " + horasSemanalesTotales);
        System.out.println("cantidadDeAlumnos = " + cantidadDeAlumnos);
        System.out.println("edadDeAlumnosPromedio = " + edadDeAlumnosPromedio);
        System.out.print("-------------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        //Armo el JSON Response
         response = "{\"horasSemanalesTotales\":\""+horasSemanalesTotales+"\","+
                        "\"cantidadDeAlumnos\":\""+cantidadDeAlumnos+"\"," +
                        "\"edadDeAlumnosPromedio\":\""+edadDeAlumnosPromedio+"\"}";

        return response;
    }

}
