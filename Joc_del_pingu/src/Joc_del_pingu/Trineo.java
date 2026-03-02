package Joc_del_pingu;

public class Trineo extends Casilla{

	public Trineo(int posicion) {
	setPosicion(posicion);
}	
	 @Override
	    public void realizarAccion(Partida partida, Jugador jugador) {
	        System.out.println("¡Un trineo aparece!");
	        jugador.moverPosicion(3);
	    }
}
