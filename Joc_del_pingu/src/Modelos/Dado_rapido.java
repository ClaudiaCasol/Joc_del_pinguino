package Modelos;

public class Dado_rapido extends Dado {
	
	public Dado_rapido(String nombre) {
		super(nombre);

		this.setMaximo(10);
		this.setMinimo(5);
	}	
}
