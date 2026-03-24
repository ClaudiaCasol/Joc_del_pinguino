package Modelos;

public class Oso extends Casilla {

    public Oso(int posicion) {
    	super(posicion);
    
    }
    @Override
    public void realizarAccion(Partida partida, Jugador jugador) {
    	if(jugador instanceof Pinguino) {
    		Pinguino p= (Pinguino) jugador;
    	 if (p.getInventario().usarPez()) {
             System.out.println("El jugador ha usado un pez para evitar al oso.");
         } else {
             System.out.println("¡Un oso te ataca! Vuelves al inicio.");
             jugador.setPosicion(-1);
         }
     }
    }
}