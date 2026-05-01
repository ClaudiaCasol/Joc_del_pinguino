package Vistas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class ManualUsuario {

    @FXML
    private void initialize() {
        System.out.println("Pantalla ManualUsuario inicializada");
    }

    @FXML
    private void volver(ActionEvent event) {

        try {
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaModoJuego.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            //cambiar de escena y que el tamaño de la escena se adapte a la ventana que se ha abierto.
            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
            stage.setScene(scene);
            stage.setTitle("Modo de juego");         
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}