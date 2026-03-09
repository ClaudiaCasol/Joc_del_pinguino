package Joc_del_pingu;

public class Trineo extends Casilla{

	public Trineo(int posicion) {
	
		super(posicion);
	}
	
	 @Override
	    public void realizarAccion(Partida partida, Jugador jugador) {
	       
		   int posicionActual = jugador.getPosicion();

        for (int i = posicionActual + 1; i < partida.getTablero().getTamano(); i++) {

            if (partida.getTablero().getCasilla(i) instanceof Trineo) {

                jugador.setPosicion(i);
                System.out.println("¡Usas un trineo y avanzas al siguiente!");
                return;
            }
        }

        System.out.println("No hay más trineos adelante.");
    }
}
