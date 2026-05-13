package Modelos;

// Dado especial de tipus Ràpid.
// Tira entre 5 i 10, garantint avançar moltes caselles.
// És consumible: s'elimina de l'inventari després d'usar-se una vegada.
// Hereta de Dado i sobreescriu els límits al constructor.
public class Dado_rapido extends Dado {

	// Constructor: configura el dado ràpid amb rang 5-10
	public Dado_rapido() {
		this.setMaximo(10); // màxim: 10 caselles d'una tirada
		this.setMinimo(5);  // mínim: 5 caselles garantides
	}
}