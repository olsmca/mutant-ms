package com.olsmca.mutant_ms.service.impl;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.repository.MutantRepository;
import com.olsmca.mutant_ms.service.MutantPersistenceService;
import impl.MutantPersistenceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MutantPersistenceServiceImplTest {

    @Autowired
    MutantPersistenceService mutantPersistenceService;

    @Autowired
    MutantRepository mutantRepository;

    @Test
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void test(String input, String expected){
        log.info("input: "+input+" expected: "+expected);
        MutantDTO mutantDTO = new MutantDTO();
        mutantDTO.setDna(input.split(","));
        mutantPersistenceService.create(mutantDTO);
    }
}
