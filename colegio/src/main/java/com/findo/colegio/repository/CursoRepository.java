package com.findo.colegio.repository;

import com.findo.colegio.document.Curso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends MongoRepository<Curso, String> {
    Optional<Curso> findById(int id);
}
