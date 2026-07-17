package Model;

import java.util.Random;

public class Generar {

    /**
     * Arreglo con los números válidos del Sudoku 6x6.
     * Se mezcla aleatoriamente para generar tableros distintos.
     */
    private int[] numerosSudoku = {1, 2, 3, 4, 5, 6};
    /**
     * Objeto encargado de validar filas, columnas y subcuadros.
     */
    private Validar validar = new Validar();
    /**
     * Generador de números aleatorios.
     */
    private Random random = new Random();

    /**
     * Genera un Sudoku completo utilizando backtracking.
     *
     * Recorre el tablero posición por posición intentando
     * colocar números válidos. Si una combinación no funciona,
     * retrocede y prueba otra alternativa.
     *
     * @param tablero Tablero sobre el cual se genera el Sudoku.
     * @param posicion Posición actual del tablero (0-35).
     * @return true si se logró generar un tablero válido.
     */
    public boolean generarSudoku(Tablero tablero, int posicion) {

        //Si ya llené las 36 casillas del Sudoku 6x6, terminé
        if (posicion == 36) {
            return true;
        }

        // calcula la fila y la columna
        int fila = posicion / 6;
        int columna = posicion % 6;

        // mezcla numeros: ase que el arreglo {1, 2, 3, 4, 5, 6} se pueda mezclar de diferentes formas lo cual genera Sudokus diferentes
        for (int i = 0; i < numerosSudoku.length; i++) {
            int aleatorio = random.nextInt(6);
            int temp = numerosSudoku[i];
            numerosSudoku[i] = numerosSudoku[aleatorio];
            numerosSudoku[aleatorio] = temp;
        }

        //prueba cada numero mezclado
        for (int i = 0; i < numerosSudoku.length; i++) {
            Celda celdaTemporal = new Celda(); // celda temporal
            celdaTemporal.setValor(numerosSudoku[i]); // se creo una celta temporal con la cual se utilizara para validar

            // verificas las reglas del sudoku
            if (validar.validarFila(tablero, celdaTemporal, fila) && validar.validarColumna(tablero, celdaTemporal, columna) && validar.validarSubcuadro(fila, columna, tablero, celdaTemporal)) {
                tablero.getCelda(fila, columna).setValor(numerosSudoku[i]); // inserta valor si es correcto en el tablero

                // comprueba que el valor de una solucion si no da una solucion retrocede borrando el numero y prueba otro
                if (generarSudoku(tablero, posicion + 1)) {
                    return true;
                } else {
                    tablero.getCelda(fila, columna).setValor(0);
                }


            }

        }
        return false;
    }
    /**
     * Elimina cuatro celdas de cada subcuadro 2x3.
     *
     * Deja visibles únicamente dos celdas por subcuadro,
     * las cuales serán las pistas iniciales del jugador.
     *
     * @param tablero Tablero generado completamente.
     */
    public void eliminarCeldasPorSubcuadro(Tablero tablero) {

        int filasBloque = 2;
        int columnasBloque = 3;

        int totalFilas = 6;
        int totalColumnas = 6;

        // recorre subcuadros
        // (0,0)
        //(0,3)
        //(2,0)
        //(2,3)
        //(4,0)
        //(4,3)
        for (int inicioFila = 0; inicioFila < totalFilas; inicioFila += filasBloque) {
            for (int inicioCol = 0; inicioCol < totalColumnas; inicioCol += columnasBloque) {

                int eliminadas = 0;
                //Elimina cuatro celdas
                while (eliminadas < 4) {

                    int fila = inicioFila + random.nextInt(filasBloque);
                    int columna = inicioCol + random.nextInt(columnasBloque);

                    Celda celda = tablero.getCelda(fila, columna);

                    if (celda.getValor() != 0) {
                        celda.setValor(0);
                        celda.setEditable(true);
                        eliminadas++;
                    }
                }
            }
        }
    }
}
