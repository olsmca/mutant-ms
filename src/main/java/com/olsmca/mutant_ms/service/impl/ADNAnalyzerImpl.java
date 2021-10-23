package com.olsmca.mutant_ms.service.impl;

import com.olsmca.mutant_ms.controller.model.MutantDTO;
import com.olsmca.mutant_ms.service.ADNAnalyzer;
import com.olsmca.mutant_ms.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ADNAnalyzerImpl implements ADNAnalyzer {

    @Override
    public boolean isMutant(MutantDTO mutantDTO) {

        Pattern pattern = Pattern.compile(Constants.DNA_SEQUENCE_PATTERN);
        Matcher matcher = pattern.matcher(String.join(",",mutantDTO.getDna()));

        long sizeMatcher = matcher.results().count();

        log.info("Matcher: "+sizeMatcher);

        if(sizeMatcher!= mutantDTO.getDna().length){
            return false;
        }

        int countMutantSequences = 0;
        /**countMutantSequences+=validarSecuencia(Arrays.asList(mutantDTO.getDna()));
        char matrix [][] = getMatrixfromArray(mutantDTO.getDna());
        if(countMutantSequences<2){
            countMutantSequences+=validarSecuencia(getColumnsAsStrings(matrix));
        }
        return true;
         */

        /**char mutantMatrix [][] = getMatrixfromArray(mutantDTO.getDna());
        log.info("mutantMatrix: "+mutantMatrix.length);
        return isMutant(mutantMatrix, "CCCC");
         */
        return isNewMutant(mutantDTO);
    }

    protected char[][] getMatrixfromArray(String[] dna){

        char matrixFromArray[][] = new char[dna.length][dna.length];
        for(int i = 0; i<= dna.length -1; i++) {
            matrixFromArray[i] = dna[i].toCharArray();
            log.info("Dna: "+dna[i]);
        }
        return matrixFromArray;
    }

    protected List<String> getColumnsAsStrings(char[][] matrix) {
        List<String> columnsAsString = new ArrayList<>();
        for(int i = 0; i < matrix.length; i++) {
            char[] columnAsChar = new char[matrix.length];
            for(int j = 0; j< matrix.length; j++) {
                columnAsChar[j]= matrix[j][i];
                //log.info("ColumnaChar: "+columnAsChar[j]);
            }
            columnsAsString.add(String.valueOf(columnAsChar));
        }
        log.info("Columna: "+columnsAsString);
        return columnsAsString;
    }

    protected int validarSecuencia(List<String> lsDna) {
        int count = 0;
        for(String dnaSequence : lsDna) {
            Pattern pattern = Pattern.compile(Constants.DNA_PATTERN);
            Matcher matcher = pattern.matcher(dnaSequence);
            while (matcher.find()) {
                count++;
            }
        }
        return count;
    }

    protected boolean isMutant(char[][] mutantMatrix, String word) {
        for(int i = 0; i < mutantMatrix.length; i++) {
            for(int j = 0; j < mutantMatrix[i].length;j++){
                if(moveInMatrix(i,j,0,mutantMatrix, word) && mutantMatrix[i][j] == word.charAt(0)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean moveInMatrix(int i, int j, int countWord, char[][] mutantMatrix,  String word) {
        System.out.println("position: " + "i: " + i + " "+ "j: "+ j + " "+ "valor "+ " " + mutantMatrix[i][j]);
        if(countWord == word.length()){
            return true;
        }

        if(i < 0 || i >= mutantMatrix.length || j < 0 || j >= mutantMatrix[i].length || mutantMatrix[i][j] != word.charAt(countWord)){
            return false;
        }

        char temp = mutantMatrix[i][j];
        System.out.println("temporal: "+ temp);
        mutantMatrix[i][j] = ' ';

        boolean win = moveInMatrix(i + 1, j, countWord +1, mutantMatrix, word)
                || moveInMatrix(i, j + 1, countWord +1, mutantMatrix, word);

        mutantMatrix[i][j] = temp;
        return win;
    }

    protected boolean isNewMutant(MutantDTO mutantDTO){
        char [][] matrix = getMatrixfromArray(mutantDTO.getDna());

        int numH = 0;
        int canH = 0;
        int numV = 0;
        int canV = 0;

        for(int x=0; x<matrix.length; x++) {
            canH = 0;
            canV = 0;

            log.info("Row Analizy: "+String.copyValueOf(matrix[x]));

            for(int y=0, y1=1 ; y1<matrix.length; y++, y1++) {

                if((numH + numV) >= 2){
                    log.info("Cantidad minima de DnA encontrada");
                    return true;
                }

                //Validar Secuencia Horizontal
                log.info("Secuencia Horizontal");
                log.info("position: " + "x: " + x + " y: "+ y + " valor = " + matrix[x][y] + " | x: " + x +  " y1: "+ y1 +" valor = " +matrix[x][y1]);
                if(matrix[x][y] == matrix[x][y1]) {
                    canH++;
                    if(canH == Constants.SIZE_DNA_PATTER_VALID-1) {
                        canH = 0;
                        numH++;
                    }
                }
                else {
                    canH = 0;
                }

                //Validar Secuencia Vertical
                log.info("Secuencia Vertical");
                log.info("position: " + "x: " + y + " y: "+ x + " valor = " + matrix[y][x] + " | x: " + y1 +  " y1: "+ x +" valor = " +matrix[y1][x]);
                if(matrix[y][x] == matrix[y1][x] ) {
                    canV++;
                    if(canV == Constants.SIZE_DNA_PATTER_VALID-1) {
                        canV = 0;
                        numV++;
                    }
                }
                else {
                    canV = 0;
                }

                if(y==matrix.length-Constants.SIZE_DNA_PATTER_VALID && (canH == 0 && canV == 0 ) ){
                    log.info("Cambio de Secuencia Valido");
                    break;
                }
            }
        }

        System.out.println("Numero Horizontales: " + numH);
        System.out.println("Numero Verticales:   " + numV);

        return (numH + numV) >= 2;
    }
}
