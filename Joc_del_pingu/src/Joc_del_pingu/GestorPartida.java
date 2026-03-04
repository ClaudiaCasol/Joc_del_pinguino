package Joc_del_pingu;

import java.util.Random;

public class GestorPartida {

    private Partida partida;
    private GestorTablero gestorTablero;
    private GestorJugador gestorJugador;
    private Random random;

    public GestorPartida() {
        gestorTablero = new GestorTablero();
        gestorJugador = new GestorJugador();
        random = new Random();
        partida = null;
    }

    public void nuevaPartida(ArrayList<Jugador> jugadores, Tablero tablero){
        partida = new Partida (jugadores);
        partida.setTablero(tablero)
        partida.iniciarPartida();
    }

    public int tirarDado(Jugador j, Dado dadoOpcional){
        if {
            dadoOpcional != null;
            
        }
    }
        
}