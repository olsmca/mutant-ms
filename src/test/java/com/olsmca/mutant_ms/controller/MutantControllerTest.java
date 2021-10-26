package com.olsmca.mutant_ms.controller;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.service.ADNAnalyzerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MutantController mutantController;

    @MockBean
    private ADNAnalyzerService adnAnalyzerService;

    private static final String JSON_TO_DESERIALIZE = "{\n" +
            "\t\"dna\": [\"TGCAAAGA\"\n" +
            "            ,\"AATATTTT\"\n" +
            "            ,\"GGATATGG\"\n" +
            "            ,\"AAAATGAT\"\n" +
            "            ,\"ATGCCAGG\"\n" +
            "            ,\"TCGGACGG\"\n" +
            "            ,\"ATAAGGAA\"\n" +
            "            ,\"ATAGGAAA\"]\n" +
            "}";

    @Test
    public void controllerInitializedCorrectly() {
        assertThat(mutantController).isNotNull();
    }

    @Test
    void shouldReturnDefaultMessage() throws Exception {

        when(adnAnalyzerService.isMutant(any(MutantDTO.class))).thenReturn(true);

        this.mockMvc.perform(post("/mutant/").contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_TO_DESERIALIZE))
                .andExpect(status().isOk());
    }
}
