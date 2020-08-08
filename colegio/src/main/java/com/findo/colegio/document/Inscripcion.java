package com.findo.colegio.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Inscripcion")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    private Integer id;

    private Integer idAlumno;
    private Integer idCurso;


    public Integer getId() { return id; }
    public Integer getIdAlumno() { return idAlumno; }
    public Integer getIdCurso() { return idCurso; }

}
