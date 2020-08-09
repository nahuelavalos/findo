package com.findo.colegio.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Inscripcion")

public class Inscripcion {

    @Id
    private Integer id;

    private Integer idAlumno;
    private Integer idCurso;


    public Integer getId() { return id; }
    public Integer getIdAlumno() { return idAlumno; }
    public Integer getIdCurso() { return idCurso; }

}
