package Modelos;

import java.util.Random;

public class Dado extends Objetos {

	private int numeroMaximo;
	private int numeroMinimo;
	Random rand = new Random();

	public Dado(String nombre) {
		super(nombre);

		this.numeroMaximo = 6;
		this.numeroMinimo = 1;
	}

	public void setMaximo(int numeroMaximo) {
		this.numeroMaximo = numeroMaximo;
	}

	public void setMinimo(int numeroMinimo) {
		this.numeroMinimo = numeroMinimo;
	}

	public int tirarDado() {

		return rand.nextInt((numeroMaximo - numeroMinimo + 1)); //es + 1 per a que no es quedi a 0.
	}
}
