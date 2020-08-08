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


    public int getId() { return id; }
    public int getIdAlumno() { return idAlumno; }
    public int getIdCurso() { return idCurso; }

}
