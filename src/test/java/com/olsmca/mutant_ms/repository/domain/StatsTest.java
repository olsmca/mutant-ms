package com.olsmca.mutant_ms.repository.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatsTest {

    @Test
    void getRadio(){
        var stats = Stats.builder().countHumanDna(100).countMutantDna(40).build();
        double expectedRatio = 40 / (double) 100;

        assertEquals(stats.calculateRatio(), expectedRatio);
    }
}
