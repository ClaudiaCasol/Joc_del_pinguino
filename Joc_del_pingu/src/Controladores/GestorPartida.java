package Controladores;

import java.util.ArrayList;
import java.util.Random;

import Modelos.BolaNieve;
import Modelos.Dado;
import Modelos.Foca;
import Modelos.Inventario;
import Modelos.Jugador;
import Modelos.Partida;
import Modelos.Pez;
import Modelos.Pinguino;
import Modelos.Tablero;
import Modelos.Usuario;

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

    public Partida getPartida() { return partida; }

    public int tirarDado(Jugador j, Dado dadoOpcional) {
        if (dadoOpcional != null) {
            return dadoOpcional.tirarDado();
        }
        return random.nextInt(6) + 1;
    }

    public void iniciarPartida(int numeroJugadores, ArrayList<Usuario> usuarios) {
        ArrayList<Jugador> jugadores = new ArrayList<>();

        String[] nombres = { "Jugador1", "Jugador2", "Jugador3", "Jugador4" };
        String[] colores = { "Verd", "Blau", "Rosa", "Groc" };

        for (int i = 0; i < numeroJugadores; i++) {
            Inventario inventario = crearInventarioInicial();
            jugadores.add(new Pinguino(-1, nombres[i], colores[i], inventario, usuarios.get(i)));
        }

        if (numeroJugadores == 4) {
            Inventario inventarioFoca = crearInventarioInicial();
            jugadores.add(new Foca(-1, "FocaCPU", "Gris", inventarioFoca));
        }

        Tablero tablero = new Tablero();
        partida = new Partida(jugadores);
        partida.setTablero(tablero);
        partida.iniciarPartida();
    }

    private Inventario crearInventarioInicial() {
        ArrayList<Dado> dados      = new ArrayList<>();
        ArrayList<BolaNieve> bolas = new ArrayList<>();
        ArrayList<Pez> peces       = new ArrayList<>();

        dados.add(new Dado());
        bolas.add(new BolaNieve());
        peces.add(new Pez());

        return new Inventario(dados, bolas, peces);
    }

    public void nuevaPartida(ArrayList<Jugador> jugadores, Tablero tablero) {
        partida = new Partida(jugadores);
        partida.setTablero(tablero);
        partida.iniciarPartida();
    }
}