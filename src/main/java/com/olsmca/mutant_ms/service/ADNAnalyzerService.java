package com.olsmca.mutant_ms.service;

import com.olsmca.mutant_ms.controller.model.MutantDTO;

/**
 * Interfaz que define el metodo para verificar si un ADN es mutante
 */
public interface ADNAnalyzerService {
    /**
     * @param mutantDTO es la represetacion de la peticion recibida y determina si el ADN es mutante o no
     * @return resultado True o False si un ADN es mutante o no.
     */
    boolean isMutant(final MutantDTO mutantDTO);
}
