package Controllers;

import Model.Juego;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.sql.Time;

public class PantallaSudokuController {

    @FXML
    private Label erroresCometidos;

    @FXML
    private GridPane matrizSudoku;

    @FXML
    private Label pistasSobrantes;

    @FXML
    private Label tiempoenjuego;

    private Juego juego = new Juego();
    private TextField[][] textFields = new TextField[6][6];
    private int minutos= 0;
    private int segundos= 0;
    private Timeline relog;

    /**
     * Solicita una pista al modelo y actualiza la interfaz.
     *
     * La celda seleccionada se rellena con el valor correcto,
     * se colorea de verde y deja de ser editable.
     */

    @FXML
    void generarPista(ActionEvent event) {
        int[] cord=juego.darPista();
        if(cord == null) return;
        textFields[cord[0]][cord[1]].setText(String.valueOf(juego.getTablero().getCelda(cord[0],cord[1]).getValor()));
        textFields[cord[0]][cord[1]].setStyle("-fx-background-color: #90EE90;");
        textFields[cord[0]][cord[1]].setEditable(false);
        pistasSobrantes.setText(String.valueOf(Integer.parseInt(pistasSobrantes.getText())-1));


    }
    /**
     * Inicia una nueva partida de Sudoku.
     *
     * Genera un nuevo tablero y solicita confirmación
     * para renderizarlo en pantalla.
     */
    @FXML
    void startGame(ActionEvent event) {
        juego.iniciarJuego();
        alert("INICIALIZACION", "se esta iniciando un nuevo juego ");

    }

    /**
     * Construye visualmente el tablero Sudoku.
     *
     * Recorre todas las celdas del modelo y crea los
     * TextField correspondientes en la interfaz.
     *
     * También configura:
     * - Restricción de entrada (1-6).
     * - Eventos del teclado.
     * - Validación de jugadas.
     * - Detección de victoria y derrota.
     */
    void renderizarTablero(){

        matrizSudoku.getChildren().clear();
        erroresCometidos.setText("0");
        pistasSobrantes.setText("3");
        minutos=0;
        segundos=0;
        if(relog != null) relog.stop();


        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){

                TextField textField = new TextField(); // Crea el componente visual que representará una celda del Sudoku.
                textField.setPrefSize(60,60);
                textField.setAlignment(Pos.CENTER);
                // Guarda una referencia al TextField para poder acceder
                // posteriormente desde otros métodos del controlador.
                textFields[i][j]=textField;


                if(juego.getTablero().getCelda(i,j).getValor() == 0){
                    textField.setText("");
                } else {
                    textField.setText(String.valueOf(juego.getTablero().getCelda(i,j).getValor()));
                    textField.setEditable(false);
                    textField.setStyle("-fx-background-color: #c7c7c7;");
                }

                // Agrega la celda a la posición correspondiente del GridPane.
                matrizSudoku.add(textField,j,i);

                final int fila = i;
                final int columna = j;

                // Permite únicamente números del 1 al 6.
                // También permite dejar la celda vacía.
                textField.setTextFormatter(new TextFormatter<>(change -> {
                    String newText = change.getControlNewText();
                    if(newText.matches("[1-6]?")){
                        return change;
                    }
                    return null;
                }));

                // Se ejecuta cada vez que el usuario libera una tecla.
                // Valida la jugada ingresada y actualiza el estado del juego.
                textField.setOnKeyReleased(event -> {
                    if(!textField.isEditable()){
                        return;
                    }

                    if(juego.isJuegoTermino()){

                        return;
                    }

                    if(!textField.getText().isEmpty()){
                        int valor = Integer.parseInt(textField.getText());
                        if(juego.getionarAccion(fila, columna, valor)){
                            textField.setStyle("-fx-background-color: #90EE90;");
                            textField.setEditable(false);
                            boolean gano = juego.verificarJugadorGano();// Comprueba si todas las celdas del tablero han sido completadas
                            if(gano){
                                alert("Victoria", "¡Has completado el Sudoku!");
                                relog.stop();
                            }
                        } else {
                            textField.setStyle("-fx-background-color: #ff0000;");
                            // Incrementa el contador de errores y verifica
                            // si el jugador alcanzó el límite permitido.
                            boolean perdio =juego.contadorErrores();
                            erroresCometidos.setText(String.valueOf(juego.getErrores()));
                            if(perdio){
                                alert("Derrota", "Has alcanzado el límite de errores.");
                                relog.stop();
                                for(int f=0; f<6; f++){
                                    for(int c=0; c<6; c++){

                                        if(!textFields[f][c].getStyle().contains("#90EE90") && !textFields[f][c].getStyle().contains("ff0000") && !textFields[f][c].getStyle().contains("#c7c7c7")){
                                            textFields[f][c].setEditable(false);
                                            textFields[f][c].setStyle("-fx-background-color: #dedede;");
                                        }
                                    }
                                }

                            }
                        }
                    }
                });


            }
        }
        cronometro();
    }
    /**
     * Inicia el cronómetro de la partida.
     *
     * Actualiza el tiempo mostrado cada segundo.
     */
    void cronometro(){
         relog = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            segundos++;// Incrementa el contador de segundos.
             // Cuando se completan 60 segundos,
            // se incrementa un minuto y se reinician los segundos.
            if (segundos == 60) {
                minutos++;
                segundos = 0;
            }
            tiempoenjuego.setText(String.format("%02d:%02d",minutos,segundos));// Actualiza el tiempo mostrado en la interfaz.
        }));

        relog.setCycleCount(Timeline.INDEFINITE);
        relog.play();


    }
    /**
     * Muestra una ventana emergente con opciones
     * para aceptar o cancelar una acción.
     *
     * Si el usuario acepta, se renderiza un nuevo tablero.
     *
     * @param tipo_alerta Título de la ventana.
     * @param imformacion Mensaje mostrado al usuario.
     */
    void alert (String tipo_alerta, String imformacion){

        Alert alert = new Alert(Alert.AlertType.NONE);

        ButtonType ok = new ButtonType("Aceptar");
        ButtonType cancelar = new ButtonType("Cancelar");

        alert.setTitle(tipo_alerta);
        alert.setHeaderText(null);
        alert.setContentText(imformacion);

        alert.getButtonTypes().setAll(ok, cancelar);

        alert.showAndWait().ifPresent(response -> {
            if (response == ok) {
                renderizarTablero();
            }else{

            }
        });

    }

}
