package Vistas;

import java.util.ArrayList;

import Modelos.Usuario;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import Controladores.GestorBBDD;

public class Estadisticas {

	@FXML
	private VBox rankingContainer;

	@FXML
	private VBox actividadContainer;

	@FXML
	private Text recordLabel;

	@FXML
	private Text mediaLabel;

	@FXML
	private Text porcentajeLabel;

	@FXML
	private Text posicionLabel;

	private ArrayList<Usuario> usuarios;

	@FXML
	public void initialize() {

		animarEntrada();
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {

		this.usuarios = usuarios;

		generarRanking();
		generarActividad();
		generarStats();
	}

	private void generarRanking() {

	    rankingContainer.getChildren().clear();

	    ArrayList<String> ranking =
	            GestorBBDD.obtenerRanking();

	    int index = 0;

	    for(String texto : ranking) {

	        String icono = "🐧";

	        if(index == 0) icono = "🥇";
	        else if(index == 1) icono = "🥈";
	        else if(index == 2) icono = "🥉";

	        agregarJugadorRanking(icono + " " + texto, "Ranking global", "");

	        index++;
	    }
	}

	private void agregarJugadorRanking(String nombre,
			String victorias,
			String partidas) {

		VBox card = new VBox();

		card.setSpacing(8);

		card.setStyle(
				"-fx-background-color: rgba(255,255,255,0.20);" +
						"-fx-background-radius: 18;" +
						"-fx-padding: 18;" +
						"-fx-cursor: hand;"
				);

		Text nombreTxt = new Text(nombre);

		nombreTxt.setStyle(
				"-fx-fill: white;" +
						"-fx-font-size: 22;" +
						"-fx-font-weight: bold;"
				);

		Text victoriasTxt = new Text("🏆 " + victorias);

		victoriasTxt.setStyle(
				"-fx-fill: white;" +
						"-fx-font-size: 16;"
				);

		Text partidasTxt = new Text("🎮 " + partidas);

		partidasTxt.setStyle(
				"-fx-fill: white;" +
						"-fx-font-size: 16;"
				);

		card.getChildren().addAll(
				nombreTxt,
				victoriasTxt,
				partidasTxt
				);

		card.setOnMouseEntered(e -> {

			card.setStyle(
					"-fx-background-color: rgba(255,255,255,0.32);" +
							"-fx-background-radius: 18;" +
							"-fx-padding: 18;" +
							"-fx-cursor: hand;" +
							"-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.4), 15,0,0,0);"
					);

		});

		card.setOnMouseExited(e -> {

			card.setStyle(
					"-fx-background-color: rgba(255,255,255,0.20);" +
							"-fx-background-radius: 18;" +
							"-fx-padding: 18;" +
							"-fx-cursor: hand;"
					);

		});

		rankingContainer.getChildren().add(card);
	}

	private void generarActividad() {

		actividadContainer.getChildren().clear();

		if (usuarios == null) {
			return;
		}

		for (Usuario u : usuarios) {

			Text texto = new Text(
					"• " + u.getNombre() + " sigue sobreviviendo al hielo"
					);

			texto.setStyle(
					"-fx-fill: white;" +
							"-fx-font-size: 16;"
					);

			actividadContainer.getChildren().add(texto);
		}
	}

	private void generarStats() {

	    int record = GestorBBDD.obtenerRecord();

	    double media = GestorBBDD.obtenerMedia();

	    double porcentaje = GestorBBDD.obtenerPorcentaje(record);

	    recordLabel.setText(String.valueOf(record));

	    mediaLabel.setText(String.valueOf(media));

	    porcentajeLabel.setText(porcentaje + "%");

	    if (usuarios != null && !usuarios.isEmpty()) {

	        String nombre = usuarios.get(0).getNombre();

	        int posicion =
	                GestorBBDD.obtenerPosicion(nombre);

	        posicionLabel.setText("#" + posicion);
	    }
	}

	private void animarEntrada() {

		FadeTransition fade = new FadeTransition(
				Duration.seconds(1),
				rankingContainer
				);

		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();

		TranslateTransition translate = new TranslateTransition(
				Duration.seconds(0.8),
				rankingContainer
				);

		translate.setFromX(-40);
		translate.setToX(0);
		translate.play();
	}


	@FXML
	private void volverMenu(ActionEvent event) {

		try {

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/Vistas/PantallaModoJuego.fxml")
					);

			Parent root = loader.load();

			Stage stage = (Stage)
					((Node) event.getSource())
					.getScene()
					.getWindow();

			stage.setScene(new Scene(root));

			stage.setTitle("Modo de Juego");

			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}