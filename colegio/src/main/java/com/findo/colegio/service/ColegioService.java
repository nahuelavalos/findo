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

        // Valido Alumno
        if(alumno.getNombre().isEmpty() || alumno.getNombre().length()>20 ||
                alumno.getApellido().isEmpty() || alumno.getApellido().length()>20 ||
                alumno.getLibreta().isEmpty() || alumno.getLibreta().length()>20) {
            return false;
        }
        else {
            if(alumno.getId()!=null) {
                if (alumno.getId() <= 0 || alumno.getId() > 99999999) {
                    return false;
                }
            }
            //Alumno OK
            return true;
        }

    }

    public boolean crearAlumno(Alumno alumno) {

        Optional<Alumno> alumnoExistente = alumnoRepository.findById(alumno.getId());

        //ID Opcional
        if(alumno.getId()==null) {
            alumno.setId((int) (alumnoRepository.count() + 1));
            while(alumnoRepository.findById(alumno.getId()).isPresent()){
                alumno.setId(alumno.getId()+1);
            }
        }

        //ID Existente
        if (!alumnoExistente.isPresent()) {
            //Creo Alumno
            alumnoRepository.save(alumno);
            return true;
        }
        else {
            return false;
        }
    }

    @Autowired
    AlumnoRepository alumnoRepository;


    // ############################ EJERCICIO (2) ############################ //

    public static boolean isCurso(Curso curso) {

        //Valido Curso
        if(curso.getNombre().isEmpty() || curso.getNombre().length()>20 ||
                curso.getHorasSemanales()<=0 || curso.getHorasSemanales()>20 ||
                curso.getFechaInicio().isBefore(LocalDate.now()) || curso.getFechaFin().isBefore(curso.getFechaInicio())
                )
        {
            return false;
        }
        else
        {
            if(curso.getId()!=null) {
                if (curso.getId() <= 0 || curso.getId() > 99999999) {
                    return false;
                }
            }
            //Curso OK
            return true;
        }

    }

    public boolean crearCurso(Curso curso) {

        Optional<Curso> cursoExistente = cursoRepository.findById(curso.getId());

        //ID Opcional
        if(curso.getId()==null)
        {
            curso.setId((int) (cursoRepository.count() + 1));
            while(cursoRepository.findById(curso.getId()).isPresent()){
                curso.setId(curso.getId()+1);
            }
        }

        //ID Existente
        if (!cursoExistente.isPresent()) {
            //Creo Curso
            cursoRepository.save(curso);
            return true;
        }
        else {
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

        //Valido Inscripcion
        if(!inscripcionExistente.isPresent() && alumnoExistente.isPresent() && cursoExistente.isPresent()) {
            if (inscripcion.getId() <= 0 || inscripcion.getId() > 99999999 ||
                    inscripcion.getIdAlumno() <= 0 || inscripcion.getIdAlumno() > 99999999 ||
                    inscripcion.getIdCurso() <= 0 || inscripcion.getIdCurso() > 99999999) {
                return false;
            } else {
                //Inscripcion OK
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

        //Inicializacion del array
        for(int i=0; i<horas.length; i++)
        {
            if(i>=Integer.parseInt(auxInf) && i<=Integer.parseInt(auxSup)) {
                horas[i] = cursoExistente.get().getHorasSemanales();
            }
        }

        //Busco todas las inscripciones del Alumno
        for ( int i=0; i<inscripciones.size(); i++) {
            if(alumnoExistente.get().getId()==inscripciones.get(i).getIdAlumno()){
                if(inscripciones.get(i).getIdCurso()==cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getId()){

                    //Seteo las variables auxiliares con la semana de INICIO y FIN de cada Curso en el que el alumno fue inscripto
                    auxInf = String.valueOf(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaInicio().getYear());
                    auxInf += String.valueOf(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaInicio().get(weekFields.weekOfWeekBasedYear()));

                    auxSup = String.valueOf(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaFin().getYear());
                    auxSup += String.valueOf(cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getFechaFin().get(weekFields.weekOfWeekBasedYear()));

                    //Logica de Negocio para cuando el Curso dura solo una semana
                    if(Integer.parseInt(auxInf)==Integer.parseInt(auxSup)) {
                        horas[Integer.parseInt(auxInf)]+=cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getHorasSemanales();
                        if(horas[Integer.parseInt(auxInf)]>20){
                            //Paso las 20 horas semanales
                            return false;}
                    }
                    //Logica de Negocio para cuando el Curso dura mas de una semana
                    else {
                        for(int j=Integer.parseInt(auxInf); j<=Integer.parseInt(auxSup);j++) {
                            horas[j]+=cursoRepository.findById(inscripciones.get(i).getIdCurso()).get().getHorasSemanales();
                            if(horas[j]>20){
                                //Paso las 20 horas semanales
                                return false;
                            }
                        }
                    }
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
        //Valido JSON Request
        if(jovenes.getCurso()<=0 || jovenes.getCantidad()<=0){
            return false;
        } else {
            return true;
        }
    }

    public boolean cursoExistente(JovenesDTO jovenes) {

        Optional<Curso> cursoExistente = cursoRepository.findById(jovenes.getCurso());
        List<Inscripcion> inscripciones = inscripcionRepository.findAll();

        //Valido Curso
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

        return response;
    }

    public void ordenarAlumnosBurbujeo(String[][] arrayAlumnos, int cantidadAlumnosArray)
    {
        for(int i=0; i<cantidadAlumnosArray-1; i++)
        {
            for(int j=0; j<cantidadAlumnosArray-1; j++) {

                String[] aux = new String[6];

                if (LocalDate.parse(arrayAlumnos[j][4].substring(0, 10)).isBefore(LocalDate.parse(arrayAlumnos[j + 1][4].substring(0, 10)))) {
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

                //Busco los alumnos inscriptos a los cursos vigentes
                for ( int j=0; j<inscripciones.size(); j++) {
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

        //Armo el JSON Response
         response = "{\"horasSemanalesTotales\":\""+horasSemanalesTotales+"\","+
                        "\"cantidadDeAlumnos\":\""+cantidadDeAlumnos+"\"," +
                        "\"edadDeAlumnosPromedio\":\""+edadDeAlumnosPromedio+"\"}";

        return response;
    }

}
