package Modelos;

// Classe abstracta base per a tots els objectes que poden estar a l'inventari d'un jugador.
// Les subclasses concretes són: Dado, BolaNieve i Pez.
// No conté lògica pròpia; serveix com a tipus comú per gestionar l'inventari de forma polimòrfica.
public abstract class Objetos {

	// Nom descriptiu de l'objecte (no s'usa activament però permet identificar-lo)
	private String nombre;

	// Constructor buit: les subclasses no necessiten cap paràmetre d'inicialització
	public Objetos() {
	}
}