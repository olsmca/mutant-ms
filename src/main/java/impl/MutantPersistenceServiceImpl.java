package impl;

import com.olsmca.mutant_ms.repository.domain.Mutant;
import com.olsmca.mutant_ms.repository.domain.Stats;
import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.repository.MutantRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.olsmca.mutant_ms.service.MutantPersistenceService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class MutantPersistenceServiceImpl implements MutantPersistenceService {

    private final MutantRepository mutantRepository;

    public MutantPersistenceServiceImpl(final MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    @Override
    public List<MutantDTO> findAll() {
        return mutantRepository.findAll()
                .stream()
                .map(mutant -> mapToDTO(mutant, new MutantDTO()))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "mutant", key = "#dna")
    public Optional<MutantDTO> get(final String dna) {
        return mutantRepository.findByDna(dna)
                .map(mutant -> mapToDTO(mutant, new MutantDTO()));
    }

    @Override
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

    @Override
    public Stats getStats(){
         Stats stats = Stats.builder()
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
