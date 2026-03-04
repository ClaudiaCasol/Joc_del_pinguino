package Joc_del_pingu;

public class Trineo extends Casilla{

	public Trineo(int posicion) {
	
		super(posicion);
	}
	
	 @Override
	    public void realizarAccion(Partida partida, Jugador jugador) {
	       
		 boolean encontrado = false;
	     
		 for(int i = jugador.getPosicion(); i < 50 && !encontrado; i++) {
			 
			 if(partida.getTablero().getCasilla(i) instanceof Trineo) {
				 
				 jugador.setPosicion(i);
				 
				 encontrado = true;
			 
			 }
			 
		 }
		 
	    }
}
