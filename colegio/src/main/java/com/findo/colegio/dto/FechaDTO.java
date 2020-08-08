package com.findo.colegio.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FechaDTO {

    private LocalDate fecha;

    public LocalDate getFecha() { return fecha; }
}
