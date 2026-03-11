package Joc_del_pingu;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorPartida {

	private Tablero tablero;
	private GestorTurnos gestorTurnos;
	private ArrayList<Jugador> jugadores;
  
	public GestorPartida(Tablero tablero, ArrayList<Jugador> jugadores) {
	    this.tablero = tablero;
	    this.jugadores = jugadores;
	    this.gestorTurnos = new GestorTurnos(jugadores);
	}

    public void iniciarPartida() {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	System.out.println("¿Qué modo de juego quieres jugar?");
    	System.out.println("1. Multijugador (mínimo 2 jugadores)");
    	System.out.println("2. 4 jugadores + CPU (foca)");
    	
    	int modo = scan.nextInt();
    	
    	if(modo == 1) { //multijugador.
    		if(jugadores.size() < 2) {
    			System.out.println("Mínimo deben haber dos jugadores.");
    			return;
    		}
    		
    		System.out.println("Partida multijugador iniciada con " + jugadores.size() + " jugadores.");
    	} else if(modo == 2) { //CPU
    		Foca foca = new Foca(0, "Foca", "Gris");
    		
    		jugadores.add(foca);
    		
    		System.out.println("Partida iniciada con la foca.");
    	} else {
    		System.out.println("Opción no válida.");
    	}
    	
    }
    public void siguienteTurno() {
        gestorTurnos.siguienteTurno();
    }
    public void activarCasilla(Jugador j) {

    }
    public Jugador getJugadorActual() {
        return gestorTurnos.getJugadorActual();
    }
    public void moverJugador(Jugador j, int pasos) {
        j.moverPosicion(pasos);
    }
    public boolean comprobarGanador(Jugador j) {

        if (j.getPosicion() >= tablero.getTamano() - 1) {
            System.out.println(j.getNombre() + " ha ganado la partida!");
            return true;
        }

        return false;
    }

}