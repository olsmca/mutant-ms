package com.olsmca.mutant_ms.service;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.repository.domain.Stats;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface MutantPersistenceService {

    public List<MutantDTO> findAll();

    public Optional<MutantDTO> get(final String dna);

    public void create(final MutantDTO mutantDTO);

    public Stats getStats();
}
