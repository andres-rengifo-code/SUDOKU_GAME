package Model;

import java.util.ArrayList;
import java.util.Random;

public class Juego {

    /**
     * Generador encargado de crear tableros Sudoku válidos.
     */
    private Generar generar = new Generar();
    /**
     * Tablero actual del juego.
     */
    private  Tablero tablero = new Tablero();
    /**
     * Objeto encargado de validar filas, columnas y subcuadros.
     */
    private Validar validar = new Validar();
    /**
     * Cantidad de errores cometidos por el jugador.
     */
    private int errores = 0;
    /**
     * Cantidad de pistas utilizadas.
     */
    private int pistas = 0;
    /**
     * Indica si la partida ha terminado.
     */
    private boolean juegoTermino = false;
    /**
     * Almacena la solución completa del Sudoku generado.
     */
    private int[][] tableroSolucion= new  int[6][6];
    /**
     * Lista de coordenadas de las celdas vacías.
     * Cada elemento almacena:
     * [fila, columna]
     */
    private ArrayList<int[]> celdasVacias = new ArrayList<>();
    /**
     * Generador de números aleatorios.
     */
    private Random random = new Random();

    /**
     * Inicializa una nueva partida.
     *
     * Genera un Sudoku completo, guarda la solución,
     * elimina celdas para crear el reto y registra
     * las posiciones vacías.
     */
    public void iniciarJuego(){
        //Reinicia las variables
        tablero = new Tablero();
        errores=0;
        pistas = 0;
        juegoTermino = false;
        celdasVacias.clear();

        generar.generarSudoku(tablero,0);//Genera unn tablero solucionado
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                tableroSolucion[i][j]=tablero.getCelda(i,j).getValor(); // le da a cada una de las celdas de la matriz solucion un valor guardando el tablero solucionado

            }

        }

        generar.eliminarCeldasPorSubcuadro(tablero);//elimina las celdas dejando solo 2 visibles por cada subcuadro
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                if(tablero.getCelda(i,j).getValor() ==0){

                    celdasVacias.add(new int[]{i,j});// Gurda las pociciones donde la celda esta vacia

                }

            }

        }


    }
    /**
     * Intenta colocar un valor en una celda.
     *
     * Verifica que la celda sea editable y que el valor
     * cumpla las reglas del Sudoku.
     *
     * @param fila Fila donde se insertará el valor.
     * @param columna Columna donde se insertará el valor.
     * @param valor Número a insertar.
     * @return true si el movimiento es válido.
     */
    public boolean getionarAccion(int fila, int columna, int valor ){

        if(!tablero.getCelda(fila,columna).isEditable()){return false;}// verifica que la celda sea editable para realizar acciones

        // Se crea una celda temporal que almacena el valor ingresado por el usuario.
        // Esta celda se utiliza para realizar las validaciones antes de modificar
        // la celda real del tablero.
        Celda celdaTemporal = new Celda();
        celdaTemporal.setValor(valor);

        //verifica que si lo que el usuaruo escribio cumple con las reglas del juego
        if(validar.validarFila(tablero,celdaTemporal,fila) && validar.validarColumna(tablero, celdaTemporal, columna) && validar.validarSubcuadro(fila, columna, tablero, celdaTemporal)){
            tablero.getCelda(fila,columna).setValor(valor);
            tablero.getCelda(fila,columna).setEditable(false);
            return true;
        }

        return false;
    }

    /**
     * Verifica si el jugador completó el tablero.
     *
     * @return true si no quedan celdas vacías.
     */
    public boolean verificarJugadorGano(){
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                if(tablero.getCelda(i,j).getValor() == 0){
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Incrementa el contador de errores.
     *
     * Si el jugador alcanza tres errores,
     * la partida termina.
     *
     * @return true si el jugador perdió.
     */
    public boolean contadorErrores(){
        errores++;
        if(errores==3){
            juegoTermino = true;
            return true;
        }
        return false;
    }

    /**
     * Proporciona una pista al jugador.
     *
     * Selecciona aleatoriamente una celda vacía
     * y coloca el valor correcto correspondiente.
     *
     * Restricciones:
     * - Máximo 3 pistas.
     * - El juego no debe haber terminado.
     *
     * @return Coordenadas de la pista entregada.
     */
    public int[] darPista(){
        if (pistas<3 && !celdasVacias.isEmpty() && !juegoTermino) {
            // Selecciona una posición aleatoria de la lista
            // de celdas vacías.
            int indice = random.nextInt(celdasVacias.size());
            // Obtiene las coordenadas de la celda seleccionada.
            int[] cordebada = celdasVacias.get(indice);
            // Extrae fila y columna del arreglo.
            int fila = cordebada[0];
            int columna = cordebada[1];
            // Inserta en el tablero el valor correcto
            // almacenado en la solución del Sudoku.
            tablero.getCelda(fila, columna).setValor(tableroSolucion[fila][columna]);
            // Elimina la celda de la lista para evitar
            // que vuelva a ser seleccionada como pista.
            celdasVacias.remove(indice);
            pistas++;
            return cordebada;

        }
        return null;


    }


    /**
     * Obtiene el tablero actual.
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Obtiene la cantidad de errores.
     */
    public int getErrores() {
        return errores;
    }

    /**
     * Indica si la partida ha terminado.
     */
    public boolean isJuegoTermino(){return juegoTermino;}
}
