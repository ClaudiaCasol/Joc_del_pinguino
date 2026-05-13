package Modelos;

// Casella especial de tipus Trineo.
// Quan un jugador hi cau, avança fins al següent trineo del taulell.
// Si no hi ha cap trineo per davant, el jugador es queda on està.
// Hereta de Casilla i sobreescriu realizarAccion().
public class Trineo extends Casilla {

	// Constructor: crea un Trineo a la posició indicada del taulell
	public Trineo(int posicion) {
		super(posicion);
	}

	// Acció quan el jugador cau en aquest trineo:
	// busca cap endavant el primer Trineo següent i hi mou el jugador.
	// Si no n'hi ha cap més, no fa res.
	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		// Posició actual del jugador, punt de partida de la cerca cap endavant
		int posicionActual = jugador.getPosicion();

		// Recorrem el taulell cap endavant des de la casella posterior a l'actual
		for (int i = posicionActual + 1; i < partida.getTablero().getTamano(); i++) {

			// Comprovem si la casella en aquesta posició és un altre Trineo
			if (partida.getTablero().getCasilla(i) instanceof Trineo) {

				// Movem el jugador fins al següent trineo trobat
				jugador.setPosicion(i);
				System.out.println("¡Usas un trineo y avanzas al siguiente!");
				return;
			}
		}

		// No s'ha trobat cap trineo per davant; el jugador no avança
		System.out.println("No hay más trineos adelante.");
	}
}