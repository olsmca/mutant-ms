package com.olsmca.mutant_ms.service.impl;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.repository.MutantRepositoryMongo;
import com.olsmca.mutant_ms.repository.domain.Mutant;
import com.olsmca.mutant_ms.repository.domain.Stats;
import com.olsmca.mutant_ms.service.MutantPersistenceService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MutantPersistenceServiceImpl implements MutantPersistenceService {

    private final MutantRepositoryMongo mutantRepository;

    public MutantPersistenceServiceImpl(final MutantRepositoryMongo mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    /**
     * Consulta los adn registrados, maneja una cache para permitir un procesamiento mas rapido de los adn almacenados
     * @param dna
     * @return
     */
    @Override
    @Cacheable(cacheNames = "mutant", key = "#dna")
    public Optional<MutantDTO> get(final String dna) {
        return mutantRepository.findByDna(dna)
                .map(mutant -> mapToDTO(mutant, new MutantDTO()));
    }

    /**
     * Metodo que consulta el mutante a almacenar, si no existe lo almacena
     * @param mutantDTO
     */
    @Override
    public void create(final MutantDTO mutantDTO) {
        final var mutant = mapToEntity(mutantDTO, new Mutant());

        Optional<MutantDTO> optionalMutantDTO = get(mutant.getDna());
        if(optionalMutantDTO.isEmpty()){
            mutantRepository.save(mutant);
        }
    }

    /**
     * Metodo calcula las estadisticas de valores Humanos y Mutantes en el ADN
     * @return retorna el Objeto Stats calculado
     */
    @Override
    public Optional<Stats> getStats(){

        var stats = Stats.builder()
                .countMutantDna(mutantRepository.countAllByIsMutant(true))
                .countHumanDna(mutantRepository.countAllByIsMutant(false))
                .build();
        stats.setRatio(stats.calculateRatio());
        return Optional.of(stats);
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
