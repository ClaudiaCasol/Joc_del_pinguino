package Vistas;

import Modelos.*;
import Controladores.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

/**
 * Controlador de la pantalla de login on els jugadors introdueixen
 * les seves credencials abans d'iniciar la partida.
 * Gestiona el login, el registre i la navegació cap a la pantalla de joc.
 */
public class PantallaMenu {

	/** Nombre total de jugadors que han d'iniciar sessió */
	private int numJugadores;

	/** Comptador del jugador que ha d'iniciar sessió en aquest moment */
	private int actual;

	/** Opció del menú per iniciar una nova partida */
	@FXML private MenuItem newGame;

	/** Opció del menú per guardar la partida */
	@FXML private MenuItem saveGame;

	/** Opció del menú per carregar una partida */
	@FXML private MenuItem loadGame;

	/** Opció del menú per sortir del joc */
	@FXML private MenuItem quitGame;

	/** Camp de text per introduir el nom d'usuari */
	@FXML private TextField userField;

	/** Camp de contrasenya per introduir la paraula de pas */
	@FXML private PasswordField passField;

	/** Botó per iniciar sessió */
	@FXML private Button loginButton;

	/** Botó per registrar un nou usuari */
	@FXML private Button registerButton;

	/** Etiqueta que mostra missatges informatius al jugador (quin jugador ha d'iniciar sessió, errors...) */
	@FXML private Label textoJugador;

	/** Llista d'usuaris que han iniciat sessió correctament per a la partida */
	private ArrayList<Usuario> usuarios = new ArrayList<>();


	/**
	 * Estableix el nombre de jugadors que han d'iniciar sessió.
	 *
	 * @param numJugadores Nombre total de jugadors de la partida
	 */
	public void setnumJugadores(int numJugadores) {
		this.numJugadores = numJugadores;
	}

	/**
	 * Mètode d'inicialització cridat automàticament per JavaFX en carregar el FXML.
	 */
	@FXML
	private void initialize() {
		System.out.println("PantallaMenu inicializada");
	}

	/**
	 * Gestiona el clic a l'opció "Nova Partida" del menú.
	 * Torna a la pantalla de selecció de mode de joc.
	 */
	@FXML
	private void handleNewGame() {
		abrirPantallaModoJuego((Stage) loginButton.getScene().getWindow());
	}

	/**
	 * Gestiona el clic a l'opció "Guardar Partida" del menú.
	 */
	@FXML
	private void handleSaveGame() {
		System.out.println("Save Game clicked");
	}

	/**
	 * Gestiona el clic a l'opció "Carregar Partida" del menú.
	 */
	@FXML
	private void handleLoadGame() {
		System.out.println("Load Game clicked");
	}

	/**
	 * Gestiona el clic a l'opció "Sortir" del menú. Tanca l'aplicació.
	 */
	@FXML
	private void handleQuitGame() {
		System.out.println("Quit Game clicked");
		System.exit(0);
	}

	/**
	 * Gestiona el clic al botó de login.
	 * Valida les credencials contra la BBDD, comprova que l'usuari no repeteixi,
	 * l'afegeix a la llista i avança al següent jugador o inicia la partida si tots han entrat.
	 *
	 * @param event Esdeveniment del botó de login
	 */
	@FXML
	private void handleLogin(ActionEvent event) {

		try {
			String user = userField.getText().trim();
			String pass = passField.getText().trim();

			Usuario u = new Usuario(user, pass);

			if (!GestorBBDD.validarUsuario(u)) {
				textoJugador.setText("Usuario incorrecto");
				return;
			}

			if (usuarios.stream().anyMatch(x -> x.getNombre().equals(user))) {
				textoJugador.setText("Ese usuario ya está en la partida");
				return;
			}

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

	/**
	 * Actualitza l'etiqueta informativa indicant quin jugador ha d'iniciar sessió ara.
	 */
	private void actualizarTexto() {
		textoJugador.setText("Perfil del jugador " + (actual + 1));
	}

	/**
	 * Carrega la pantalla de joc (PantallaJuego) un cop tots els jugadors han fet login.
	 * Configura el controlador amb el nombre de jugadors i els usuaris validats.
	 *
	 * @param event Esdeveniment del botó que ha desencadenat la transició
	 * @throws IOException Si no es pot carregar el FXML de PantallaJuego
	 */
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

	/**
	 * Obre la pantalla de selecció de mode de joc (PantallaModoJuego).
	 *
	 * @param stage Finestra actual on es canviarà l'escena
	 */
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

	/**
	 * Gestiona el clic al botó de registre.
	 * Comprova que els camps no estiguin buits, que l'usuari no existeixi ja,
	 * i crea el nou usuari a la BBDD si tot és correcte.
	 */
	@FXML
	private void handleRegister() {

		String user = userField.getText();
		String pass = passField.getText();
		Usuario usuario = new Usuario(user, pass);

		if (user.isEmpty() || pass.isEmpty()) {
			textoJugador.setText("Rellena todos los campos");
			return;
		}

		if (GestorBBDD.validarUsuario(usuario)) {
			textoJugador.setText("El usuario ya existe");
			return;
		}

		Usuario u = new Usuario(user, pass);

		if (GestorBBDD.crearUsuario(u)) {

			textoJugador.setText("Usuario creado correctamente");

		} else {

			textoJugador.setText("Error al crear usuario");

		}    
	}
}