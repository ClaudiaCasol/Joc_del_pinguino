package JuegoPinguino;

public class GestorTurnos {
private boolean validacion=false;
private int turnoActual=0;
private int jugadorActual=0;

public GestorTurnos() {
	validacion=false;
	turnoActual=1;
	jugadorActual=1;
}
public void validarTurno() {
	if (turnoActual > 0 && jugadorActual > 0 && jugadorActual <= 4) {
	    validacion = true;
	} else {
	    validacion = false;
	}
	
}
public void siguienteTurno() {
	jugadorActual++;
	if (jugadorActual>4) {
		jugadorActual=1;
		turnoActual++;
	}
}
public boolean isValidacion() {
	return validacion;
}
public int getTurnoActual() {
	return turnoActual;
}
public int getJugadorActual() {
	return jugadorActual;
}


}
