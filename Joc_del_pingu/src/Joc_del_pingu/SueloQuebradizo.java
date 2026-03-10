package Joc_del_pingu;

public class SueloQuebradizo extends Casilla {

	public SueloQuebradizo(int posicion) {
	super(posicion);
}	
	 @Override
	    public void realizarAccion(Partida partida, Jugador jugador) {
	        System.out.println("¡Un suelo Quebradizo aparece!");
	        int total = jugador.getInventario().getPez().size()
	                + jugador.getInventario().getBolaNieve().size()
	                + jugador.getInventario().getDado().size();
	        if ( total>5) {
	        	System.out.println("El hielo se rompe por el peso. Vuelves al inicio.");
	        	jugador.setPosicion(0);
	        }
	        else if (total>0&& total<=5) {
	        	System.out.println("El hielo se agrieta. Pierdes un turno.");
	        	jugador.setTurnosPerdidos(1);
	        }
	        else System.out.println("Pasas sin ninguna penalización");
	        
	    }
}
