package Modelos;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {
	private MediaPlayer efectoActual;
    private MediaPlayer musica;
    private double volumenGeneral = 1.0;
    
    public void reproducirMusica(String ruta) {
        if (musica != null) {
        	return;
        }

        try {
            Media media = new Media(
                new java.io.File("src" + ruta).toURI().toString()
            );

            musica = new MediaPlayer(media);
            musica.setCycleCount(MediaPlayer.INDEFINITE);
            musica.setVolume(volumenGeneral);
            musica.play();

        } catch (Exception e) {
            System.out.println("Error cargando música: " + ruta);
            e.printStackTrace();
        }
    }

    public void reproducirEfecto(String ruta) {
        try {
            //parar sonido anterior si existe
            if (efectoActual != null) {
                efectoActual.stop();
            }

            Media media = new Media(
                new java.io.File("src" + ruta).toURI().toString()
            );

            efectoActual = new MediaPlayer(media);
            efectoActual.setVolume(volumenGeneral);;
            efectoActual.play();

        } catch (Exception e) {
            System.out.println("Error reproduciendo sonido: " + ruta);
            e.printStackTrace();
        }
    }
    public void setVolumen(double volumen) {
        volumenGeneral = volumen;

        if (musica != null) {
            musica.setVolume(volumenGeneral);
        }
        if (efectoActual != null) {
            efectoActual.setVolume(volumenGeneral);
        }
    }
    
    public void pararMusica() {
        if (musica != null) {
            musica.stop();
        }
    }
}