package com.olsmca.mutant_ms.service.impl;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.repository.MutantRepository;
import com.olsmca.mutant_ms.repository.domain.Mutant;
import com.olsmca.mutant_ms.service.ADNAnalyzerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
class ADNAnalyzerServiceImplTest {

    @Autowired
    ADNAnalyzerService adnAnalyzerService;

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void checkIsMutant(String input, boolean expected){
        log.info("input: "+input+" expected: "+expected);
        MutantDTO mutantDTO = new MutantDTO();
        mutantDTO.setDna(input.split(","));
        mutantDTO.setIsMutant(expected);

        assertEquals(adnAnalyzerService.isMutant(mutantDTO), expected);
    }
}
