package Modelos;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Classe que gestiona la reproducció d'àudio del joc.
 * Permet reproduir música de fons en bucle i efectes de so puntuals,
 * a més de controlar el volum general de l'aplicació.
 */
public class AudioManager {

	/** Reproductor de l'efecte de so actual */
	private MediaPlayer efectoActual;

	/** Reproductor de la música de fons */
    private MediaPlayer musica;

    /** Volum general de l'àudio (entre 0.0 i 1.0) */
    private double volumenGeneral = 1.0;

    /**
     * Reprodueix música de fons en bucle infinit des d'una ruta de fitxer.
     * Si ja hi ha música en reproducció, no fa res.
     *
     * @param ruta Ruta relativa del fitxer d'àudio a reproduir
     */
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

    /**
     * Reprodueix un efecte de so puntual des d'una ruta de fitxer.
     * Si hi havia un efecte anterior en reproducció, l'atura primer.
     *
     * @param ruta Ruta relativa del fitxer d'àudio de l'efecte a reproduir
     */
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

    /**
     * Estableix el volum general de l'àudio i l'aplica tant a la música
     * com a l'efecte de so actual si n'hi ha.
     *
     * @param volumen Valor del volum entre 0.0 (silenci) i 1.0 (màxim)
     */
    public void setVolumen(double volumen) {
        volumenGeneral = volumen;

        if (musica != null) {
            musica.setVolume(volumenGeneral);
        }
        if (efectoActual != null) {
            efectoActual.setVolume(volumenGeneral);
        }
    }

    /**
     * Atura la reproducció de la música de fons si n'hi ha una en curs.
     */
    public void pararMusica() {
        if (musica != null) {
            musica.stop();
        }
    }
}