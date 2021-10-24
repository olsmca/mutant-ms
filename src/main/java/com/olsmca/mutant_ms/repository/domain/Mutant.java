package com.olsmca.mutant_ms.repository.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


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
