package Vistas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ManualUsuario {

    @FXML private Button btnTancar;

    @FXML
    private void handleTancar() {
        Stage stage = (Stage) btnTancar.getScene().getWindow();
        stage.close();
    }
}