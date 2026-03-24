package Controladores;

import Modelos.Casilla;
import Modelos.Jugador;
import Modelos.Partida;
import Modelos.Pinguino;

public class GestorTablero {

	public void ejecutarCasilla(Partida partida, Pinguino p, Casilla c) {
		
		if(c == null) { //pongo null por si sale un número en el tablero que está fuera del tablero.
			return;
		}
		
		System.out.println("El jugador " + p.getNombre() + " ha caído en la casilla " + c.getPosicion());
		
		c.realizarAccion(partida, p); //ejecuta lo que hace la casilla.
	}
	
	public void comprobarFinTurno(Partida partida) {
		
		Jugador jugador = partida.getJugadorActual(); //devuelve el turno del jugador.
		
		if(jugador.getPosicion() >= partida.getTablero().getTamano() - 1) { //comprobamos si el jugador ha llegado a la última casilla.
			System.out.println("Ha ganado " + jugador.getNombre());
			
			partida.setGanador(jugador); //guarda el jugador ganador.
			partida.finalizarPartida();
		}
	}
	
}
