package Modelos;

public class Agujero extends Casilla {

	public Agujero(int posicion) {
	super(posicion);
	
}	
	 @Override
	    public void realizarAccion(Partida partida, Jugador jugador) {
	    int posicionActual = jugador.getPosicion();

        for (int i = posicionActual - 1; i >= 0; i--) {

            if (partida.getTablero().getCasilla(i) instanceof Agujero) {

                jugador.setPosicion(i);
                System.out.println("Has caído en un agujero. Retrocedes al agujero anterior.");
                return;
            }
        }

        System.out.println("No hay agujero anterior.");
    }
}
