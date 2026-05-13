package Modelos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principal de l'aplicació JavaFX del Joc del Pingüí.
 * S'encarrega d'inicialitzar i mostrar la primera pantalla del joc.
 */
public class Main extends Application {

    /**
     * Mètode d'inici de JavaFX. Carrega la pantalla del mode de joc
     * i configura la finestra principal de l'aplicació.
     *
     * @param stage Finestra principal proporcionada per JavaFX
     * @throws Exception Si no es pot carregar el fitxer FXML
     */
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaModoJuego.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1280, 800);

        stage.setTitle("Joc del Pingu");
        stage.setScene(scene);
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        

        stage.show();
    }

    /**
     * Punt d'entrada del programa. Llança l'aplicació JavaFX.
     *
     * @param args Arguments de la línia de comandes (no s'utilitzen)
     */
    public static void main(String[] args) {
        launch();
    }
}