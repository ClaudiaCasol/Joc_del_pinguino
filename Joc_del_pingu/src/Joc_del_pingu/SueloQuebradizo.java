package Joc_del_pingu;

public class SueloQuebradizo extends Casilla {

	public SueloQuebradizo(int posicion) {
	super(posicion);
	setPosicion(posicion);
}	
	 @Override
	    public void realizarAccion(Partida partida, Jugador jugador) {
	        System.out.println("¡Un suelo Quebradizo aparece!");
	        jugador.moverPosicion(-6);
	    }
}
