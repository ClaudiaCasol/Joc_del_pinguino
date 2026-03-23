package Modelos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
<<<<<<< Updated upstream
    public void start(Stage stage) throws Exception {
=======
    public void start(Stage stage) {
        try {
            // Cargar la primera pantalla (PantallaMenu)
            Parent root = FXMLLoader.load(getClass().getResource("/Vistas/PantallaMenu.fxml"));
>>>>>>> Stashed changes

            Scene scene = new Scene(root);

            stage.setTitle("Joc del Pingu");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}