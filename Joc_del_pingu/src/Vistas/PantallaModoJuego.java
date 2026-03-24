package Vistas;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class PantallaModoJuego {

    @FXML
    private ComboBox<String> comboJugadores;

    @FXML
    private void initialize() {
        comboJugadores.setItems(FXCollections.observableArrayList(
                "2 jugadors",
                "3 jugadors",
                "4 jugadors + CPU"
        ));

        comboJugadores.setValue("2 jugadors");
    }

    @FXML
    private void iniciarPartida(ActionEvent event) {
        try {
            int numeroJugadores = obtenerNumeroJugadores();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaJuego.fxml"));
            Parent root = loader.load();

            PantallaJuego controller = loader.getController();
            controller.configurarPartida(numeroJugadores);
            controller.prepararPantalla();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pantalla de Juego");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int obtenerNumeroJugadores() {
        String opcion = comboJugadores.getValue();

        switch (opcion) {
            case "2 jugadors":
                return 2;
            case "3 jugadors":
                return 3;
            case "4 jugadors + CPU":
                return 4;
            default:
                return 2;
        }
    }
}