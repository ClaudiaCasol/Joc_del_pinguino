package Vistas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PantallaModoJuego {

    @FXML
    private void multijugador(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaJuego.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Modo multijugador seleccionado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cpu(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaJuego.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Modo CPU seleccionado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}