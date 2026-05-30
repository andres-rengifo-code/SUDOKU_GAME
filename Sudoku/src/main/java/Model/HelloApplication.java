package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/IGU/PanatllaSudoku.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 700);
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }
}
