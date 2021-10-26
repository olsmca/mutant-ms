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
        //TODO: validaciones iniciales de tamaño
        if(!initValidation(mutantDTO)){
            return false;
        }

        char [][] matrix = getMatrixfromArray(mutantDTO.getDna());

        var countMutant = isMutantHorVer(matrix);
        if(countMutant<2){
            countMutant += isMutanteDiagonals(matrix);
        }

        return countMutant>1?true:false;
    }

    protected boolean initValidation(MutantDTO mutantDTO){
        Optional<String> stringOptional =Arrays.stream(mutantDTO.getDna()).findFirst();

        int sizeElement = !stringOptional.isEmpty()?stringOptional.get().length():0;
        int sizeDnaLs = mutantDTO.getDna().length;
        var dynamicPattern = String.format(Constants.DNA_PATTERN,sizeElement);

        var pattern = Pattern.compile(dynamicPattern);
        var matcher = pattern.matcher(String.join(",",mutantDTO.getDna()));

        long sizeMatcher = matcher.results().count();

        log.info("sizeElement: "+sizeElement);
        log.info("Matcher: "+sizeMatcher);
        log.info("SizeDNA: "+sizeDnaLs);
        log.info("dynamicPattern: "+dynamicPattern);

        if(sizeDnaLs< Constants.SIZE_DNA_PATTER_VALID || sizeElement <Constants.SIZE_DNA_PATTER_VALID){
            return false;
        }

        if( (sizeMatcher != sizeDnaLs ) || (sizeElement != sizeDnaLs ) ){
            return false;
        }

        return true;
    }

    protected int isMutantHorVer(final char [][] matrix) {

        var secuenciaHorizontal = new SecuenciaDNA();
        var secuenciaVertical = new SecuenciaDNA();

        for(int x=0; x<matrix.length; x++) {
            for(int y=0 ; y<matrix.length-1; y++) {

                if((secuenciaHorizontal.getNumSequence() + secuenciaVertical.getNumSequence()) >= 2){
                    log.info("Cantidad minima de DnA encontrada");
                    log.info("Numero Horizontales: " + secuenciaHorizontal.getNumSequence());
                    log.info("Numero Verticales:   " + secuenciaVertical.getNumSequence());
                    return secuenciaHorizontal.getNumSequence() + secuenciaVertical.getNumSequence();
                }

                this.validateSequence(matrix[x][y], matrix[x][y+1], secuenciaHorizontal);

                this.validateSequence(matrix[y][x], matrix[y+1][x], secuenciaVertical);

                if(y==matrix.length-Constants.SIZE_DNA_PATTER_VALID && (secuenciaHorizontal.getCountMutant() == 0
                        && secuenciaVertical.getCountMutant() == 0 )){
                    log.info("Cambio de Secuencia Valido");
                    break;
                }
            }
            secuenciaHorizontal.setCountMutant(0);
            secuenciaVertical.setCountMutant(0);
        }

        log.info("Cantidad Secuencias Horizontales: " + secuenciaHorizontal.getNumSequence());
        log.info("Cantidad Secuencias Verticales:   " + secuenciaVertical.getNumSequence());

        return (secuenciaHorizontal.getNumSequence() + secuenciaVertical.getNumSequence());
    }

    public int isMutanteDiagonals(final char [][] matrix) {
        int ciclos = matrix.length;
        var diagonalesDerIzqArr = new SecuenciaDNA();
        var diagonalesDerIzqAba = new SecuenciaDNA();
        var diagonalesIzqDerArr = new SecuenciaDNA();
        var diagonalesIzqDerAba = new SecuenciaDNA();

        for(var x=0; ciclos >= Constants.SIZE_DNA_PATTER_VALID; x++, ciclos--) {
            var x1 = 0;
            int y2 = matrix.length - (x + 1);
            int x2 = matrix.length - 1;
            int x3 = x + 1;

            for(int y=0, y1=x+1; y1 < matrix.length-1; y++, x1++, y1++, x2--, y2--, x3++) {

                if((diagonalesDerIzqArr.getNumSequence()+diagonalesDerIzqAba.getNumSequence()+
                        diagonalesIzqDerArr.getNumSequence()+diagonalesIzqDerAba.getNumSequence())>= 2){
                    log.info("Cantidad minima de DnA encontrada");
                    log.info("Numero diagonalesDerIzq:    " + diagonalesDerIzqArr.getNumSequence());
                    log.info("Numero diagonalesDerIzqAba: " + diagonalesDerIzqAba.getNumSequence());
                    log.info("Numero diagonalesIzqDer:    " + diagonalesIzqDerArr.getNumSequence());
                    log.info("Numero diagonalesAbaArr:    " + diagonalesIzqDerAba.getNumSequence());
                    return (diagonalesDerIzqArr.getNumSequence()+diagonalesDerIzqAba.getNumSequence()+
                            diagonalesIzqDerArr.getNumSequence()+diagonalesIzqDerAba.getNumSequence());
                }

                this.validateSequence(matrix[x1][y], matrix[x1 + 1][y + 1], diagonalesDerIzqArr);
                this.validateSequence(matrix[x3][x1], matrix[x3 + 1][x1 + 1], diagonalesDerIzqAba);
                this.validateSequence(matrix[x1][y2], matrix[x1 + 1][y2 - 1], diagonalesIzqDerArr);
                this.validateSequence(matrix[x2][y1], matrix[x2-1][y1+1], diagonalesIzqDerAba);

                if(y1==matrix.length-Constants.SIZE_DNA_PATTER_VALID && (diagonalesDerIzqArr.getCountMutant() == 0
                        && diagonalesIzqDerArr.getCountMutant() == 0 && diagonalesIzqDerAba.getCountMutant() == 0 && diagonalesDerIzqAba.getCountMutant()==0)){
                    log.info("Cambio de Secuencia Valido");
                    break;
                }
            }
        }

        log.info("Cantidad Secuencias Diagonales Izquierda-Derecha Arriba: " + diagonalesDerIzqArr.getNumSequence());
        log.info("Cantidad Secuencias Diagonales Izquierda-Derecha Abajo : " + diagonalesDerIzqAba.getNumSequence());
        log.info("Cantidad Secuencias Diagonales Derecha-Izquierda Arriba: " + diagonalesIzqDerArr.getNumSequence());
        log.info("Cantidad Secuencias Diagonales Derecha-Izquierda Abajo : " + diagonalesIzqDerAba.getNumSequence());

        return (diagonalesDerIzqArr.getNumSequence()+diagonalesDerIzqAba.getNumSequence()+
                diagonalesIzqDerArr.getNumSequence()+diagonalesIzqDerAba.getNumSequence());
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