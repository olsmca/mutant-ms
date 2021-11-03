package com.olsmca.mutant_ms.repository;

import com.olsmca.mutant_ms.repository.domain.Mutant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Definicion del repositorio para objetos de dominio Mutant
 */
@Repository
public interface MutantRepositoryMongo extends MongoRepository<Mutant, String> {

    long countAllByIsMutant(Boolean isMutant);
    Optional<Mutant> findByDna(String dna);
}
