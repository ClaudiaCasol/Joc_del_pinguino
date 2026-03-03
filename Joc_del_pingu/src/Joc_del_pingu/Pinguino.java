package Joc_del_pingu;

public class Pinguino extends Jugador {

	private Inventario inv;

	public Pinguino(int posicion, String nombre, String color, Inventario inv) {
		super(posicion, nombre, color);
		this.inv = inv;
	}

	public Inventario getInv() {
		return inv;
	}

	public void setInv(Inventario inv) {
		this.inv = inv;
	}

	public void gestionarBatalla(Pinguino p2) {
		//implementar cuando toque la "guerra"
	}

	public void anadirItem(Objetos item) {
		if (item instanceof BolaNieve) {
			inv.agregarBolaNieve((BolaNieve) item);
		}

		if(item instanceof Dado) {
			inv.agregarDado((Dado) item);
		}

		if(item instanceof Pez) {
			inv.agregarPez((Pez) item);
		}
	}

	public void eliminarItem(Objetos item) {
		if (item instanceof BolaNieve) {
			inv.eliminarBolaNieve((BolaNieve) item);
		}

		if(item instanceof Dado) {
			inv.eliminarDado((Dado) item);
		}

		if(item instanceof Pez) {
			inv.eliminarPez((Pez) item);
		}
	}

	@Override
	public String toString() {
		return "Pinguino " + getNombre() +
				" (" + getColor() + ") - Posición: " +
				getPosicion();
	}
}