package com.findo.colegio.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CursosActivosDTO {

    private Integer horasSemanalesTotales;
    private Integer cantidadDeAlumnos;
    private Integer edadDeAlumnosPromedio;

    public CursosActivosDTO(Integer horasSemanalesTotales, Integer cantidadDeAlumnos, Integer edadDeAlumnosPromedio){
        this.horasSemanalesTotales=horasSemanalesTotales;
        this.cantidadDeAlumnos=cantidadDeAlumnos;
        this.edadDeAlumnosPromedio=edadDeAlumnosPromedio;
    }
}
