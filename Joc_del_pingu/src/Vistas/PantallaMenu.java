package Vistas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class PantallaMenu {

    @FXML private MenuItem newGame;
    @FXML private MenuItem saveGame;
    @FXML private MenuItem loadGame;
    @FXML private MenuItem quitGame;

    @FXML private TextField userField;
    @FXML private PasswordField passField;

    @FXML private Button loginButton;
    @FXML private Button registerButton;

    @FXML
    private void initialize() {
        System.out.println("PantallaMenu inicializada");
    }

    @FXML
    private void handleNewGame() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaModoJuego.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seleccionar modo de juego");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveGame() {
        System.out.println("Save Game clicked");
    }

    @FXML
    private void handleLoadGame() {
        System.out.println("Load Game clicked");
    }

    @FXML
    private void handleQuitGame() {
        System.out.println("Quit Game clicked");
        System.exit(0);
    }

    @FXML
    private void handleLogin(ActionEvent event) {

        String username = userField.getText();
        String password = passField.getText();

        System.out.println("Login pressed: " + username + " / " + password);

        if (!username.isEmpty() && !password.isEmpty()) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaJuego.fxml"));
                Parent pantallaJuegoRoot = loader.load();

                Scene pantallaJuegoScene = new Scene(pantallaJuegoRoot);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(pantallaJuegoScene);
                stage.setTitle("Pantalla de Juego");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Introduce usuario y contraseña.");
        }
    }

    @FXML
    private void handleRegister() {
        System.out.println("Register pressed");
    }
}