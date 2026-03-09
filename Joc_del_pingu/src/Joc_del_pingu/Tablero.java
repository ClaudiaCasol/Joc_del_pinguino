package Joc_del_pingu;

import java.util.ArrayList;
import java.util.Random;

public class Tablero {
	private ArrayList<Casilla> casillas = new ArrayList<>();
	private int tamano = 50;


	public Tablero() {
		Random random = new Random();

		for (int i = 0; i < tamano; i++) {


			if (i == 0) {
				// Primera casilla siempre normal.
				casillas.add(new Casilla(i));

			} 

			else {
				int tipo = random.nextInt(10);

				switch (tipo) {
				case 0:
					casillas.add(new Oso(i));
					break;
				case 1:
					casillas.add(new Trineo(i));
					break;
				case 2:
					casillas.add(new Agujero(i));
					break;
				case 3:
					casillas.add(new Evento(i));
					break;
				case 4:
					casillas.add(new SueloQuebradizo(i));
					break;
				case 5, 6, 7, 8, 9: //Hacemos esto para que la casilla normal sea más común					
					casillas.add(new Casilla(i));
				}
			}
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
	public int getTamano() {
		return tamano;
	}

	// Permite cambiar el tamaño (aunque normalmente no haría falta)
	public void setTamano(int tamaño) {
		this.tamano = tamaño;
	}

	// Método para actualizar el tablero
	public void actualizarTablero() {
		// De momento no hace nada
	}
}
