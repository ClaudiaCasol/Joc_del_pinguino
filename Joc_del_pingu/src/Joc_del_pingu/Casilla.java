package Joc_del_pingu;

public abstract class Casilla {
 private int posicion=0;
 public Casilla(int posicion) {
	 this.posicion = posicion;
 }
 public void setPosicion(int posicion) {
	 this.posicion= posicion;
 }
 
 public int getPosicion() {
	 return posicion;
 }
 
 public abstract void realizarAccion(Partida partida, Jugador jugador);
 
}
