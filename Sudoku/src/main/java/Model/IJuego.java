package Model;

/**
 * Interfaz que define el contrato del modelo de juego Sudoku.
 *
 * Permite desacoplar el controlador de la implementación
 * concreta del juego, facilitando el uso del patrón Adaptador.
 */
public interface IJuego {

    /**
     * Inicializa una nueva partida.
     */
    void iniciarJuego();

    /**
     * Intenta registrar una acción del jugador en una celda.
     *
     * @param fila    Fila donde se intenta colocar el valor.
     * @param columna Columna donde se intenta colocar el valor.
     * @param valor   Número ingresado por el jugador (1-6).
     * @return true si el movimiento es válido y fue aceptado.
     */
    boolean getionarAccion(int fila, int columna, int valor);

    /**
     * Verifica si el jugador completó correctamente el tablero.
     *
     * @return true si no quedan celdas vacías.
     */
    boolean verificarJugadorGano();

    /**
     * Incrementa el contador de errores y determina si el jugador perdió.
     *
     * @return true si el jugador alcanzó el límite de errores (derrota).
     */
    boolean contadorErrores();

    /**
     * Proporciona una pista al jugador completando una celda vacía aleatoria.
     *
     * No se entrega pista si solo queda una celda sin resolver
     * o si el juego terminó. No hay límite de pistas.
     *
     * @return Arreglo [fila, columna] de la celda revelada, o null si no aplica.
     */
    int[] darPista();

    /**
     * Obtiene el tablero actual de la partida.
     *
     * @return Instancia del tablero en juego.
     */
    Tablero getTablero();

    /**
     * Obtiene la cantidad de errores acumulados por el jugador.
     *
     * @return Número de errores.
     */
    int getErrores();

    /**
     * Indica si la partida ha concluido (victoria o derrota).
     *
     * @return true si el juego terminó.
     */
    boolean isJuegoTermino();

    /**
     * Indica cuántas pistas se han usado en la partida.
     *
     * @return Número de pistas utilizadas.
     */
    int getPistasRestantes();
}
