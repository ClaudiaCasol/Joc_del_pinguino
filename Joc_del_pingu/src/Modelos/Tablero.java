package Modelos;

import java.util.ArrayList;
import java.util.Random;

// Representa el taulell de joc amb 50 caselles.
// En crear-se sense paràmetres, genera les caselles aleatòriament:
//   - La primera casella sempre és normal
//   - Les restants es reparteixen aleatòriament entre Oso, Trineo, Agujero,
//     Interrogante, SueloQuebradizo i Casilla normal (la normal és la més freqüent)
// També accepta una llista ja construïda (usat en carregar partides de la BBDD).
public class Tablero {

	// Llista ordenada de les 50 caselles del taulell (índex 0 = primera casella)
	private ArrayList<Casilla> casillas = new ArrayList<>();

	// Mida total del taulell. Per defecte 50 caselles (posicions 0-49)
	private int tamano = 50;

	// Constructor amb llista: reconstrueix el taulell a partir de caselles ja creades.
	// Usat quan es carrega una partida desada a la BBDD.
	public Tablero(ArrayList<Casilla> casillas) {
		this.casillas = casillas;
	}

	// Constructor sense paràmetres: genera el taulell aleatori de 50 caselles.
	// La casella 0 sempre és normal; les 48 restants es generen amb Random.
	// La casella 50 (posició final, guany) sempre és normal.
	public Tablero() {
		Random random = new Random();

		for (int i = 0; i < tamano - 1; i++) {

			if (i == 0) {
				// La primera casella sempre és normal per evitar efectes immediats a l'inici
				casillas.add(new Casilla(i));
			} else {
				// Nombre aleatori entre 0 i 9 per decidir el tipus de casella
				int tipo = random.nextInt(10);

				switch (tipo) {
				case 0:
					// 10% de probabilitat: casella Oso (fa tornar al principi)
					casillas.add(new Oso(i));
					break;
				case 1:
					// 10% de probabilitat: casella Trineo (avança fins al següent trineo)
					casillas.add(new Trineo(i));
					break;
				case 2:
					// 10% de probabilitat: casella Agujero (retrocedeix a l'agujero anterior)
					casillas.add(new Agujero(i));
					break;
				case 3:
					// 10% de probabilitat: casella Interrogante (dóna un objecte aleatori)
					casillas.add(new Interrogante(i));
					break;
				case 4:
					// 10% de probabilitat: casella SueloQuebradizo (penalitza per pes)
					casillas.add(new SueloQuebradizo(i));
					break;
				case 5: case 6: case 7: case 8: case 9:
					// 50% de probabilitat: casella normal (sense efecte)
					casillas.add(new Casilla(i));
				}
			}
		}

		// L'última casella (posició 50) és sempre normal; arribar-hi significa guanyar
		casillas.add(new Casilla(50));
	}

	// Retorna la llista completa de caselles del taulell
	public ArrayList<Casilla> getCasillas() {
		return casillas;
	}

	// Retorna la casella a la posició indicada, o null si la posició és fora de rang.
	// Usat per les caselles especials per consultar el taulell (Agujero, Trineo).
	public Casilla getCasilla(int posicion) {
		if (posicion >= 0 && posicion < casillas.size()) {
			return casillas.get(posicion);
		} else {
			return null; // posició fora dels límits del taulell
		}
	}

	// Retorna el nombre total de caselles del taulell (per defecte 50)
	public int getTamano() {
		return tamano;
	}

	// Permet canviar la mida del taulell (reservat per futures extensions del joc)
	public void setTamano(int tamaño) {
		this.tamano = tamaño;
	}

	// Reservat per futures actualitzacions dinàmiques del taulell durant la partida
	public void actualizarTablero() {
		// De moment no fa res; preparat per possibles extensions
	}
}