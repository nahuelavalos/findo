package com.findo.colegio.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {
    @Id
    private Integer id;
    private String nombre;
    private String apellido;
    private String libreta;
    private LocalDate fechaNacimiento;

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getLibreta() { return libreta; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
}

