package com.olsmca.mutant_ms.controller.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class MutantDTO {

    @NotNull
    @Size(max = 255)
    private String[] dna;

    private Boolean isMutant;

}
