package com.olsmca.mutant_ms.repository;

import com.olsmca.mutant_ms.repository.domain.Mutant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Definicion del repositorio para objetos de dominio Mutant
 */
public interface MutantRepository extends JpaRepository<Mutant, String> {

    long countAllByIsMutant(Boolean isMutant);
    Optional<Mutant> findByDna(String dna);
}
