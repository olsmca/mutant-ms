package com.olsmca.mutant_ms.service;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.repository.domain.Stats;

import java.util.Optional;

public interface MutantPersistenceService {

    Optional<MutantDTO> get(final String dna);

    void create(final MutantDTO mutantDTO);

    Stats getStats();
}
