package com.olsmca.mutant_ms.service;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.repository.domain.Stats;

import java.util.Optional;

public interface MutantPersistenceService {

    /**
     * Consulta los adn registrados, maneja una cache para permitir un procesamiento mas rapido de los adn almacenados
     * @param dna
     * @return
     */
    Optional<MutantDTO> get(final String dna);

    /**
     * Metodo que consulta el mutante a almacenar, si no existe lo almacena
     * @param mutantDTO
     */
    void create(final MutantDTO mutantDTO);

    /**
     * Metodo calcula las estadisticas de valores Humanos y Mutantes en el ADN
     * @return retorna el Objeto Stats calculado
     */
    Optional<Stats> getStats();
}
