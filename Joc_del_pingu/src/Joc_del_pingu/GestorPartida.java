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

    public GestorPartida() {
        gestorTablero = new GestorTablero();
        gestorJugador = new GestorJugador();
        random = new Random();
        partida = null;
    }

    public void nuevaPartida(ArrayList<Jugador> jugadores, Tablero tablero){
        partida = new Partida(jugadores);
        partida.setTablero(tablero);
        partida.iniciarPartida();
    }

    public int tirarDado(Jugador j, Dado dadoOpcional){
        
        int resultado;
        
        if(dadoOpcional != null) {
            resultado = dadoOpcional.lanzar(random);
        }
        else {
            resultado = random.nextInt(6) + 1;
        }

        return resultado;
                        
        }

    public void ejecutarTurnoCompleto(){

        if(partida == null){
            return;
        }

        if(partida.estaFinalizadaFinalizada()){
            return;
        }

        Jugador jugadorActual = partida.getJugadorActual();

        if(jugadorActual instanceof Foca){
            Foca foca = (Foca) jugadorActual;

            if(foca.estaBloqueada()){
                foca.reducirBloqueo();
                partida.avanzarTurno();
                return;
            }
        }

        if(jugadorActual.getTurnosPerdidos() > 0){
            jugadorActual.setTurnosPerdidos(jugadorActual.getTurnosPerdidos() - 1);
            partida.avanzarTurno();
            return;
        }

        int resultadoDado = tirarDado(jugadorActual, null);

        gestorJugador.jugadorSeMueve(jugadorActual, resultadoDado, partida.getTablero());

        Casilla casillaActual = partida.getTablero().getCasilla(jugadorActual.getPosicion());

    if(jugadorActual instanceof Pinguino && casillaActual != null){
        gestorTablero.ejecutarCasilla(partida, (Pinguino) jugadorActual, casillaActual);
    }

    gestorTablero.comprobarFinTurno(partida);

    if(partida.estaFinalizada()){
        return;
    }

    partida.avanzarTurno();
}