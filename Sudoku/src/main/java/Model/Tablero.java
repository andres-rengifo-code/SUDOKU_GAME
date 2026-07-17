package Model;

public class Tablero  {
    /**
     * Matriz que almacena las celdas del tablero.
     */
    private Celda[][] matrizCeldas;

    /**
     * Tamaño del tablero.
     * Para este Sudoku se utiliza una matriz de 6 filas y 6 columnas.
     */
    private static final int TAMANIO_MATRIZ = 6;

    /**
     * Constructor del tablero.
     *
     * Crea la matriz de celdas y posteriormente inicializa
     * cada posición con un objeto de tipo Celda.
     */
    public Tablero() {
        matrizCeldas = new Celda[TAMANIO_MATRIZ][TAMANIO_MATRIZ];
        crearMatriz();
    }

    /**
     * Inicializa todas las posiciones de la matriz.
     *
     * Recorre cada fila y columna del tablero y crea un
     * objeto Celda para cada posición.
     */
    public void crearMatriz(){
        for(int i = 0; i< TAMANIO_MATRIZ; i++){
            for(int j = 0; j< TAMANIO_MATRIZ; j++){
                matrizCeldas[i][j] = new Celda();
            }

        }
    }

    /**
     * Obtiene la matriz completa de celdas.
     *
     * @return Matriz de objetos Celda.
     */
    public Celda[][] getMatrizCeldas() {
        return matrizCeldas;
    }

    /**
     * Obtiene una celda específica del tablero.
     *
     * @param fil Fila de la celda.
     * @param column Columna de la celda.
     * @return Celda ubicada en la posición indicada.
     */
    public Celda getCelda(int fil, int column) {
        return matrizCeldas[fil][column];
    }


}
