package com.olsmca.mutant_ms.service;

import com.olsmca.mutant_ms.repository.domain.Mutant;
import com.olsmca.mutant_ms.repository.domain.Stats;
import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.repository.MutantRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MutantService {

    private final MutantRepository mutantRepository;

    public MutantService(final MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    public List<MutantDTO> findAll() {
        return mutantRepository.findAll()
                .stream()
                .map(mutant -> mapToDTO(mutant, new MutantDTO()))
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "mutant", key = "#dna")
    public Optional<MutantDTO> get(final String dna) {
        return mutantRepository.findByDna(dna)
                .map(mutant -> mapToDTO(mutant, new MutantDTO()));
    }

    public void create(final MutantDTO mutantDTO) {
        final Mutant mutant = new Mutant();
        mapToEntity(mutantDTO, mutant);

        Optional<MutantDTO> optionalMutantDTO = get(mutant.getDna());
        if(optionalMutantDTO.isPresent()){
            return;
        }else{
            mutantRepository.save(mutant);
        }
    }

    public Stats getStats(){
         Stats stats = Stats.builder()
                        .countMutantDna(mutantRepository.countAllByIsMutant(true))
                        .countHumanDna(mutantRepository.countAllByIsMutant(false))
                        .build();
         stats.setRatio(stats.CalculateRatio());
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
