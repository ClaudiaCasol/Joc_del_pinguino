package Modelos;

// Casella especial de tipus Terra Quebradís.
// L'efecte depèn del pes de l'inventari del jugador (nombre total d'objectes):
//   - Més de 5 objectes -> torna a l'inici (-1)
//   - Entre 1 i 5 objectes -> perd un torn
//   - Cap objecte -> passa sense penalització
// Hereta de Casilla i sobreescriu realizarAccion().
public class SueloQuebradizo extends Casilla {

	// Constructor: crea un SueloQuebradizo a la posició indicada del taulell
	public SueloQuebradizo(int posicion) {
		super(posicion);
	}

	// Acció quan el jugador cau en el terra quebradís.
	// Compta el total d'objectes de l'inventari i aplica la penalització corresponent.
	// Només afecta jugadors de tipus Pinguino.
	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		System.out.println("¡Un suelo Quebradizo aparece!");

		// Només els pinguins tenen inventari; la Foca no es veu afectada
		if (jugador instanceof Pinguino) {
			Pinguino p = (Pinguino) jugador;

			// Calculem el total d'objectes sumant peixos + boles de neu + dados
			int total = p.getInventario().getPez().size()
					+ p.getInventario().getBolaNieve().size()
					+ p.getInventario().getDado().size();

			if (total > 5) {
				// Massa pes: el gel es trenca i el jugador torna al principi
				System.out.println("El hielo se rompe por el peso. Vuelves al inicio.");
				jugador.setPosicion(-1); // -1 = fora del taulell, zona de sortida
			} else if (total > 0 && total <= 5) {
				// Pes moderat: el gel s'esquerda però el jugador sobreviu perdent un torn
				System.out.println("El hielo se agrieta. Pierdes un turno.");
				jugador.setTurnosPerdidos(1); // el jugador saltarà el seu proper torn
			} else {
				// Sense objectes: el jugador pesa prou poc per passar sense problemes
				System.out.println("Pasas sin ninguna penalización");
			}
		}
	}
}