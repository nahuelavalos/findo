package com.findo.colegio.repository;

import com.findo.colegio.document.Inscripcion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends MongoRepository<Inscripcion, String> {

    //Integer countByCurso(int idCurso);
    Optional<Inscripcion> findById(int id);
}
