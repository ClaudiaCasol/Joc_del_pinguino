package Joc_del_pingu;

import java.util.ArrayList;

public class GestorPartida {

	private Tablero tablero;
	private GestorTurnos gestorTurnos;
	private ArrayList<Jugador> jugadores;
  
	public GestorPartida(Tablero tablero, ArrayList<Jugador> jugadores) {
	    this.tablero = tablero;
	    this.jugadores = jugadores;
	    this.gestorTurnos = new GestorTurnos(jugadores);
	}
    public void nuevaPartida(ArrayList<Jugador> jugadores, Tablero tablero){
        partida = new Partida (jugadores);
        partida.setTablero(tablero);
        partida.iniciarPartida();
    }
    public void iniciarPartida() {
    	
    }
    public void jugarTunro() {
    	
    }
    public void activarCasilla(Jugador j) {
    	
    }
    public boolean comprobarGanador(Jugador j) {
    	return true;
    }

    public int tirarDado(Jugador j, Dado dadoOpcional){
        if(dadoOpcional != null) {
                        
        }
    }
        
}