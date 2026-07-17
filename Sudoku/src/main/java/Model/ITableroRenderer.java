package Controllers;

/**
 * Interfaz que define el contrato para renderizar el tablero Sudoku en la UI.
 *
 * Desacopla la lógica de construcción visual del controlador concreto,
 * permitiendo distintas implementaciones de renderizado (por ejemplo,
 * con estilos diferentes o pruebas unitarias con mocks).
 */
public interface ITableroRenderer {

    /**
     * Construye y muestra el tablero completo en la interfaz gráfica.
     *
     * Debe limpiar el estado previo, crear todos los TextField,
     * aplicar los estilos de subcuadro y configurar los eventos de interacción.
     */
    void renderizarTablero();

    /**
     * Inicia el cronómetro de la partida desde cero.
     */
    void cronometro();

    /**
     * Muestra una ventana de diálogo al usuario.
     *
     * Si el usuario acepta, se debe renderizar un nuevo tablero.
     *
     * @param tipoAlerta  Título de la ventana emergente.
     * @param informacion Mensaje descriptivo mostrado al usuario.
     */
    void alert(String tipoAlerta, String informacion);
}
