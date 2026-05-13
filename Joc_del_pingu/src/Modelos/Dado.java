package Modelos;

import java.util.Random;

// Classe que representa un dado normal del joc.
// Per defecte tira entre 1 i 6. Les subclasses Dado_rapido i Dado_lento
// sobreescriuen els límits per obtenir rangs diferents.
// Hereta d'Objetos; forma part de l'inventari del jugador (màxim 3 dados en total).
public class Dado extends Objetos {

	// Valor màxim que pot sortir en tirar el dado (inclusiu). Per defecte: 6
	private int numeroMaximo;

	// Valor mínim que pot sortir en tirar el dado (inclusiu). Per defecte: 1
	private int numeroMinimo;

	// Generador de nombres aleatoris per a la tirada del dado
	Random rand = new Random();

	// Constructor: inicialitza el dado normal amb rang 1-6
	public Dado() {
		this.numeroMaximo = 6;
		this.numeroMinimo = 1;
	}

	// Assigna el valor màxim del dado
	public void setMaximo(int numeroMaximo) {
		this.numeroMaximo = numeroMaximo;
	}

	// Assigna el valor mínim del dado
	public void setMinimo(int numeroMinimo) {
		this.numeroMinimo = numeroMinimo;
	}

	// Retorna el valor màxim configurat per aquest dado
	public int getMaximo() {
		return numeroMaximo;
	}

	// Retorna el valor mínim configurat per aquest dado
	public int getMinimo() {
		return numeroMinimo;
	}

	// Tira el dado i retorna un nombre aleatori entre el mínim i el màxim (ambdós inclusius)
	public int tirarDado() {
		return rand.nextInt(numeroMaximo - numeroMinimo + 1) + numeroMinimo;
	}
}