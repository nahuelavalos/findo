package com.findo.colegio.repository;

import com.findo.colegio.document.Alumno;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends MongoRepository<Alumno, String> {
    Optional<Alumno> findById(int id);
}
