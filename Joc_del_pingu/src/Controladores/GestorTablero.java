package Controladores;

import Modelos.Casilla;
import Modelos.Jugador;
import Modelos.Partida;
import Modelos.Pinguino;

/**
 * Classe controladora que gestiona les accions relacionades amb el tauler de joc.
 * S'encarrega d'executar l'efecte de les caselles i comprovar si la partida ha acabat.
 */
public class GestorTablero {

	/**
	 * Executa l'acció de la casella on ha caigut un pingüí.
	 * Si la casella és null (posició fora del tauler), no fa res.
	 *
	 * @param partida Partida en curs
	 * @param p       Pingüí que ha caigut a la casella
	 * @param c       Casella on ha caigut el pingüí
	 */
	public void ejecutarCasilla(Partida partida, Pinguino p, Casilla c) {
		
		if(c == null) { //pongo null por si sale un número en el tablero que está fuera del tablero.
			return;
		}
		
		System.out.println("El jugador " + p.getNombre() + " ha caído en la casilla " + c.getPosicion());
		
		c.realizarAccion(partida, p); //ejecuta lo que hace la casilla.
	}
	
	/**
	 * Comprova si el jugador actual ha arribat a l'última casella del tauler.
	 * Si és així, el declara guanyador i finalitza la partida.
	 *
	 * @param partida Partida en curs de la qual es comprova el final de torn
	 */
	public void comprobarFinTurno(Partida partida) {
		
		Jugador jugador = partida.getJugadorActual(); //devuelve el turno del jugador.
		
		if(jugador.getPosicion() >= partida.getTablero().getTamano() - 1) { //comprobamos si el jugador ha llegado a la última casilla.
			System.out.println("Ha ganado " + jugador.getNombre());
			
			partida.setGanador(jugador); //guarda el jugador ganador.
			partida.finalizarPartida();
		}
	}
	
}