package Modelos;

// Classe base que representa una casella normal del taulell.
// Totes les caselles especials (Oso, Trineo, Agujero, etc.) hereten d'aquesta classe.
// Quan un jugador cau en una casella normal no passa res (realizarAccion és buit).
public class Casilla {

	// Posició d'aquesta casella dins el taulell (0 = primera casella, 49 = última)
	private int posicion = 0;

	// Constructor: crea una casella a la posició indicada
	public Casilla(int posicion) {
		this.posicion = posicion;
	}

	// Assigna una nova posició a aquesta casella
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	// Retorna la posició d'aquesta casella al taulell
	public int getPosicion() {
		return posicion;
	}

	// Executa l'acció de la casella quan un jugador hi cau.
	// En la casella normal no fa res; les subclasses sobreescriuen aquest mètode
	// per definir efectes especials (retrocedir, avançar, perdre torn, etc.)
	public void realizarAccion(Partida partida, Jugador jugador) {
		// Casella normal: sense efecte
	}

}