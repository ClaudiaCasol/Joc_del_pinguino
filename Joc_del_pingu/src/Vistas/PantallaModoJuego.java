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

public class PantallaModoJuego {

    @FXML
    private ComboBox<String> comboJugadores;

    @FXML
    private void initialize() {
        comboJugadores.setItems(FXCollections.observableArrayList(
                "2 jugadores",
                "3 jugadores",
                "4 jugadores + CPU"
        ));

        comboJugadores.setValue("2 jugadores");
    }

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