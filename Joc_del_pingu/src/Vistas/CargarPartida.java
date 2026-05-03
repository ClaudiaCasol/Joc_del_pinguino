package Vistas;

import java.util.ArrayList;

import Controladores.GestorBBDD;
import Modelos.Partida;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CargarPartida {

    @FXML
    private VBox contenedorPartidas;

    private VBox selectedCard = null;

    @FXML
    public void initialize() {

        GestorBBDD gestor = new GestorBBDD();
        ArrayList<Partida> partidas = gestor.obtenerPartidas();

        contenedorPartidas.getChildren().clear();

        int totalSlots = Math.max(8, partidas.size());

        for (int i = 0; i < totalSlots; i++) {

            VBox card;

            if (i < partidas.size()) {
                card = crearCard(partidas.get(i));
            } else {
                card = crearSlotVacio(i);
            }

            contenedorPartidas.getChildren().add(card);
        }
    }

    private VBox crearCard(Partida p) {

        VBox contenedor = new VBox();
        contenedor.setPrefWidth(800);

        HBox card = new HBox();
        card.setSpacing(20);
        card.setStyle(estiloNormal());

        Label icono = new Label("🐧");
        icono.setStyle("-fx-font-size: 20;");

        Label nombre = new Label("PARTIDA " + p.getId());
        nombre.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        Region spacer = new Region();
        spacer.setMinWidth(200);

        Label fecha = new Label(p.getFecha());
        fecha.setStyle("-fx-font-size: 12;");

        card.getChildren().addAll(icono, nombre, spacer, fecha);

        contenedor.getChildren().add(card);

        card.setOnMouseClicked(e -> {
            seleccionar(card);
            cargarPartida(p.getId());
        });

        return contenedor;
    }

    private VBox crearSlotVacio(int index) {

        VBox contenedor = new VBox();
        contenedor.setPrefWidth(800);

        HBox card = new HBox();
        card.setSpacing(20);
        card.setStyle(estiloVacio());

        Label icono = new Label("➕");
        icono.setStyle("-fx-font-size: 18;");

        Label texto = new Label("NUEVA PARTIDA");
        texto.setStyle("-fx-font-size: 13;");

        card.getChildren().addAll(icono, texto);

        contenedor.getChildren().add(card);

        return contenedor;
    }

    private String estiloNormal() {
        return "-fx-background-color: rgba(255,255,255,0.85);" +
               "-fx-padding: 15;" +
               "-fx-background-radius: 4;";
    }

    private String estiloSeleccionado() {
        return "-fx-background-color: #7cc6e6;" +
               "-fx-padding: 15;" +
               "-fx-background-radius: 4;";
    }

    private String estiloVacio() {
        return "-fx-background-color: rgba(200,200,200,0.3);" +
               "-fx-padding: 15;" +
               "-fx-background-radius: 4;";
    }

   
    private void seleccionar(HBox card) {

        if (selectedCard != null) {
            selectedCard.setStyle(estiloNormal());
        }

        card.setStyle(estiloSeleccionado());
        selectedCard = (VBox) card.getParent();
    }

    private void cargarPartida(int id) {
        System.out.println("Cargar partida " + id);
    }
}