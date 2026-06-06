package Controllers;

import Model.IJuego;
import Model.JuegoAdapter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * Controlador de la pantalla principal del Sudoku.
 *
 * Implementa {@link ITableroRenderer} para separar la responsabilidad
 * de renderizado del resto de la lógica de la aplicación.
 *
 * Usa {@link IJuego} (vía {@link JuegoAdapter}) para comunicarse con el
 * modelo sin depender de la clase concreta {@link Model.Juego}, siguiendo
 * el patrón Adapter.
 */
public class PantallaSudokuController implements Controllers.ITableroRenderer {

    // -----------------------------------------------------------------------
    // FXML bindings
    // -----------------------------------------------------------------------

    @FXML private Label    erroresCometidos;
    @FXML private GridPane matrizSudoku;
    @FXML private Label    pistasSobrantes;
    @FXML private Label    tiempoenjuego;

    // -----------------------------------------------------------------------
    // Estado interno
    // -----------------------------------------------------------------------

    /**
     * Referencia al modelo a través de la interfaz {@link IJuego}.
     * Se instancia mediante {@link JuegoAdapter} para aplicar el patrón Adapter.
     */
    private IJuego juego = new JuegoAdapter();

    /** Matriz de referencias a los TextField de la cuadrícula. */
    private TextField[][] textFields = new TextField[6][6];

    /** Contadores del cronómetro. */
    private int minutos  = 0;
    private int segundos = 0;

    /** Timeline del cronómetro. */
    private Timeline relog;

    // -----------------------------------------------------------------------
    // Acciones FXML
    // -----------------------------------------------------------------------

    /**
     * Solicita una pista al modelo y actualiza la interfaz.
     *
     * La celda seleccionada se rellena con el valor correcto,
     * se colorea de verde y deja de ser editable.
     *
     * El botón no hace nada si el modelo rechaza la pista
     * (quedan ≤1 celda vacía, se agotaron las 3 pistas o el juego terminó).
     */
    @FXML
    void generarPista(ActionEvent event) {
        int[] cord = juego.darPista();
        if (cord == null) return;

        int fila    = cord[0];
        int columna = cord[1];

        textFields[fila][columna].setText(
                String.valueOf(juego.getTablero().getCelda(fila, columna).getValor()));
        textFields[fila][columna].setStyle("-fx-background-color: #90EE90;");
        textFields[fila][columna].setEditable(false);
    }

    /**
     * Inicia una nueva partida de Sudoku.
     *
     * Genera un nuevo tablero y solicita confirmación al usuario
     * antes de renderizarlo en pantalla.
     */
    @FXML
    void startGame(ActionEvent event) {
        juego.iniciarJuego();
        alert("INICIALIZACIÓN", "Se está iniciando un nuevo juego.");
    }

    // -----------------------------------------------------------------------
    // ITableroRenderer – implementación
    // -----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     *
     * Construye visualmente el tablero Sudoku.
     * Recorre todas las celdas del modelo y crea los TextField correspondientes.
     *
     * También configura:
     * - Bordes diferenciados por subcuadro 2×3 (oscuro en exterior, claro en interior).
     * - Restricción de entrada (solo dígitos 1-6).
     * - Eventos de teclado para validar jugadas.
     * - Detección de victoria y derrota.
     */
    @Override
    public void renderizarTablero() {
        matrizSudoku.getChildren().clear();
        erroresCometidos.setText("0");
        pistasSobrantes.setText("");
        minutos  = 0;
        segundos = 0;

        if (relog != null) relog.stop();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {

                TextField textField = new TextField();
                textField.setPrefSize(60, 60);
                textField.setAlignment(Pos.CENTER);
                textFields[i][j] = textField;

                if (juego.getTablero().getCelda(i, j).getValor() == 0) {
                    textField.setText("");
                } else {
                    textField.setText(String.valueOf(juego.getTablero().getCelda(i, j).getValor()));
                    textField.setEditable(false);
                    textField.setStyle("-fx-background-color: #c7c7c7;");
                }

                matrizSudoku.add(textField, j, i);

                final int fila    = i;
                final int columna = j;

                // Permite únicamente números del 1 al 6 (o campo vacío).
                textField.setTextFormatter(new TextFormatter<>(change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("[1-6]?")) {
                        return change;
                    }
                    return null;
                }));

                // Valida la jugada cada vez que el usuario suelta una tecla.
                textField.setOnKeyReleased(e -> {
                    if (!textField.isEditable() || juego.isJuegoTermino()) return;

                    if (!textField.getText().isEmpty()) {
                        int valor = Integer.parseInt(textField.getText());

                        if (juego.getionarAccion(fila, columna, valor)) {
                            textField.setStyle("-fx-background-color: #90EE90;");
                            textField.setEditable(false);

                            if (juego.verificarJugadorGano()) {
                                alert("Victoria", "¡Has completado el Sudoku!");
                                relog.stop();
                            }
                        } else {
                            textField.setStyle("-fx-background-color: #ff0000;");

                            erroresCometidos.setText(String.valueOf(juego.getErrores() + 1));
                            boolean perdio = juego.contadorErrores();
                            erroresCometidos.setText(String.valueOf(juego.getErrores()));

                            if (perdio) {
                                alert("Derrota", "Has alcanzado el límite de errores.");
                                relog.stop();
                                bloquearTableroAlPerder();
                            }
                        }
                    }
                });
            }
        }

        cronometro();
    }

    /**
     * {@inheritDoc}
     *
     * Actualiza el tiempo mostrado cada segundo.
     */
    @Override
    public void cronometro() {
        relog = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            segundos++;
            if (segundos == 60) {
                minutos++;
                segundos = 0;
            }
            tiempoenjuego.setText(String.format("%02d:%02d", minutos, segundos));
        }));
        relog.setCycleCount(Timeline.INDEFINITE);
        relog.play();
    }

    /**
     * {@inheritDoc}
     *
     * Si el usuario acepta, se renderiza un nuevo tablero.
     */
    @Override
    public void alert(String tipoAlerta, String informacion) {
        Alert alert = new Alert(Alert.AlertType.NONE);

        ButtonType ok       = new ButtonType("Aceptar");
        ButtonType cancelar = new ButtonType("Cancelar");

        alert.setTitle(tipoAlerta);
        alert.setHeaderText(null);
        alert.setContentText(informacion);
        alert.getButtonTypes().setAll(ok, cancelar);

        alert.showAndWait().ifPresent(response -> {
            if (response == ok) {
                renderizarTablero();
            }
        });
    }

    // -----------------------------------------------------------------------
    // Helpers privados
    // -----------------------------------------------------------------------

    /**
     * Bloquea todas las celdas que no fueron completadas cuando el jugador pierde,
     * aplicando el estilo correspondiente.
     */
    private void bloquearTableroAlPerder() {
        for (int f = 0; f < 6; f++) {
            for (int c = 0; c < 6; c++) {
                TextField tf = textFields[f][c];
                if (tf.isEditable()) {
                    tf.setEditable(false);
                    tf.setStyle("-fx-background-color: #dedede;");
                }
            }
        }
    }

}
