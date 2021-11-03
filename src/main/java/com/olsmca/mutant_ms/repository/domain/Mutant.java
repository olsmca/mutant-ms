package com.olsmca.mutant_ms.repository.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;


@Getter
@Setter
@Document("Mutant")
public class Mutant {

    @Id
    private String id;

    @Indexed(unique=true)
    private String dna;

    @Indexed(name = "is_mutant")
    private Boolean isMutant;

}
