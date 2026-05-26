package Model;

public class Validar {


    public boolean validarFila(Tablero tablero, Celda celda,int fila){
        for(int col = 0; col <9 ; col++){

            if(tablero.getCelda(fila,col).getValor() == celda.getValor()){
                return false;
            }


        }
        return true;
    }

    public boolean validarColumna(Tablero tablero, Celda celda,int col){

        for(int fila = 0; fila <9 ; fila++){

            if(tablero.getCelda(fila,col).getValor() == celda.getValor()){
                return false;
            }


        }
        return true;


    }

    public boolean validarSubcuadro(int fila, int columna, Tablero tablero, Celda celda){
        int inicioFila = (fila/3)*3;
        int inicioColumna = (columna /3)*3;

        for (int i =inicioFila ; i < inicioFila+3; i++){
            for(int j = inicioColumna; j < inicioColumna+3; j++){

                if(tablero.getCelda(i,j).getValor() == celda.getValor()){
                    return false;
                }

            }
        }

        return true;
    }

}
