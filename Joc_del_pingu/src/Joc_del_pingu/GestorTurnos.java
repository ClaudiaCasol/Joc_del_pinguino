package Joc_del_pingu;

import java.util.ArrayList;

public class GestorTurnos {
private boolean validacion=false;
private int turnoActual=0;
private int jugadorActual=0;
private ArrayList<Jugador> jugadores;


public GestorTurnos(ArrayList<Jugador> jugadores) {
    this.jugadores = jugadores;
}
public void validarTurno() {
	 if (turnoActual > 0 && jugadorActual >= 0 && jugadorActual < jugadores.size()) {
	        validacion = true;
	    } else {
	        validacion = false;
	    }
	
}
public void siguienteTurno() {
	jugadorActual++;
	if (jugadorActual>=jugadores.size()) {
		jugadorActual=0;
		turnoActual++;
	}
}
public boolean isValidacion() {
	return validacion;
}
public int getTurnoActual() {
	return turnoActual;
}
public Jugador getJugadorActual() {
	return jugadores.get(jugadorActual);
}


}
