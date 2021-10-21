package com.olsmca.mutant_ms.repository.domain;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import com.olsmca.mutant_ms.util.Constants;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Data
public class Mutant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String dna;

    @Column(nullable = false)
    private Boolean isMutant;

}
