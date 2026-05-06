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

		if (usuarios == null || usuarios.isEmpty()) {

			Text vacio = new Text("No hay jugadores registrados");
			vacio.setStyle(
					"-fx-fill: white;" +
							"-fx-font-size: 18;"
					);

			rankingContainer.getChildren().add(vacio);

			return;
		}

		for (int i = 0; i < usuarios.size(); i++) {

			Usuario u = usuarios.get(i);

			String icono = "🐧";

			if (i == 0) icono = "🥇";
			else if (i == 1) icono = "🥈";
			else if (i == 2) icono = "🥉";

			agregarJugadorRanking(
					icono + " " + u.getNombre(),
					(10 - i) + " victorias",
					(20 - i) + " partidas"
					);
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

		if (usuarios == null) {
			return;
		}

		int total = usuarios.size();

		recordLabel.setText(String.valueOf(total * 3));
		mediaLabel.setText(String.valueOf(total));
		porcentajeLabel.setText((total * 25) + "%");
		posicionLabel.setText("#1");
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