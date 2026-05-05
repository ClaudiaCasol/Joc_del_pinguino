package Vistas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import Modelos.Usuario;

public class PantallaModoJuego {

    @FXML private Button btn2Jugadors;
    @FXML private Button btn3Jugadors;
    @FXML private Button btn4Jugadors;
    @FXML private Button btnManual;
    @FXML private Label  lblInfo;

    @FXML
    private void initialize() {
        if (lblInfo != null) {
            lblInfo.setText("Selecciona el nombre de jugadors");
        }
    }

    @FXML
    private void handle2Jugadors(ActionEvent event) {
        irAPantallaLogin(event, 2);
    }

    @FXML
    private void handle3Jugadors(ActionEvent event) {
        irAPantallaLogin(event, 3);
    }

    @FXML
    private void handle4Jugadors(ActionEvent event) {
        irAPantallaLogin(event, 4);
    }

    @FXML
    private void handleManual(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Vistas/ManualUsuario.fxml")
            );
            Parent root = loader.load();

            Stage stageManual = new Stage();
            stageManual.initModality(Modality.APPLICATION_MODAL);
            stageManual.setTitle("Manual d'usuari — El Joc d'en Pingu");
            stageManual.setScene(new Scene(root, 600, 500));
            stageManual.setResizable(false);
            stageManual.showAndWait();

        } catch (IOException e) {
            System.out.println("Error obrint el manual d'usuari: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void irAPantallaLogin(ActionEvent event, int numJugadors) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Vistas/PantallaMenu.fxml")
            );
            Parent root = loader.load();

            PantallaMenu controller = loader.getController();
            controller.setnumJugadores(numJugadors);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Inici de sessió");
            stage.show();

        } catch (IOException e) {
            System.out.println("Error carregant la pantalla de login: " + e.getMessage());
            e.printStackTrace();
        }
    }
}