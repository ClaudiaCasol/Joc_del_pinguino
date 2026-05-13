package Modelos;

// Casella especial de tipus Forat.
// Quan un jugador hi cau, retrocedeix fins a l'agujero anterior del taulell.
// Si no hi ha cap agujero anterior, el jugador es queda on està.
// Hereta de Casilla i sobreescriu realizarAccion().
public class Agujero extends Casilla {

	// Constructor: crea un Agujero a la posició indicada del taulell
	public Agujero(int posicion) {
		super(posicion);
	}

	// Acció quan el jugador cau en aquest forat:
	// busca cap enrere el primer Agujero anterior i hi mou el jugador.
	// Si no existeix cap agujero anterior, no el mou.
	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		// Posició actual del jugador, punt de partida de la cerca cap enrere
		int posicionActual = jugador.getPosicion();

		// Recorrem el taulell cap enrere des de la casella anterior a l'actual
		for (int i = posicionActual - 1; i >= 0; i--) {

			// Comprovem si la casella en aquesta posició és també un Agujero
			if (partida.getTablero().getCasilla(i) instanceof Agujero) {

				// Movem el jugador fins a l'agujero anterior trobat
				jugador.setPosicion(i);
				System.out.println("Has caído en un agujero. Retrocedes al agujero anterior.");
				return;
			}
		}

		// No s'ha trobat cap agujero anterior; el jugador no es mou
		System.out.println("No hay agujero anterior.");
	}
}