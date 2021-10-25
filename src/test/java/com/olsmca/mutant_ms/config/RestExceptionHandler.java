package com.olsmca.mutant_ms.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RestExceptionHandler {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenNotFound_whenGetSpecificException_thenNotFoundCode() throws Exception {
        String exceptionParam = "not_found";

        mvc.perform(get("/exception/{exception_id}", exceptionParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RestExceptionHandler))
                .andExpect(result -> assertEquals("resource not found", result.getResolvedException().getMessage()));
    }
}
