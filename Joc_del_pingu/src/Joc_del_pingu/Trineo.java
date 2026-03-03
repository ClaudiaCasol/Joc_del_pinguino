package Joc_del_pingu;

public class Trineo extends Casilla{

	public Trineo(int posicion) {
	
		super(posicion);
	}
	
	 @Override
	    public void realizarAccion(Partida partida, Jugador jugador) {
	       
		 System.out.println("¡Un trineo aparece!");
	     
		 for(int i = jugador.getPosicion(); i < 50; i++) {
			 
		 }
		 
	    }
}
