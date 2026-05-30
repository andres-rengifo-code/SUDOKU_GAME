package Controllers;

import Model.Juego;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
    private TextField[][] textFields = new TextField[9][9];
    private int minutos= 0;
    private int segundos= 0;
    private Timeline relog;



    @FXML
    void generarPista(ActionEvent event) {
        int[] cord=juego.darPista();
        if(cord == null) return;
        textFields[cord[0]][cord[1]].setText(String.valueOf(juego.getTablero().getCelda(cord[0],cord[1]).getValor()));
        textFields[cord[0]][cord[1]].setStyle("-fx-background-color: #90EE90;");
        textFields[cord[0]][cord[1]].setEditable(false);
        pistasSobrantes.setText(String.valueOf(Integer.parseInt(pistasSobrantes.getText())-1));


    }
    /*
    @FXML
    void startGameEasyLevel(ActionEvent event) {
        juego.iniciarJuego("facil");
        renderizarTablero();


    }

    @FXML
    void startGameHardLevel(ActionEvent event) {
        juego.iniciarJuego("dificil");
        renderizarTablero();


    }

     */

    @FXML
    void startGame(ActionEvent event) {
        juego.iniciarJuego();
        renderizarTablero();

    }

    void renderizarTablero(){

        matrizSudoku.getChildren().clear();
        erroresCometidos.setText("0");
        pistasSobrantes.setText("3");
        minutos=0;
        segundos=0;
        if(relog != null) relog.stop();


        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){

                TextField textField = new TextField();
                textField.setPrefSize(60,60);
                textField.setAlignment(Pos.CENTER);
                textFields[i][j]=textField;


                if(juego.getTablero().getCelda(i,j).getValor() == 0){
                    textField.setText("");
                } else {
                    textField.setText(String.valueOf(juego.getTablero().getCelda(i,j).getValor()));
                    textField.setEditable(false);
                    textField.setStyle("-fx-background-color: #c7c7c7;");
                }


                matrizSudoku.add(textField,j,i);

                final int fila = i;
                final int columna = j;

                textField.setTextFormatter(new TextFormatter<>(change -> {
                    String newText = change.getControlNewText();
                    if(newText.matches("[1-6]?")){
                        return change;
                    }
                    return null;
                }));


                textField.setOnKeyReleased(event -> {
                    if(!textField.isEditable()){
                        return;
                    }

                    if(juego.isJuegoTermino()){

                        return;
                    }
                    /*
                    if(!juego.getTablero().getCelda(fila, columna).isEditable()){
                        return;
                    }

                     */

                    if(!textField.getText().isEmpty()){
                        int valor = Integer.parseInt(textField.getText());
                        if(juego.getionarAccion(fila, columna, valor)){
                            textField.setStyle("-fx-background-color: #90EE90;");
                            textField.setEditable(false);
                            boolean gano = juego.verificarJugadorGano();
                            if(gano){
                                System.out.println("Jugador gano");
                                relog.stop();
                            }
                        } else {
                            textField.setStyle("-fx-background-color: #ff0000;");
                            boolean perdio =juego.contadorErrores();
                            erroresCometidos.setText(String.valueOf(juego.getErrores()));
                            if(perdio){
                                System.out.println("JUGADOR PERDIO");
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

    void cronometro(){
         relog = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            segundos++;
            if (segundos == 60) {
                minutos++;
                segundos = 0;
            }
            tiempoenjuego.setText(String.format("%02d:%02d",minutos,segundos));
        }));

        relog.setCycleCount(Timeline.INDEFINITE);
        relog.play();


    }

}
