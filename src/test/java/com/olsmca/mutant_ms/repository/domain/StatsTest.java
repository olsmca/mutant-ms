package com.olsmca.mutant_ms.repository.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatsTest {

    private Stats stats;

    @BeforeEach
    void init(){
        stats = Stats.builder().countHumanDna(0).countMutantDna(0).build();
    }

    @Test
    void calculateRadiusEqualsToZero(){
        assertEquals(stats.calculateRatio(), 0);
    }

    @Test
    void calculateHumanRadiusGreaterThanZero(){
        stats.setCountHumanDna(100);
        assertEquals(stats.calculateRatio(), (double) stats.getCountMutantDna() /  stats.getCountHumanDna());
    }

    @Test
    void calculateMutantRadiusGreaterThanZero(){
        stats.setCountMutantDna(40);
        assertEquals(stats.calculateRatio(), (double) stats.getCountMutantDna() / stats.getCountHumanDna());
    }

    @Test
    void calculateRadius(){
        stats.setCountMutantDna(40);
        stats.setCountHumanDna(100);
        assertEquals(stats.calculateRatio(), (double) stats.getCountMutantDna() /  stats.getCountHumanDna());
    }

}
