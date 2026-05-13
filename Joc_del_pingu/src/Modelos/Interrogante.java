package Modelos;

import java.util.Random;

// Casella especial de tipus Interrogant (?).
// Quan un jugador hi cau, rep un objecte aleatori de l'inventari.
// Probabilitats: dado ràpid 15% | dado lent 45% | peix 20% | bola de neu 20%.
// Hereta de Casilla i sobreescriu realizarAccion().
public class Interrogante extends Casilla {

	// Generador de nombres aleatoris per determinar quin objecte rep el jugador
	private Random rand = new Random();

	// Constructor: crea un Interrogante a la posició indicada del taulell
	public Interrogante(int posicion) {
		super(posicion);
	}

	// Acció quan el jugador cau en la casella d'interrogant.
	// Genera un nombre aleatori entre 0 i 99 i assigna un objecte segons el rang:
	//   0-14  (15%) -> dado ràpid
	//  15-59  (45%) -> dado lent
	//  60-79  (20%) -> peix
	//  80-99  (20%) -> bola de neu
	// Només afecta jugadors de tipus Pinguino.
	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		// La Foca CPU no pot rebre objectes de l'interrogant
		if (!(jugador instanceof Pinguino)) {
			return;
		}

		Pinguino p = (Pinguino) jugador;

		// Nombre aleatori entre 0 i 99 per determinar el premi
		int opcion = rand.nextInt(100);

		if (opcion < 15) {
			// 15% de probabilitat: dado ràpid (tira entre 5 i 10)
			p.getInventario().agregarDado(new Dado_rapido());
			System.out.println("Has conseguido un dado rápido.");

		} else if (opcion < 60) {
			// 45% de probabilitat: dado lent (tira entre 1 i 3)
			p.getInventario().agregarDado(new Dado_lento());
			System.out.println("Has conseguido un dado lento.");

		} else if (opcion < 80) {
			// 20% de probabilitat: peix (serveix per escapar de l'ós o subornar la foca)
			p.getInventario().agregarPez(new Pez());
			System.out.println("Has conseguido un pez.");

		} else {
			// 20% de probabilitat: bola de neu (s'usa en la guerra entre pinguins)
			p.getInventario().agregarBolaNieve(new BolaNieve());
			System.out.println("Has conseguido una bola de nieve.");
		}
	}
}