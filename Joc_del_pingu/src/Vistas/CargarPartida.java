package Vistas;

import java.io.IOException;
import java.util.ArrayList;
import Modelos.Partida;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class CargarPartida {

    @FXML
    private VBox contenedorPartidas;

    private HBox selectedCard = null;

    @FXML
    public void initialize() {

        // Obtenir les partides guardades a la base de dades
        ArrayList<Partida> partidas = obtenerPartidasGuardadas();

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

    /**
     * Obté les partides guardades des de la base de dades.
     * Retorna una llista buida si no hi ha connexió o no existeix cap partida.
     *
     * @return ArrayList amb les partides trobades.
     */
    private ArrayList<Partida> obtenerPartidasGuardadas() {
        ArrayList<Partida> partidas = new ArrayList<>();

        try {
            java.sql.Connection con = Controladores.BBDD.conectarBaseDatos();

            if (con == null) {
                System.out.println("No s'ha pogut connectar a la BBDD per carregar partides.");
                return partidas;
            }

            String sql = "SELECT ID_PARTIDA, DATA_PARTIDA FROM PARTIDA WHERE ACTIVA = 1 ORDER BY DATA_PARTIDA DESC";
            ArrayList<java.util.LinkedHashMap<String, String>> resultats = Controladores.BBDD.select(con, sql);

            for (java.util.LinkedHashMap<String, String> fila : resultats) {
                Partida p = new Partida();
                p.setId(Integer.parseInt(fila.get("ID_PARTIDA")));
                p.setFecha(fila.get("DATA_PARTIDA"));
                partidas.add(p);
            }

            Controladores.BBDD.cerrar(con);

        } catch (Exception e) {
            System.out.println("Error carregant partides: " + e.getMessage());
        }

        return partidas;
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

        String ruta = getClass().getResource("/imatges/brush.png").toExternalForm();

        return "-fx-background-color: #7cc6e6;" +
               "-fx-background-image: url('" + ruta + "');" +
               "-fx-background-size: cover;" +
               "-fx-background-repeat: no-repeat;" +
               "-fx-background-position: center;" +
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
        selectedCard = card;
    }

    private void cargarPartida(int id) {
        System.out.println("Cargar partida " + id);
    }

    @FXML
    private void volverPantallaModoJuego(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("PantallaModoJuego.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1280, 800);

            stage.setScene(scene);
            stage.setMinWidth(1000);
            stage.setMinHeight(700);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}