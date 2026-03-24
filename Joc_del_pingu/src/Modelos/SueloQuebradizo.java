package Modelos;

public class SueloQuebradizo extends Casilla {

	public SueloQuebradizo(int posicion) {
	super(posicion);
}	
	 @Override
	    public void realizarAccion(Partida partida, Jugador jugador) {
	        System.out.println("¡Un suelo Quebradizo aparece!");
	        if (jugador instanceof Pinguino) {
	            Pinguino p = (Pinguino) jugador;

	            int total = p.getInventario().getPez().size()
	                    + p.getInventario().getBolaNieve().size()
	                    + p.getInventario().getDado().size();
	        
	        if (total>5) {
	        	System.out.println("El hielo se rompe por el peso. Vuelves al inicio.");
	        	jugador.setPosicion(-1);
	        }
	        else if (total>0&& total<=5) {
	        	System.out.println("El hielo se agrieta. Pierdes un turno.");
	        	jugador.setTurnosPerdidos(1);
	        }
	        else System.out.println("Pasas sin ninguna penalización");
	        
	    }
	 }
}
