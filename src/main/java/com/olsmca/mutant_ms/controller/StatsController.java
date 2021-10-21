package com.olsmca.mutant_ms.controller;

import com.olsmca.mutant_ms.repository.domain.Stats;
import com.olsmca.mutant_ms.service.MutantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private final MutantService mutantService;

    public StatsController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @GetMapping
    public ResponseEntity getAllMutants() {
        ResponseEntity<Stats> response;
        try{
            response = ResponseEntity.ok(mutantService.getStats());
        }catch (Exception ex){
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return  response;
    }
}
