package Joc_del_pingu;

import java.util.ArrayList;

public class GestorPartida {

	private Tablero tablero;
	private GestorTurnos gestorTurnos;
	private ArrayList<Jugador> jugadores;
  
	public GestorPartida(Tablero tablero, ArrayList<Jugador> jugadores) {
	    this.tablero = tablero;
	    this.jugadores = jugadores;
	    this.gestorTurnos = new GestorTurnos(jugadores);
	}

    public void iniciarPartida() {
    	
    }
    public void siguienteTurno() {
        gestorTurnos.siguienteTurno();
    }
    public void activarCasilla(Jugador j) {

    }
    public Jugador getJugadorActual() {
        return gestorTurnos.getJugadorActual();
    }
    public void moverJugador(Jugador j, int pasos) {
        j.moverPosicion(pasos);
    }
    public boolean comprobarGanador(Jugador j) {

        if (j.getPosicion() >= tablero.getTamano() - 1) {
            System.out.println(j.getNombre() + " ha ganado la partida!");
            return true;
        }

        return false;
    }

}