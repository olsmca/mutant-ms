package com.olsmca.mutant_ms.service.impl;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.repository.MutantRepository;
import com.olsmca.mutant_ms.repository.domain.Mutant;
import com.olsmca.mutant_ms.repository.domain.Stats;
import com.olsmca.mutant_ms.service.MutantPersistenceService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MutantPersistenceServiceImpl implements MutantPersistenceService {

    private final MutantRepository mutantRepository;

    public MutantPersistenceServiceImpl(final MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    @Override
    @Cacheable(cacheNames = "mutant", key = "#dna")
    public Optional<MutantDTO> get(final String dna) {
        return mutantRepository.findByDna(dna)
                .map(mutant -> mapToDTO(mutant, new MutantDTO()));
    }

    @Override
    public void create(final MutantDTO mutantDTO) {
        final var mutant = mapToEntity(mutantDTO, new Mutant());

        Optional<MutantDTO> optionalMutantDTO = get(mutant.getDna());
        if(!optionalMutantDTO.isEmpty()){
            mutantRepository.save(mutant);
        }
    }

    @Override
    public Stats getStats(){
        var stats = Stats.builder()
                .countMutantDna(mutantRepository.countAllByIsMutant(true))
                .countHumanDna(mutantRepository.countAllByIsMutant(false))
                .build();
        stats.setRatio(stats.calculateRatio());
        return stats;
    }

    private MutantDTO mapToDTO(final Mutant mutant, final MutantDTO mutantDTO) {
        mutantDTO.setDna(mutant.getDna().split(","));
        mutantDTO.setIsMutant(mutant.getIsMutant());
        return mutantDTO;
    }

    private Mutant mapToEntity(final MutantDTO mutantDTO, final Mutant mutant) {
        mutant.setDna(String.join(",",mutantDTO.getDna()));
        mutant.setIsMutant(mutantDTO.getIsMutant());
        return mutant;
    }

}
