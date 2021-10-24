package com.olsmca.mutant_ms.service.impl;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.repository.domain.SecuenciaDNA;
import com.olsmca.mutant_ms.service.ADNAnalyzerService;
import com.olsmca.mutant_ms.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ADNAnalyzerServiceImpl implements ADNAnalyzerService {

    @Override
    public boolean isMutant(MutantDTO mutantDTO) {

        Optional<String> stringOptional =Arrays.stream(mutantDTO.getDna()).findFirst();

        int sizeElement = stringOptional.isPresent()?stringOptional.get().length():0;
        int sizeDnaLs = mutantDTO.getDna().length;
        var dynamicPattern = String.format("[ATGC]{%d}",sizeElement);

        var pattern = Pattern.compile(dynamicPattern);
        var matcher = pattern.matcher(String.join(",",mutantDTO.getDna()));

        long sizeMatcher = matcher.results().count();

        log.info("sizeElement: "+sizeElement);
        log.info("Matcher: "+sizeMatcher);
        log.info("SizeDNA: "+sizeDnaLs);
        log.info("dynamicPattern: "+dynamicPattern);

        if( (sizeMatcher != sizeDnaLs ) || (sizeElement != sizeDnaLs ) ){
            return false;
        }

        char [][] matrix = getMatrixfromArray(mutantDTO.getDna());

        return isNewMutant(matrix) || isMutanteDiagonales(matrix) ;
    }

    protected boolean isNewMutant(final char [][] matrix) {


        SecuenciaDNA secuenciaHorizontal = new SecuenciaDNA();
        SecuenciaDNA secuenciaVertical = new SecuenciaDNA();

        for(int x=0; x<matrix.length; x++) {
            for(int y=0, y1=1 ; y1<matrix.length; y++, y1++) {

                if((secuenciaHorizontal.getNumSequence() + secuenciaVertical.getNumSequence()) >= 2){
                    log.info("Cantidad minima de DnA encontrada");
                    log.info("Numero Horizontales: " + secuenciaHorizontal.getNumSequence());
                    log.info("Numero Verticales:   " + secuenciaVertical.getNumSequence());
                    return true;
                }
                //Diagonales Horizontales
                log.info("Position Horizontales: " + "x: " + x + " y: "+ y + " valor = " + matrix[x][y] + "  |  x: "
                        + x +  " y: "+ y1 +" valor = " +matrix[x][y1]);
                this.validateSequence(matrix[x][y], matrix[x][y1], secuenciaHorizontal);
                //Diagonales Verticales
                log.info("Position Verticales: " + "x: " + y + " y: "+ x + " valor = " + matrix[y][x] + "  |  x: "
                        + y1 +  " y: "+ x +" valor = " +matrix[y1][x]);
                this.validateSequence(matrix[y][x], matrix[y1][x], secuenciaVertical);

                if(y==matrix.length-Constants.SIZE_DNA_PATTER_VALID && (secuenciaHorizontal.getCountMutant() == 0
                        && secuenciaVertical.getCountMutant() == 0 )){
                    log.info("Cambio de Secuencia Valido");
                    break;
                }
            }
            secuenciaHorizontal.setCountMutant(0);
            secuenciaVertical.setCountMutant(0);
        }

        System.out.println("Cantidad Secuencias Horizontales: " + secuenciaHorizontal.getNumSequence());
        System.out.println("Cantidad Secuencias Verticales:   " + secuenciaVertical.getNumSequence());

        return (secuenciaHorizontal.getNumSequence() + secuenciaVertical.getNumSequence()) >= 2;
    }

    public boolean isMutanteDiagonales(final char [][] matrix) {
        int ciclos = matrix.length;
        SecuenciaDNA diagonalesDerIzq = new SecuenciaDNA();
        SecuenciaDNA diagonalesIzqDer = new SecuenciaDNA();
        SecuenciaDNA diagonalesAbaArr = new SecuenciaDNA();

        for(int x=0; ciclos >= Constants.SIZE_DNA_PATTER_VALID; x++) {
            int x1 = 0;
            int y2 = matrix.length - (x + 1);
            int x2 = matrix.length - 1;

            for(int y=x, y1=x+1; y1<ciclos; y++, x1++, y1++, x2--, y2--) {

                if((diagonalesDerIzq.getNumSequence() + diagonalesIzqDer.getNumSequence()
                        + diagonalesAbaArr.getNumSequence()) >= 2){
                    log.info("Cantidad minima de DnA encontrada");
                    log.info("Numero diagonalesDerIzq: " + diagonalesDerIzq.getNumSequence());
                    log.info("Numero diagonalesIzqDer: " + diagonalesIzqDer.getNumSequence());
                    log.info("Numero diagonalesAbaArr: " + diagonalesAbaArr.getNumSequence());
                    return true;
                }

                log.info("Diagonal Izq - Der (Arriba - Abajo)");
                this.validateSequence(matrix[x1][y], matrix[x1 + 1][y + 1], diagonalesDerIzq);

                log.info("Diagonal Der - Izq (Arriba - Abajo)");
                this.validateSequence(matrix[x1][y2], matrix[x1 + 1][y2 - 1], diagonalesIzqDer);

                log.info("Diagonal Izq - Der (Abajo - Arriba)");
                this.validateSequence(matrix[x2][y1-1], matrix[x2-1][y1], diagonalesAbaArr);

                if(y==matrix.length-Constants.SIZE_DNA_PATTER_VALID && (diagonalesDerIzq.getCountMutant() == 0
                        && diagonalesIzqDer.getCountMutant() == 0 && diagonalesAbaArr.getCountMutant() == 0)){
                    log.info("Cambio de Secuencia Valido");
                    break;
                }
            }
            ciclos--;
            System.out.println();
        }

        System.out.println();
        System.out.println("Cantidad Secuencias Diagonales Derecha-Izquierda: " + diagonalesDerIzq.getNumSequence());
        System.out.println("Cantidad Secuencias Diagonales Izquierda-Derecha: " + diagonalesIzqDer.getNumSequence());
        System.out.println("Cantidad Secuencias Diagonales Izq-Der Aba-Arr  : " + diagonalesAbaArr.getNumSequence());

        System.out.println();

        return (diagonalesDerIzq.getNumSequence() + diagonalesIzqDer.getNumSequence()
                + diagonalesAbaArr.getNumSequence()) >= 2;
    }

    protected char[][] getMatrixfromArray(String[] dna){

        char [][] matrixFromArray = new char[dna.length][dna.length];
        for(var i = 0; i<= dna.length -1; i++) {
            matrixFromArray[i] = dna[i].toCharArray();
            log.info("Dna: "+dna[i]);
        }
        return matrixFromArray;
    }

    private void validateSequence(char val1, char val2, SecuenciaDNA secuenciaDNA) {
        if(val1 == val2) {
            secuenciaDNA.setCountMutant(secuenciaDNA.getCountMutant() + 1);
            if(secuenciaDNA.getCountMutant() == Constants.SIZE_DNA_PATTER_VALID-1) {
                secuenciaDNA.setCountMutant(0);
                secuenciaDNA.setNumSequence(secuenciaDNA.getNumSequence() + 1);
            }
        }else {
            secuenciaDNA.setCountMutant(0);
        }
    }
}

