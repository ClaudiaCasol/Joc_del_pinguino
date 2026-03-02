package JuegoPinguino;

import java.util.ArrayList;
import java.util.Random;

public class Tablero {
	private ArrayList<Casilla> casillas = new ArrayList<>();
	private int tamano = 40;


	public Tablero() {
		Random random = new Random();

		for (int i = 0; i < tamano; i++) {

			Casilla nuevaCasilla;

			if (i == 0) {
				// Primera casilla siempre normal (por ejemplo SueloQuebradizo)
				nuevaCasilla = new SueloQuebradizo(i);
			} else {
				int tipo = random.nextInt(5);

				switch (tipo) {
				case 0:
					nuevaCasilla = new Oso(i);
					break;
				case 1:
					nuevaCasilla = new Trineo(i);
					break;
				case 2:
					nuevaCasilla = new Agujero(i);
					break;
				case 3:
					nuevaCasilla = new Evento(i);
					break;
				default:
					nuevaCasilla = new SueloQuebradizo(i);
					break;
				}
			}

			casillas.add(nuevaCasilla);
		}
	}

// DEVUELVE la lista completa de casillas
	public ArrayList<Casilla> getCasillas() {
		return casillas;
	}

// DEVUELVE una casilla concreta según posición
	public Casilla getCasilla(int posicion) {

		if (posicion >= 0 && posicion < casillas.size()) {
			return casillas.get(posicion);
		} else {
			return null; // si la posición no existe
		}
	}

// DEVUELVE el tamaño del tablero
	public int getTamaño() {
		return tamano;
	}

// Permite cambiar el tamaño (aunque normalmente no haría falta)
	public void setTamaño(int tamaño) {
		this.tamano = tamaño;
	}

// Método para actualizar el tablero
	public void actualizarTablero() {
		// De momento no hace nada
	}
}
