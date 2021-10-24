package com.olsmca.mutant_ms.controller;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.service.ADNAnalyzerService;
import com.olsmca.mutant_ms.service.MutantPersistenceService;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/mutant/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MutantController {

    private final MutantPersistenceService mutantService;

    private final ADNAnalyzerService adnAnalyzer;

    public MutantController(final MutantPersistenceService mutantService, ADNAnalyzerService adnAnalyzer) {
        this.mutantService = mutantService;
        this.adnAnalyzer = adnAnalyzer;
    }

    @GetMapping
    public ResponseEntity<List<MutantDTO>> getAllMutants() {
        return ResponseEntity.ok(mutantService.findAll());
    }

    @GetMapping("{dna}")
    public ResponseEntity<MutantDTO> getMutant(@PathVariable final String dna) {
        Optional<MutantDTO> mutantDTOOptional = mutantService.get(dna);
        return ResponseEntity.ok(mutantDTOOptional.isPresent()?mutantDTOOptional.get():null);
    }

    @PostMapping
    public ResponseEntity<Void> isMutant(@RequestBody @Valid final MutantDTO mutantDTO) {

        boolean isMutant = adnAnalyzer.isMutant(mutantDTO);
        mutantDTO.setIsMutant(isMutant);
        mutantService.create(mutantDTO);
        return new ResponseEntity<>(isMutant?HttpStatus.OK:HttpStatus.FORBIDDEN);
    }
}
