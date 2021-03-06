package com.olsmca.mutant_ms.controller;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.service.ADNAnalyzerService;
import com.olsmca.mutant_ms.service.MutantPersistenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/mutant/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MutantController {

    private final MutantPersistenceService mutantService;

    private final ADNAnalyzerService adnAnalyzer;

    public MutantController(final MutantPersistenceService mutantService, ADNAnalyzerService adnAnalyzer) {
        this.mutantService = mutantService;
        this.adnAnalyzer = adnAnalyzer;
    }

    /**
    *   Este metodo captura la solicitudes POST entrantes y verifica
    *   Si la matriz de ADN entrante es mutante o no
    *   @param mutantDTO es un objeto que tiene como atributos el dna a procesar
    *   @return Si el ADN procesado es mutante retorna un ResponseEntity 200 OK caso contrario 403 FORBIDDEN
    */
    @PostMapping
    public ResponseEntity<Void> isMutant(@RequestBody @Valid final MutantDTO mutantDTO) {

        boolean isMutant = adnAnalyzer.isMutant(mutantDTO);
        mutantDTO.setIsMutant(isMutant);
        mutantService.create(mutantDTO);
        return new ResponseEntity<>(isMutant?HttpStatus.OK:HttpStatus.FORBIDDEN);
    }
}
