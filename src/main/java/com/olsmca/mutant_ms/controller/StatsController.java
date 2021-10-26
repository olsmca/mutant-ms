package com.olsmca.mutant_ms.controller;

import com.olsmca.mutant_ms.repository.domain.Stats;
import com.olsmca.mutant_ms.service.MutantPersistenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private final MutantPersistenceService mutantPersistenceService;

    public StatsController(MutantPersistenceService mutantService) {
        this.mutantPersistenceService = mutantService;
    }

    @GetMapping
    public Stats getStats() {
        return  mutantPersistenceService.getStats().orElseThrow(NoContentException::new);
    }
}
