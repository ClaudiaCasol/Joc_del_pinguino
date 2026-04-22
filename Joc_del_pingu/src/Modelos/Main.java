package Modelos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaMenu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1280, 800);

        stage.setTitle("Joc del Pingu");
        stage.setScene(scene);
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.setMaximized(true);

        root.scaleXProperty().bind(scene.widthProperty().divide(1280.0));
        root.scaleYProperty().bind(scene.heightProperty().divide(800.0));

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}