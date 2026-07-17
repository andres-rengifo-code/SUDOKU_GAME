package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase principal del modelo que gestiona el estado de una partida de Sudoku.
 *
 * Implementa {@link IJuego} para desacoplarse del controlador
 * mediante la interfaz, siguiendo el patrón Adapter junto con {@link JuegoAdapter}.
 */
public class Juego implements IJuego {

    /** Generador encargado de crear tableros Sudoku válidos. */
    private Generar generar = new Generar();

    /** Tablero actual del juego. */
    private Tablero tablero = new Tablero();

    /** Objeto encargado de validar filas, columnas y subcuadros. */
    private Validar validar = new Validar();

    /** Cantidad de errores cometidos por el jugador. */
    private int errores = 0;

    /** Cantidad de pistas utilizadas. */
    private int pistas = 0;

    /** Indica si la partida ha terminado. */
    private boolean juegoTermino = false;

    /** Almacena la solución completa del Sudoku generado. */
    private int[][] tableroSolucion = new int[6][6];

    /**
     * Lista de coordenadas de las celdas vacías.
     * Cada elemento almacena: [fila, columna].
     */
    private ArrayList<int[]> celdasVacias = new ArrayList<>();

    /** Generador de números aleatorios. */
    private Random random = new Random();

    // -----------------------------------------------------------------------
    // IJuego – implementación
    // -----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     *
     * Genera un Sudoku completo, guarda la solución,
     * elimina celdas para crear el reto y registra
     * las posiciones vacías.
     */
    @Override
    public void iniciarJuego() {
        tablero = new Tablero();
        errores = 0;
        pistas = 0;
        juegoTermino = false;
        celdasVacias.clear();

        generar.generarSudoku(tablero, 0);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tableroSolucion[i][j] = tablero.getCelda(i, j).getValor();
            }
        }

        generar.eliminarCeldasPorSubcuadro(tablero);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (tablero.getCelda(i, j).getValor() == 0) {
                    celdasVacias.add(new int[]{i, j});
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * Verifica que la celda sea editable y que el valor
     * cumpla las reglas del Sudoku antes de insertarlo.
     */
    @Override
    public boolean getionarAccion(int fila, int columna, int valor) {
        if (!tablero.getCelda(fila, columna).isEditable()) {
            return false;
        }

        Celda celdaTemporal = new Celda();
        celdaTemporal.setValor(valor);

        if (validar.validarFila(tablero, celdaTemporal, fila)
                && validar.validarColumna(tablero, celdaTemporal, columna)
                && validar.validarSubcuadro(fila, columna, tablero, celdaTemporal)) {

            tablero.getCelda(fila, columna).setValor(valor);
            tablero.getCelda(fila, columna).setEditable(false);

            // Elimina la celda resuelta de la lista de vacías
            celdasVacias.removeIf(c -> c[0] == fila && c[1] == columna);
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean verificarJugadorGano() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (tablero.getCelda(i, j).getValor() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * Tres errores equivalen a derrota.
     */
    @Override
    public boolean contadorErrores() {
        errores++;
        if (errores == 3) {
            juegoTermino = true;
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * La pista NO se entrega si solo queda UNA celda vacía
     * (el jugador debe resolver la última por sí mismo),
     * o si el juego ya terminó.
     * No hay límite de pistas.
     */
    @Override
    public int[] darPista() {
        // Bloquea solo cuando queda exactamente 1 celda o el juego terminó
        if (celdasVacias.isEmpty() || celdasVacias.size() <= 1 || juegoTermino) {
            return null;
        }

        int indice = random.nextInt(celdasVacias.size());
        int[] coordenada = celdasVacias.get(indice);
        int fila    = coordenada[0];
        int columna = coordenada[1];

        tablero.getCelda(fila, columna).setValor(tableroSolucion[fila][columna]);
        tablero.getCelda(fila, columna).setEditable(false);
        celdasVacias.remove(indice);
        pistas++;

        return coordenada;
    }

    // -----------------------------------------------------------------------
    // Getters
    // -----------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public Tablero getTablero() {
        return tablero;
    }

    /** {@inheritDoc} */
    @Override
    public int getErrores() {
        return errores;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isJuegoTermino() {
        return juegoTermino;
    }

    /**
     * {@inheritDoc}
     *
     * @return Cantidad de pistas usadas hasta el momento.
     */
    @Override
    public int getPistasRestantes() {
        return pistas;
    }
}
