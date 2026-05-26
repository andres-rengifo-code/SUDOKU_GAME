module universidad.sudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports Controllers;
    opens Controllers to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;

}