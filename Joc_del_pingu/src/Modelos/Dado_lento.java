package Modelos;

// Dado especial de tipus Lent.
// Tira entre 1 i 3, útil per avançar amb precisió i evitar caselles perilloses.
// És consumible: s'elimina de l'inventari després d'usar-se una vegada.
// Hereta de Dado i sobreescriu els límits al constructor.
public class Dado_lento extends Dado {

	// Constructor: configura el dado lent amb rang 1-3
	public Dado_lento() {
		this.setMaximo(3); // màxim: 3 caselles per tirada
		this.setMinimo(1); // mínim: 1 casella garantida
	}
}