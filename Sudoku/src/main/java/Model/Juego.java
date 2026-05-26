package Model;

import java.util.ArrayList;
import java.util.Random;

public class Juego {

    private Generar generar = new Generar();
    private  Tablero tablero = new Tablero();
    private Niveles niveles = new Niveles();
    private Validar validar = new Validar();
    private int errores = 0;
    private int pistas = 0;
    private boolean juegoTermino = false;
    private int[][] tableroSolucion= new  int[9][9];
    private ArrayList<int[]> celdasVacias = new ArrayList<>();
    private Random random = new Random();


    public void iniciarJuego(String dificultad){
        tablero = new Tablero();
        errores=0;
        pistas = 0;
        juegoTermino = false;
        celdasVacias.clear();
        generar.generarSudoku(tablero,0);
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                tableroSolucion[i][j]=tablero.getCelda(i,j).getValor();

            }

        }

        generar.eliminarCeldasSegunNivel(tablero,niveles,dificultad);
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(tablero.getCelda(i,j).getValor() ==0){

                    celdasVacias.add(new int[]{i,j});

                }

            }

        }


    }

    public boolean getionarAccion(int fila, int columna, int valor ){

        if(!tablero.getCelda(fila,columna).isEditable()){
            return false;
        }

        Celda celdaTemporal = new Celda();
        celdaTemporal.setValor(valor);

        if(validar.validarFila(tablero,celdaTemporal,fila) && validar.validarColumna(tablero, celdaTemporal, columna) && validar.validarSubcuadro(fila, columna, tablero, celdaTemporal)){
            tablero.getCelda(fila,columna).setValor(valor);
            tablero.getCelda(fila,columna).setEditable(false);
            return true;
        }

        return false;
    }

    public boolean verificarJugadorGano(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(tablero.getCelda(i,j).getValor() == 0){
                    return false;
                }
            }

        }
        return true;
    }
    public boolean contadorErrores(){
        errores++;
        if(errores==3){
            juegoTermino = true;
            return true;
        }
        return false;
    }

    public int[] darPista(){
        if (pistas<3 && !celdasVacias.isEmpty() && !juegoTermino) {
            int indice = random.nextInt(celdasVacias.size());
            int[] cordebada = celdasVacias.get(indice);
            int fila = cordebada[0];
            int columna = cordebada[1];
            tablero.getCelda(fila, columna).setValor(tableroSolucion[fila][columna]);
            celdasVacias.remove(indice);
            pistas++;
            return cordebada;

        }
        return null;


    }





    public Tablero getTablero() {
        return tablero;
    }

    public int getErrores() {
        return errores;
    }

    public boolean isJuegoTermino(){return juegoTermino;}
}
