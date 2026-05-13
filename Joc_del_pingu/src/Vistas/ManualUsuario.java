package Vistas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

/**
 * Controlador de la pantalla del manual d'usuari del joc.
 * Permet a l'usuari consultar les instruccions i tornar al menú de mode de joc.
 */
public class ManualUsuario {

    /**
     * Mètode d'inicialització cridat automàticament per JavaFX en carregar el FXML.
     * Mostra un missatge per consola confirmant que la pantalla s'ha inicialitzat.
     */
    @FXML
    private void initialize() {
        System.out.println("Pantalla ManualUsuario inicializada");
    }

    /**
     * Torna a la pantalla de selecció de mode de joc (PantallaModoJuego)
     * quan es prem el botó de tornar. La mida de la nova escena s'adapta
     * a la finestra actual.
     *
     * @param event Esdeveniment del botó de tornar
     */
    @FXML
    private void volver(ActionEvent event) {

        try {
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PantallaModoJuego.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            //cambiar de escena y que el tamaño de la escena se adapte a la ventana que se ha abierto.
            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
            stage.setScene(scene);
            stage.setTitle("Modo de juego");         
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}