package Vistas;

import java.util.ArrayList;

import Controladores.GestorBBDD;
import Modelos.Usuario;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
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
    private VBox recordPlayersContainer;

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

        generarRanking();

        generarStats();

        generarActividad();

        generarRecordHolders();
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {

        this.usuarios = usuarios;

        generarStats();

        generarActividad();
    }

    private void generarRanking() {

        rankingContainer.getChildren().clear();

        ArrayList<Usuario> ranking =
                GestorBBDD.obtenerRankingUsuarios();

        for(int i = 0; i < ranking.size(); i++) {

            Usuario u = ranking.get(i);

            String icono = "🐧";

            if(i == 0) icono = "🥇";
            else if(i == 1) icono = "🥈";
            else if(i == 2) icono = "🥉";

            VBox card = crearCardRanking(
                    icono,
                    u.getNombre(),
                    u.getNumPartidasJugadas(),
                    i + 1
            );

            rankingContainer.getChildren().add(card);
        }
    }

    private VBox crearCardRanking(
            String icono,
            String nombre,
            int partidas,
            int posicion) {

        VBox card = new VBox();

        card.setSpacing(10);

        card.setPadding(new Insets(18));

        card.setStyle(
                "-fx-background-color: rgba(255,255,255,0.28);" +
                "-fx-background-radius: 18;"
        );

        Text nombreTxt = new Text(
                icono + " " + nombre
        );

        nombreTxt.setStyle(
                "-fx-fill: #1f4e79;" +
                "-fx-font-size: 22;" +
                "-fx-font-weight: bold;"
        );

        Text partidasTxt = new Text(
                "🎮 " + partidas + " partidas jugadas"
        );

        partidasTxt.setStyle(
                "-fx-fill: #356b8c;" +
                "-fx-font-size: 16;"
        );

        Text posicionTxt = new Text(
                "🏆 Posición #" + posicion
        );

        posicionTxt.setStyle(
                "-fx-fill: #1f4e79;" +
                "-fx-font-size: 16;"
        );

        card.getChildren().addAll(
                nombreTxt,
                partidasTxt,
                posicionTxt
        );

        return card;
    }

    private void generarStats() {

        int record =
                GestorBBDD.obtenerRecord();

        double media =
                GestorBBDD.obtenerMedia();

        recordLabel.setText(
        		
                String.valueOf(record)
                
        );

        mediaLabel.setText(
                String.format("%.2f", media)
        );

        double porcentaje =
                GestorBBDD.obtenerPorcentaje(record);

        porcentajeLabel.setText(
                porcentaje + "%"
        );
    }

    private void generarActividad() {

        actividadContainer.getChildren().clear();

        ArrayList<String> jugadoresTop =
                GestorBBDD.obtenerJugadoresSobreMedia();

        for(String nombre : jugadoresTop) {

            Text texto = new Text(
                    "🔥 " + nombre +
                    " supera la media global"
            );

            texto.setStyle(
                    "-fx-fill: white;" +
                    "-fx-font-size: 16;"
            );

            actividadContainer
                    .getChildren()
                    .add(texto);
        }
    }

    private void generarRecordHolders() {

        recordPlayersContainer
                .getChildren()
                .clear();

        ArrayList<String> jugadores =
                GestorBBDD.obtenerJugadoresRecord();

        for(String nombre : jugadores) {

            Text texto = new Text(
                    "👑 " + nombre
            );

            texto.setStyle(
                    "-fx-fill: white;" +
                    "-fx-font-size: 18;" +
                    "-fx-font-weight: bold;"
            );

            recordPlayersContainer
                    .getChildren()
                    .add(texto);
        }
    }

    private void animarEntrada() {

        FadeTransition fade =
                new FadeTransition(
                        Duration.seconds(1),
                        rankingContainer
                );

        fade.setFromValue(0);

        fade.setToValue(1);

        fade.play();

        TranslateTransition translate =
                new TranslateTransition(
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

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/Vistas/PantallaModoJuego.fxml"
                            )
                    );

            Parent root = loader.load();

            Stage stage =
                    (Stage)
                    ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));

            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}