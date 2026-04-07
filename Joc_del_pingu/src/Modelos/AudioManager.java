package Modelos;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {

    private MediaPlayer musica;

    public void reproducirMusica(String ruta) {
        if (musica != null) {
            musica.stop();
        }

        Media media = new Media(ruta);
        musica = new MediaPlayer(media);
        musica.setCycleCount(MediaPlayer.INDEFINITE);
        musica.setVolume(0.3);
        musica.play();
    }

    public void reproducirEfecto(String ruta) {
        try {
            Media media = new Media(ruta);
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