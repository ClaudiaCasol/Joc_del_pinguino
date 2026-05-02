package Vistas;

import Modelos.*;
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

import java.io.IOException;
import java.util.ArrayList;



public class PantallaMenu {
	
	private int numJugadores;
	private int actual;


    @FXML private MenuItem newGame;
    @FXML private MenuItem saveGame;
    @FXML private MenuItem loadGame;
    @FXML private MenuItem quitGame;

    @FXML private TextField userField;
    @FXML private PasswordField passField;

    @FXML private Button loginButton;
    @FXML private Button registerButton;
    
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    

    
    public void setnumJugadores(int numJugadores) {
    	this.numJugadores = numJugadores;
    }

    @FXML
    private void initialize() {
        System.out.println("PantallaMenu inicializada");
    }

    @FXML
    private void handleNewGame() {
        abrirPantallaModoJuego((Stage) loginButton.getScene().getWindow());
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

        try {
            String user = userField.getText().trim();
            String pass = passField.getText().trim();

            Usuario u = new Usuario(user, pass);
            
            

            /*if (!GestorBBDD.validarUsuario(u)) {
                //textoJugador.setText("Usuario incorrecto");
                return;
            }

            if (usuarios.stream().anyMatch(x -> x.getNombre().equals(user))) {
                //textoJugador.setText("Ese usuario ya estÃ¡ en la partida");
                return;
            }*/

            usuarios.add(u);
            actual++;

            if (actual < numJugadores) {
                userField.clear();
                passField.clear();
                actualizarTexto();
            } else {
                irAlJuego(event);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizarTexto() {
        //textoJugador.setText("Jugador " + (actual + 1));
    }
    
    private void irAlJuego(ActionEvent event) throws IOException {

    	try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaJuego.fxml"));
            Parent root = loader.load();

            PantallaJuego controller = loader.getController();
            controller.configurarPartida(numJugadores, usuarios);
            controller.prepararPantalla();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pantalla de Juego");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void abrirPantallaModoJuego(Stage stage) {
        try {
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaModoJuego.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Seleccionar modo de juego");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        System.out.println("Register pressed");
    }
}