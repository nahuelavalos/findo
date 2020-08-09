package com.findo.colegio.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JovenesRequestDTO {
    private Integer curso;
    private Integer cantidad;

    public Integer getCurso() { return curso; }
    public Integer getCantidad() { return cantidad; }
}