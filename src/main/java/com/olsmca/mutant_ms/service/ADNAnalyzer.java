package com.olsmca.mutant_ms.service;

import com.olsmca.mutant_ms.controller.model.MutantDTO;

public interface ADNAnalyzer {
    public boolean isMutant(final MutantDTO mutantDTO);
}
