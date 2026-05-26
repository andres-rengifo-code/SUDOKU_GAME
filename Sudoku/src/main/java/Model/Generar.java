package Model;

import java.util.Random;

public class Generar {

    private int[] numerosSudoku = {1,2,3,4,5,6,7,8,9};
    private Validar validar = new Validar();
    private Random random = new Random();

    public boolean generarSudoku( Tablero tablero, int posicion ){

        if( posicion== 81){
            return true;
        }
        int fila =posicion/9;
        int columna = posicion%9;


        for(int i = 0; i < numerosSudoku.length; i++){
            int aleatorio = random.nextInt(9);
            int temp = numerosSudoku[i];
            numerosSudoku[i] = numerosSudoku[aleatorio];
            numerosSudoku[aleatorio] = temp;
        }

        for(int i = 0; i <numerosSudoku.length; i++) {
            Celda celdaTemporal = new Celda();
            celdaTemporal.setValor(numerosSudoku[i]);
            if(validar.validarFila(tablero,celdaTemporal,fila) && validar.validarColumna(tablero, celdaTemporal, columna) && validar.validarSubcuadro(fila, columna, tablero, celdaTemporal)){
                tablero.getCelda(fila,columna).setValor(numerosSudoku[i]);
                if(generarSudoku(tablero, posicion+1)){
                    return true;
                }
                else{
                    tablero.getCelda(fila, columna).setValor(0);
                }


            }

        }
        return false;
    }

    public void eliminarCeldasSegunNivel(Tablero tablero, Niveles nivel, String dificultad){

        int celdasAEliminar=0;
        if(dificultad.equalsIgnoreCase("facil")){
             celdasAEliminar = nivel.nivelFacil();
        }
        if(dificultad.equalsIgnoreCase("medio")){
             celdasAEliminar = nivel.nivelMedio();

        }
        if(dificultad.equalsIgnoreCase("dificil")){
             celdasAEliminar = nivel.nivelDificil();

        }
        int celdasEliminadas=0;
        while (celdasEliminadas<celdasAEliminar){
            int fila = random.nextInt(9);
            int columna = random.nextInt(9);

            if(tablero.getCelda(fila,columna).getValor() != 0 ){
                tablero.getCelda(fila,columna).setValor(0);
                tablero.getCelda(fila,columna).setEditable(true);
                celdasEliminadas++;
            }
        }
    }

}
