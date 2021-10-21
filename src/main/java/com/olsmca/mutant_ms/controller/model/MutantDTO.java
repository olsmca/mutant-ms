package com.olsmca.mutant_ms.controller.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.olsmca.mutant_ms.util.Constants;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MutantDTO {

    @NotNull
    @Size(max = 255)
    private String[] dna;

    private Boolean isMutant;

}
