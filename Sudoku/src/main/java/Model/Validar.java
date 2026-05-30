package Model;

public class Validar {

    // valida si lo introducido  por el usuario en respecto a la fila  igual al valor generado por la tabla de sudoku solucionada
    public boolean validarFila(Tablero tablero, Celda celda,int fila){
        for(int col = 0; col <6 ; col++){

            if(tablero.getCelda(fila,col).getValor() == celda.getValor()){
                return false;
            }


        }
        return true;
    }
    // valida si lo introducido  por el usuario en rrespecto ala columna es igual al valor generado por la tabla de sudoku solucionada
    public boolean validarColumna(Tablero tablero, Celda celda,int col){

        for(int fila = 0; fila <6 ; fila++){

            if(tablero.getCelda(fila,col).getValor() == celda.getValor()){
                return false;
            }


        }
        return true;


    }

    // valida si el subcuadro de 2x3  lo que introsuzca el usuario es igual a lo generado por la matriz resuelta
    public boolean validarSubcuadro(int fila, int columna, Tablero tablero, Celda celda){
        int inicioFila = (fila/2)*2;
        int inicioColumna = (columna /3)*3;

        for (int i =inicioFila ; i < inicioFila+2; i++){
            for(int j = inicioColumna; j < inicioColumna+3; j++){

                if(tablero.getCelda(i,j).getValor() == celda.getValor()){
                    return false;
                }

            }
        }

        return true;
    }

}
