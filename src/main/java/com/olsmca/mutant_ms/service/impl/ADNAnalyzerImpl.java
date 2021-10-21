package com.olsmca.mutant_ms.service.impl;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.service.ADNAnalyzer;
import org.springframework.stereotype.Service;

@Service
public class ADNAnalyzerImpl implements ADNAnalyzer {

    @Override
    public boolean isMutant(final MutantDTO dna) {
        return true;
    }
}
