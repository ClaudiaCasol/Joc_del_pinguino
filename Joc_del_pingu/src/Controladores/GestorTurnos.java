package Controladores;

import java.util.ArrayList;

import Modelos.Jugador;

/**
 * Classe controladora que gestiona l'ordre dels torns durant la partida.
 * Porta el compte del torn actual i del jugador que li toca jugar.
 */
public class GestorTurnos {

	/** Indica si el torn actual és vàlid */
	private boolean validacion = false;

	/** Número del torn global (s'incrementa quan tots els jugadors han jugat) */
	private int turnoActual = 0;

	/** Índex del jugador que té el torn actual dins la llista de jugadors */
	private int jugadorActual = 0;

	/** Llista de jugadors que participen a la partida */
	private ArrayList<Jugador> jugadores;

	/**
	 * Constructor que inicialitza el gestor de torns amb la llista de jugadors.
	 *
	 * @param jugadores Llista de jugadors de la partida
	 */
	public GestorTurnos(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	/**
	 * Valida si el torn actual és correcte.
	 * El torn és vàlid si turnoActual >= 0 i jugadorActual és un índex vàlid.
	 */
	public void validarTurno() {
		if (turnoActual >= 0 && jugadorActual >= 0 && jugadorActual < jugadores.size()) {
			validacion = true;
		} else {
			validacion = false;
		}
	}

	/**
	 * Avança al següent jugador. Si ja han jugat tots, reinicia l'índex
	 * i incrementa el comptador de torns globals.
	 */
	public void siguienteTurno() {
		jugadorActual++;
		if (jugadorActual >= jugadores.size()) {
			jugadorActual = 0;
			turnoActual++;
		}
	}

	/**
	 * Retorna si el torn actual és vàlid.
	 *
	 * @return true si el torn és vàlid, false en cas contrari
	 */
	public boolean isValidacion() {
		return validacion;
	}

	/**
	 * Retorna el número del torn global actual.
	 *
	 * @return Número de torn actual
	 */
	public int getTurnoActual() {
		return turnoActual;
	}

	/**
	 * Retorna el jugador que té el torn en aquest moment.
	 *
	 * @return Objecte Jugador amb el torn actual
	 */
	public Jugador getJugadorActual() {
		return jugadores.get(jugadorActual);
	}

}