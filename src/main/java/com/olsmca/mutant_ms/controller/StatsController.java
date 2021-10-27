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

    /**
     *   Este metodo captura la solicitudes GET entrantes y verifica /stats
     *   procesa la estadisticas de las peticiones almacenadas por la aplicacion
     *   @return el objeto Stats con status 200 en caso contrario retorna un 204 NO CONTENT
     */
    @GetMapping
    public Stats getStats() {
        return  mutantPersistenceService.getStats().orElseThrow(NoContentException::new);
    }
}
