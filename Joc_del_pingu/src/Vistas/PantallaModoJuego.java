package Vistas;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Controlador de la pantalla principal del menú del joc.
 * Permet seleccionar el nombre de jugadors i navegar a les
 * diferents seccions: nova partida, manual, carregar partida i estadístiques.
 */
public class PantallaModoJuego {

    /** ComboBox per seleccionar el nombre de jugadors de la partida */
    @FXML
    private ComboBox<String> comboJugadores;

    /**
     * Mètode d'inicialització cridat automàticament per JavaFX en carregar el FXML.
     * Omple el ComboBox amb les opcions de jugadors i estableix el valor per defecte.
     */
    @FXML
    private void initialize() {
        comboJugadores.setItems(FXCollections.observableArrayList(
                "2 jugadores",
                "3 jugadores",
                "4 jugadores + CPU"
        ));

        comboJugadores.setValue("2 jugadores");
    }

    /**
     * Inicia el procés per començar una nova partida.
     * Llegeix el nombre de jugadors seleccionat i navega a la pantalla de login (PantallaMenu).
     *
     * @param event Esdeveniment del botó d'iniciar partida
     */
    @FXML
    private void iniciarPartida(ActionEvent event) {
        try {
            int numeroJugadores = obtenerNumeroJugadores();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaMenu.fxml"));
            Parent root = loader.load();

            PantallaMenu controller = loader.getController();
            controller.setnumJugadores(numeroJugadores);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pantalla de Login");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navega a la pantalla del manual d'usuari.
     *
     * @param event Esdeveniment del botó del manual
     */
    @FXML
    private void irManual(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ManualUsuario.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Manual de usuario");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Navega a la pantalla de càrrega de partides guardades.
     *
     * @param event Esdeveniment del botó de carregar partida
     */
    @FXML
    private void irCargarPartida(ActionEvent event) {
         try {

             FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/CargarPartida.fxml"));
             Parent root = loader.load();

             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

             stage.setScene(new Scene(root));
             stage.setTitle("Cargar Partida");

         } catch (Exception e) {
             e.printStackTrace();
         }
     }
    
    /**
     * Navega a la pantalla d'estadístiques globals del joc.
     * Passa una llista buida d'usuaris fins que es connecti amb la BBDD de partides.
     *
     * @param event Esdeveniment del botó d'estadístiques
     */
    @FXML
    private void irEstadisticas(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Vistas/Estadisticas.fxml")
            );

            Parent root = loader.load();

            Estadisticas controller = loader.getController();

            // PASAMOS LOS USUARIOS REALES
            // de momento vacío hasta conectar partidas/BBDD

            controller.setUsuarios(new ArrayList<>());

            Stage stage = (Stage)
                    ((Node) event.getSource())
                            .getScene()
                            .getWindow();

            stage.setScene(new Scene(root));

            stage.setTitle("Estadísticas");

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converteix l'opció seleccionada al ComboBox en un nombre enter de jugadors.
     *
     * @return Nombre de jugadors (2, 3 o 4); per defecte retorna 2
     */
    private int obtenerNumeroJugadores() {
        String opcion = comboJugadores.getValue();

        switch (opcion) {
            case "2 jugadores":
                return 2;
            case "3 jugadores":
                return 3;
            case "4 jugadores + CPU":
                return 4;
            default:
                return 2;
        }
    }
}