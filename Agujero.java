package JuegoPinguino;

public class Agujero extends Casilla {

	public Agujero(int posicion) {
	super(posicion);
	setPosicion(posicion);
}	
	 @Override
	    public void realizarAccion(Partida partida, Jugador jugador) {
	        System.out.println("¡Un agujero aparece!");
	        jugador.moverPosicion(-10);
	    }
}
