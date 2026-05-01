package Vistas;

import java.util.ArrayList;

import Controladores.GestorBBDD;
import Modelos.Partida;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class CargarPartida {

    @FXML
    private VBox contenedor; //es el vbox del scrollbar

    @FXML
    public void initialize() {

        GestorBBDD gestor = new GestorBBDD();
        ArrayList<Partida> partidas = gestor.obtenerPartidas();

        contenedor.getChildren().clear();

        if (partidas.size() == 0) {
            Label texto = new Label("No hay partidas guardadas");
            contenedor.getChildren().add(texto);
        } else {

            for (Partida p : partidas) {
                VBox card = crearCard(p);
                contenedor.getChildren().add(card);
            }
        }
    }

    private VBox crearCard(Partida p) {

        VBox card = new VBox();
        card.setSpacing(5);

        card.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-background-radius: 10;");

        Label nombre = new Label("Partida " + p.getId());
        Label fecha = new Label("Fecha: " + p.getFecha());
        Label turnos = new Label("Turnos: " + p.getTurnos());

        card.getChildren().addAll(nombre, fecha, turnos);

        // CLICK
        card.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                cargarPartida(p.getId());
            }
        });

        return card;
    }

    private void cargarPartida(int id) {

        try {

            GestorBBDD gestor = new GestorBBDD();
            gestor.cargarTablero(id); // carga la partida

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaJuego.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) contenedor.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Partida cargada");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}