package Model;

public class Validar {

    /**
     * Verifica que el valor de una celda no se encuentre repetido
     * en la fila indicada.
     *
     * @param tablero Tablero sobre el que se realiza la validación.
     * @param celdaActual Celda que contiene el valor a validar.
     * @param fila Fila donde se realizará la búsqueda.
     * @return true si el valor no está repetido en la fila,
     *         false en caso contrario.
     */
    public  boolean validarFila(Tablero tablero, Celda celdaActual,int fila){
        for(int col = 0; col <6 ; col++){

            if(tablero.getCelda(fila,col).getValor() == celdaActual.getValor()){
                return false;
            }


        }
        return true;
    }
    /**
     * Verifica que el valor de una celda no se encuentre repetido
     * en la columna indicada.
     *
     * @param tablero Tablero sobre el que se realiza la validación.
     * @param celdaActual Celda que contiene el valor a validar.
     * @param col Columna donde se realizará la búsqueda.
     * @return true si el valor no está repetido en la columna,
     *         false en caso contrario.
     */
    public boolean validarColumna(Tablero tablero, Celda celdaActual,int col){

        for(int fila = 0; fila <6 ; fila++){

            if(tablero.getCelda(fila,col).getValor() == celdaActual.getValor()){
                return false;
            }


        }
        return true;


    }

    /**
     * Verifica que el valor de una celda no se encuentre repetido
     * dentro del subcuadro 2x3 correspondiente.
     *
     * @param fila Fila donde se encuentra la celda.
     * @param columna Columna donde se encuentra la celda.
     * @param tablero Tablero sobre el que se realiza la validación.
     * @param celdaActual Celda que contiene el valor a validar.
     * @return true si el valor no está repetido en el subcuadro,
     *         false en caso contrario.
     */
    public  boolean validarSubcuadro(int fila, int columna, Tablero tablero, Celda celdaActual){
        int inicioFila = (fila/2)*2;
        int inicioColumna = (columna /3)*3;

        for (int i =inicioFila ; i < inicioFila+2; i++){
            for(int j = inicioColumna; j < inicioColumna+3; j++){

                if(tablero.getCelda(i,j).getValor() == celdaActual.getValor()){
                    return false;
                }

            }
        }

        return true;
    }

}
