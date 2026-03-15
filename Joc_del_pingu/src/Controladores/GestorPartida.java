package Controladores;

import java.util.ArrayList;
import java.util.Random;

import Modelos.Casilla;
import Modelos.Dado;
import Modelos.Foca;
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

    public void nuevaPartida(ArrayList<Jugador> jugadores, Tablero tablero) {
        partida = new Partida(jugadores);
        partida.setTablero(tablero);
        partida.iniciarPartida();
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

    public void ejecutarTurnoCompleto() {

        if (partida == null) {
            return;
        }

        if (partida.estaFinalizada()) {
            return;
        }

        Jugador jugadorActual = partida.getJugadorActual();

        if (jugadorActual instanceof Foca) {
            Foca foca = (Foca) jugadorActual;

            if (foca.estaBloqueada()) {
                foca.reducirBloqueo();
                partida.avanzarTurno();
                return;
            }
        }

        if (jugadorActual.getTurnosPerdidos() > 0) {
            jugadorActual.setTurnosPerdidos(jugadorActual.getTurnosPerdidos() - 1);
            partida.avanzarTurno();
            return;
        }

        Dado dadoSeleccionado = null;

        if (jugadorActual instanceof Pinguino) {
            Pinguino p = (Pinguino) jugadorActual;

            if (p.getInventario() != null && !p.getInventario().getDado().isEmpty()) {
                dadoSeleccionado = p.getInventario().getDado().get(0);
            }
        }

        int resultadoDado = tirarDado(jugadorActual, dadoSeleccionado);

        gestorJugador.jugadorSeMueve(jugadorActual, resultadoDado, partida.getTablero());

        Casilla casillaActual = partida.getTablero().getCasilla(jugadorActual.getPosicion());

        if (jugadorActual instanceof Pinguino && casillaActual != null) {
            gestorTablero.ejecutarCasilla(partida, (Pinguino) jugadorActual, casillaActual);
        }

        gestorTablero.comprobarFinTurno(partida);

        if (partida.estaFinalizada()) {
            return;
        }

        partida.avanzarTurno();
    }
}