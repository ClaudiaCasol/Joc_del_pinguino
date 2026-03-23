package Vistas;

import java.util.ArrayList;

import Modelos.Foca;
import Modelos.Inventario;
import Modelos.Jugador;
import Modelos.Pinguino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PantallaModoJuego {
	@FXML
	private VBox selectorJugadores;
	@FXML
	private void multijugador(ActionEvent event) {

	    selectorJugadores.setVisible(true);
	    selectorJugadores.setManaged(true);

	    System.out.println("Elige número de jugadores");
	}
	@FXML
	
	private void dosJugadores(ActionEvent event) {
	    iniciarJuego(event, 2);
	}

	@FXML
	private void tresJugadores(ActionEvent event) {
	    iniciarJuego(event, 3);
	}

	
	@FXML
	private void cuatroJugadores(ActionEvent event) {
	    iniciarJuego(event, 4);
	}
	
	
	@FXML
	private void cpu(ActionEvent event) {

	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaJuego.fxml"));
	        Parent root = loader.load();

	        ArrayList<Jugador> jugadores = new ArrayList<>();

	        jugadores.add(new Pinguino(0, "J1", "Azul", new Inventario()));
	        jugadores.add(new Foca(0, "CPU", "Gris"));

	        PantallaJuego controller = loader.getController();
	        controller.iniciarJuego(jugadores);

	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private void iniciarJuego(ActionEvent event, int numJugadores) {

	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaJuego.fxml"));
	        Parent root = loader.load();

	        //  SE CREAN LOS JUGADORES
	        ArrayList<Jugador> jugadores = new ArrayList<>();

	        for (int i = 1; i <= numJugadores; i++) {
	            jugadores.add(new Pinguino(0, "J" + i, "Color" + i, new Inventario()));
	        }

	        //  LA SIGUIENTE PANTALLA
	        PantallaJuego controller = loader.getController();
	        controller.iniciarJuego(jugadores);

	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}