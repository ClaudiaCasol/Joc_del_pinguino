package Modelos;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {

    private MediaPlayer musica;

    public void reproducirMusica(String ruta) {
        if (musica != null) {
            musica.stop();
        }

        try {
            Media media = new Media(
                new java.io.File("src" + ruta).toURI().toString()
            );

            musica = new MediaPlayer(media);
            musica.setCycleCount(MediaPlayer.INDEFINITE);
            musica.setVolume(0.3);
            musica.play();

        } catch (Exception e) {
            System.out.println("Error cargando música: " + ruta);
            e.printStackTrace();
        }
    }

    public void reproducirEfecto(String ruta) {
        try {
            Media media = new Media(
                new java.io.File("src" + ruta).toURI().toString()
            );

            MediaPlayer efecto = new MediaPlayer(media);
            efecto.setVolume(1.0);
            efecto.play();

        } catch (Exception e) {
            System.out.println("Error reproduciendo sonido: " + ruta);
            e.printStackTrace();
        }
    }

    public void pararMusica() {
        if (musica != null) {
            musica.stop();
        }
    }
}