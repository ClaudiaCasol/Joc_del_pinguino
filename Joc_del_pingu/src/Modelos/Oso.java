package Modelos;

// Casella especial de tipus Oso (ós).
// Quan un jugador hi cau, pot usar un peix de l'inventari per escapar.
// Si no té cap peix, torna a la posició -1 (fora del taulell, inici de la partida).
// Hereta de Casilla i sobreescriu realizarAccion().
public class Oso extends Casilla {

	// Constructor: crea un Oso a la posició indicada del taulell
	public Oso(int posicion) {
		super(posicion);
	}

	// Acció quan el jugador cau en la casella de l'ós.
	// Només afecta jugadors de tipus Pinguino (la Foca no interactua amb l'ós).
	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		// Comprovem que és un Pinguino, ja que la Foca no pot usar objectes
		if (jugador instanceof Pinguino) {
			Pinguino p = (Pinguino) jugador;

			// Intentem usar un peix de l'inventari per subornar l'ós
			if (p.getInventario().usarPez()) {
				// El jugador tenia un peix: l'ha gastat i escapa sense penalització
				System.out.println("El jugador ha usado un pez para evitar al oso.");
			} else {
				// El jugador no té cap peix: torna al principi del taulell
				System.out.println("¡Un oso te ataca! Vuelves al inicio.");
				jugador.setPosicion(-1); // -1 significa fora del taulell (zona de sortida)
			}
		}
	}
}