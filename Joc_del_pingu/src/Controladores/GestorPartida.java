package Controladores;

import java.util.ArrayList;
import java.util.Random;

import Modelos.Dado;
import Modelos.Foca;
import Modelos.Inventario;
import Modelos.Jugador;
import Modelos.Partida;
import Modelos.Pinguino;
import Modelos.Tablero;

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

    public Partida getPartida() {
        return partida;
    }

    public int tirarDado(Jugador j, Dado dadoOpcional) {
        int resultado;

        if (dadoOpcional != null) {
            resultado = dadoOpcional.tirarDado();
        } else {
            resultado = random.nextInt(6) + 1;
        }
        
        return resultado;
    }

    public void iniciarPartida(int numeroJugadores) {
        ArrayList<Jugador> jugadores = new ArrayList<>();

        String[] nombres = { "Jugador1", "Jugador2", "Jugador3", "Jugador4" };
        String[] colores = { "Verde", "Azul", "Rosa", "Amarillo" };

        for (int i = 0; i < numeroJugadores; i++) {
            Inventario inventario = new Inventario(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            Dado dado = new Dado("normal");
            inventario.agregarDado(dado);

            jugadores.add(new Pinguino(-1, nombres[i], colores[i], inventario));
        }

        if (numeroJugadores == 4) {
            jugadores.add(new Foca(-1, "FocaCPU", "Gris"));
        }

        Tablero tablero = new Tablero();

        partida = new Partida(jugadores);
        partida.setTablero(tablero);
        partida.iniciarPartida();
    }

    public void nuevaPartida(ArrayList<Jugador> jugadores, Tablero tablero) {
        partida = new Partida(jugadores);
        partida.setTablero(tablero);
        partida.iniciarPartida();
    }
}