package com.olsmca.mutant_ms.service.impl;

import java.util.function.Function;

public class main {

    public static void main(String[] args) {

/*        mx(10);
        Math.pow();
        Function<Integer, Integer> negate = (i -> -i), square = (i -> i * i), negateSquare = negate.compose(square);
        System.out.println(negateSquare.apply(10));

        Comparator<String> cop = (String one, String two) -> one.compareTo(two);

        DateTimeFormatter fromDateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    StringBuilder dd = new StringBuilder("casa");
        System.out.println(dd);*/

        char[][] mutantmatrix = new char[6][6];
        mutantmatrix[0][0]= 'A';
        mutantmatrix[0][1]= 'C';
        mutantmatrix[0][2]= 'T';
        mutantmatrix[0][3]= 'A';
        mutantmatrix[0][4]= 'C';
        mutantmatrix[0][5]= 'T';

        mutantmatrix[1][0]= 'T';
        mutantmatrix[1][1]= 'A';
        mutantmatrix[1][2]= 'T';
        mutantmatrix[1][3]= 'G';
        mutantmatrix[1][4]= 'C';
        mutantmatrix[1][5]= 'C';

        mutantmatrix[2][0]= 'G';
        mutantmatrix[2][1]= 'G';
        mutantmatrix[2][2]= 'A';
        mutantmatrix[2][3]= 'A';
        mutantmatrix[2][4]= 'C';
        mutantmatrix[2][5]= 'A';

        mutantmatrix[3][0]= 'C';
        mutantmatrix[3][1]= 'T';
        mutantmatrix[3][2]= 'T';
        mutantmatrix[3][3]= 'A';
        mutantmatrix[3][4]= 'C';
        mutantmatrix[3][5]= 'C';

        mutantmatrix[4][0]= 'G';
        mutantmatrix[4][1]= 'G';
        mutantmatrix[4][2]= 'G';
        mutantmatrix[4][3]= 'G';
        mutantmatrix[4][4]= 'C';
        mutantmatrix[4][5]= 'T';

        mutantmatrix[5][0]= 'A';
        mutantmatrix[5][1]= 'C';
        mutantmatrix[5][2]= 'T';
        mutantmatrix[5][3]= 'G';
        mutantmatrix[5][4]= 'A';
        mutantmatrix[5][5]= 'G';

        boolean result =  isMutant(mutantmatrix, "CCCC");
        System.out.println(result);
    }


    public static boolean isMutant(char[][] mutantMatrix, String word) {
        for(int i = 0; i < mutantMatrix.length; i++) {
            for(int j = 0; j < mutantMatrix[i].length;j++){
                if(moveInMatrix(i,j,0,mutantMatrix, word) && mutantMatrix[i][j] == word.charAt(0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean moveInMatrix(int i, int j, int countWord, char[][] mutantMatrix,  String word) {
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



    public static int mx( int s){
        for(int i=0; i<3; i++){
            System.out.println(i);
            s = s + i;
            System.out.println(s);
        }
        System.out.println(s);
        return s;
    }

    public static void imprimir(Object parameter) {
        System.out.println(parameter);
    }



    public static Function<String,String> swap = s -> {
        if(s.equals("Australia"))
            return "New Zealand";
        else
            return s;
    };
}
