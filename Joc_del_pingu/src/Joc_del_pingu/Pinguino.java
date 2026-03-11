package Joc_del_pingu;

public class Pinguino extends Jugador {

	 public Pinguino(int posicion, String nombre, String color, Inventario inv) {
	        super(posicion, nombre, color);
	    }

	    public void gestionarBatalla(Pinguino p2) {
	        // implementar cuando toque la guerra
	    }

	    public void anadirItem(Objetos item) {

	        if (item instanceof BolaNieve) {
	            getInventario().agregarBolaNieve((BolaNieve) item);
	        } 
	        else if (item instanceof Dado) {
	            getInventario().agregarDado((Dado) item);
	        } 
	        else if (item instanceof Pez) {
	            getInventario().agregarPez((Pez) item);
	        }
	    }

	    public void eliminarItem(Objetos item) {

	        if (item instanceof BolaNieve) {
	            getInventario().eliminarBolaNieve((BolaNieve) item);
	        } 
	        else if (item instanceof Dado) {
	            getInventario().eliminarDado((Dado) item);
	        } 
	        else if (item instanceof Pez) {
	            getInventario().eliminarPez((Pez) item);
	        }
	    }

	    @Override
	    public String toString() {
	        return "Pinguino " + getNombre() +
	               " (" + getColor() + ") - Posición: " +
	               getPosicion();
	    }
}