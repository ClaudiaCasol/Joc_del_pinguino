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

/**
 * Controlador de la pantalla d'estadístiques del joc.
 * Mostra el rànquing de jugadors, estadístiques globals i els jugadors destacats.
 */
public class Estadisticas {

    /** Contenidor del rànquing de jugadors */
    @FXML
    private VBox rankingContainer;

    /** Contenidor de l'activitat dels jugadors per sobre de la mitjana */
    @FXML
    private VBox actividadContainer;

    /** Contenidor dels jugadors amb el rècord de victòries */
    @FXML
    private VBox recordPlayersContainer;

    /** Text que mostra el rècord màxim de victòries */
    @FXML
    private Text recordLabel;

    /** Text que mostra la mitjana de victòries */
    @FXML
    private Text mediaLabel;

    /** Text que mostra el percentatge d'usuaris per sota del rècord */
    @FXML
    private Text porcentajeLabel;

    /** Text que mostra la posició de l'usuari al rànquing */
    @FXML
    private Text posicionLabel;

    /** Llista d'usuaris de la partida actual (pot ser null si es carrega des del menú) */
    private ArrayList<Usuario> usuarios;

    /**
     * Mètode d'inicialització cridat automàticament per JavaFX en carregar el FXML.
     * Aplica l'animació d'entrada i carrega totes les seccions d'estadístiques.
     */
    @FXML
    public void initialize() {

        animarEntrada();

        generarRanking();

        generarStats();

        generarActividad();

        generarRecordHolders();
    }

    /**
     * Estableix la llista d'usuaris actuals i actualitza les estadístiques.
     *
     * @param usuarios Llista d'usuaris de la partida actual
     */
    public void setUsuarios(ArrayList<Usuario> usuarios) {

        this.usuarios = usuarios;

        generarStats();

        generarActividad();
    }

    /**
     * Genera i omple el contenidor del rànquing amb targetes per a cada usuari.
     * Els tres primers rebran icones de medalla (or, plata, bronze).
     */
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

    /**
     * Crea una targeta visual per a un jugador del rànquing.
     *
     * @param icono    Icona a mostrar (medalla o pingüí)
     * @param nombre   Nom del jugador
     * @param partidas Nombre de partides jugades
     * @param posicion Posició al rànquing
     * @return VBox amb la targeta del jugador
     */
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

    /**
     * Obté i mostra les estadístiques globals: rècord, mitjana i percentatge.
     */
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

    /**
     * Genera la secció d'activitat mostrant els jugadors que superen la mitjana global de victòries.
     */
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

    /**
     * Genera la secció dels jugadors amb el rècord màxim de victòries.
     */
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

    /**
     * Aplica una animació d'entrada de tipus FadeIn i translació horitzontal
     * al contenidor del rànquing quan es carrega la pantalla.
     */
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

    /**
     * Torna al menú principal (PantallaModoJuego) quan es prem el botó de tornar.
     *
     * @param event Esdeveniment del botó de tornar
     */
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