package com.olsmca.mutant_ms.repository.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
public class Stats {
    @JsonProperty("count_mutant_dna")
    long countMutantDna;
    @JsonProperty("count_human_dna")
    long countHumanDna;
    private double ratio;

    public double CalculateRatio() {
        return countHumanDna + countMutantDna == 0?1f:countMutantDna / (double) countHumanDna;
    }
}
