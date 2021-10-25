package com.olsmca.mutant_ms.repository.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatsTest {

    @Test
    void calculateRadiusEqualsToZero(){
        var stats = Stats.builder().countHumanDna(0).countMutantDna(0).build();
        assertEquals(stats.calculateRatio(), 0);
    }

    @Test
    void calculateHumanRadiusGreaterThanZero(){
        var stats = Stats.builder().countHumanDna(0).countMutantDna(0).build();
        stats.setCountHumanDna(100);
        assertEquals(stats.calculateRatio(), (double) stats.getCountMutantDna() /  stats.getCountHumanDna());
    }

    @Test
    void calculateMutantRadiusGreaterThanZero(){
        var stats = Stats.builder().countHumanDna(0).countMutantDna(0).build();
        stats.setCountMutantDna(40);
        assertEquals(stats.calculateRatio(), (double) stats.getCountMutantDna() / stats.getCountHumanDna());
    }

    @Test
    void calculateRadius(){
        var stats = Stats.builder().countHumanDna(0).countMutantDna(0).build();
        stats.setCountMutantDna(40);
        stats.setCountHumanDna(100);
        assertEquals(stats.calculateRatio(), (double) stats.getCountMutantDna() /  stats.getCountHumanDna());
    }

}
