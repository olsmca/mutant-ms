package com.olsmca.mutant_ms.repository.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Objeto para manejar el conteo de las secuencias ADN durante el procesamiento de la Matrices
 */
@Getter
@Setter
public class SecuenciaDNA {

    int numSequence = 0;
    int countMutant = 0;
}
