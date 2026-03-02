package Joc_del_pingu;

public class Oso extends Casilla {

    public Oso(int posicion) {
    	super(posicion);
    	setPosicion(posicion);
    }
    @Override
    public void realizarAccion(Partida partida, Jugador jugador) {
        System.out.println("¡Un oso aparece!");
        jugador.moverPosicion(-2);
    }
}