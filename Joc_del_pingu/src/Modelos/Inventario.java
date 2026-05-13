package Modelos;

import java.util.ArrayList;
import java.util.Random;

// Gestiona tots els objectes que porta un jugador durant la partida.
// Cada jugador (Pinguino o Foca) té el seu propi Inventario independent.
// Límits màxims: 3 dados en total | 6 boles de neu | 2 peixos.
public class Inventario {

	// Generador de nombres aleatoris, usat per perderObjetoAleatorio()
	Random rand = new Random();

	// Llista de dados de l'inventari (normal, ràpid o lent). Màxim 3 en total
	private ArrayList<Dado> dado = new ArrayList<Dado>();

	// Llista de boles de neu de l'inventari. Màxim 6
	private ArrayList<BolaNieve> bolaNieve = new ArrayList<BolaNieve>();

	// Llista de peixos de l'inventari. Màxim 2
	private ArrayList<Pez> pez = new ArrayList<Pez>();

	// Constructor complet: inicialitza l'inventari amb llistes ja existents
	public Inventario(ArrayList<Dado> dado, ArrayList<BolaNieve> bolaNieve, ArrayList<Pez> pez) {
		this.dado = dado;
		this.bolaNieve = bolaNieve;
		this.pez = pez;
	}

	// Constructor buit: crea un inventari completament buit
	public Inventario() {
	}

	// Retorna la llista de dados actuals de l'inventari
	public ArrayList<Dado> getDado() {
		return dado;
	}

	// Substitueix tota la llista de dados per la nova llista indicada
	public void setDado(ArrayList<Dado> dado) {
		this.dado = dado;
	}

	// Retorna la llista de boles de neu actuals de l'inventari
	public ArrayList<BolaNieve> getBolaNieve() {
		return bolaNieve;
	}

	// Substitueix tota la llista de boles de neu per la nova llista indicada
	public void setBolaNieve(ArrayList<BolaNieve> bolaNieve) {
		this.bolaNieve = bolaNieve;
	}

	// Retorna la llista de peixos actuals de l'inventari
	public ArrayList<Pez> getPez() {
		return pez;
	}

	// Substitueix tota la llista de peixos per la nova llista indicada
	public void setPez(ArrayList<Pez> pez) {
		this.pez = pez;
	}

	// Afegeix un dado a l'inventari si no s'ha superat el màxim de 3 dados
	public void agregarDado(Dado item) {
		if (this.dado.size() < 3) {
			this.dado.add(item);
		} else {
			System.out.println("No se puede añadir otro dado, tienes los dados máximos.");
		}
	}

	// Afegeix una bola de neu a l'inventari si no s'ha superat el màxim de 6
	public void agregarBolaNieve(BolaNieve item) {
		if (this.bolaNieve.size() < 6) {
			this.bolaNieve.add(item);
		} else {
			System.out.println("No se pueden añadir más bolas de nieve, tienes las bolas de nieve máximas.");
		}
	}

	// Afegeix un peix a l'inventari si no s'ha superat el màxim de 2
	public void agregarPez(Pez item) {
		if (this.pez.size() < 2) {
			this.pez.add(item);
		} else {
			System.out.println("No se pueden añadir más peces, tienes los peces máximos.");
		}
	}

	// Elimina el dado especificat de l'inventari
	public void eliminarDado(Dado item) {
		this.dado.remove(item);
	}

	// Elimina la bola de neu especificada si l'inventari en té alguna
	public void eliminarBolaNieve(BolaNieve item) {
		if (this.bolaNieve.size() > 0 && this.bolaNieve.size() <= 6) {
			this.bolaNieve.remove(item);
		}
	}

	// Elimina el peix especificat si l'inventari en té algun
	public void eliminarPez(Pez item) {
		if (this.pez.size() > 0 && this.pez.size() <= 2) {
			this.pez.remove(item);
		}
	}

	// Elimina un objecte aleatori de l'inventari (un dado extra, una bola o un peix).
	// S'activa en certes caselles de penalització.
	// Nota: si la llista d'aquell tipus és buida, pot llançar IndexOutOfBoundsException.
	public void perderObjetoAleatorio() {
		// Tria aleatòriament quin tipus d'objecte es perd (0=dado, 1=bola, 2=peix)
		int opcion = rand.nextInt(3);

		switch (opcion) {
		case 0: {
			// Perd un dado extra (posicions 1 o 2; el 0 és el dado base que sempre es guarda)
			Dado d = dado.get(rand.nextInt(2) + 1);
			this.eliminarDado(d);
			break;
		}
		case 1: {
			// Perd la primera bola de neu de la llista
			BolaNieve b = bolaNieve.get(0);
			this.eliminarBolaNieve(b);
			break;
		}
		case 2: {
			// Perd el primer peix de la llista
			Pez p = pez.get(0);
			this.eliminarPez(p);
			break;
		}
		}
	}

	// Elimina la meitat de cada tipus d'objecte de l'inventari.
	// S'activa quan la Foca ataca un pingui sense peix.
	public void perderMitadInventario() {
		// Calculem quants elements de cada tipus s'han d'eliminar (divisió entera)
		int mitadDado = dado.size() / 2;
		int mitadBolaNieve = bolaNieve.size() / 2;
		int mitadPez = pez.size() / 2;

		// Eliminem la meitat dels dados (sempre des de la posició 0)
		for (int i = 0; i < mitadDado; i++) {
			if (!dado.isEmpty()) {
				dado.remove(0);
			}
		}

		// Eliminem la meitat de les boles de neu
		for (int i = 0; i < mitadBolaNieve; i++) {
			if (!bolaNieve.isEmpty()) {
				bolaNieve.remove(0);
			}
		}

		// Eliminem la meitat dels peixos
		for (int i = 0; i < mitadPez; i++) {
			if (!pez.isEmpty()) {
				pez.remove(0);
			}
		}
	}

	// Imprimeix per consola el resum complet de l'inventari del jugador
	public void totalObjetos() {
		// Comptem quants dados ràpids i lents té el jugador per separat
		int dadoRapido = 0;
		int dadoLento = 0;

		for (Dado d : dado) {
			if (d instanceof Dado_rapido) {
				dadoRapido++;
			} else if (d instanceof Dado_lento) {
				dadoLento++;
			}
		}

		System.out.println("INVENTARIO:");
		System.out.println("Tienes 1 dado normal.");
		System.out.println("Tienes " + dadoRapido + " rápido/s");
		System.out.println("Tienes " + dadoLento + " lento/s");
		System.out.println("Tienes" + bolaNieve.size() + " bolas de nieve.");
		System.out.println("Tienes " + pez.size() + " pez/es.");
	}

	// Comprova si el jugador té almenys un peix a l'inventari
	// Retorna true si n'hi ha, false si la llista és buida
	public boolean tienePez() {
		if (pez.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	// Intenta usar un peix: si n'hi ha, l'elimina i retorna true.
	// Si no n'hi ha cap, retorna false sense fer res.
	public boolean usarPez() {
		if (!pez.isEmpty()) {
			pez.remove(0); // eliminem el primer peix disponible
			return true;
		}
		return false;
	}
}