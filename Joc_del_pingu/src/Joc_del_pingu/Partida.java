package Joc_del_pingu;

import java.util.ArrayList;

public class Partida {

    private Tablero tablero;
    private ArrayList<Jugador> jugadores;

    private int turnos = 0;
    private int jugadorActual = 0;

    private boolean finalizada;
    private Jugador ganador;

    public Partida(ArrayList<Jugador> jugadores) {
        this.tablero = new Tablero();
        this.jugadores = jugadores;
        this.jugadorActual = 0;
        this.turnos = 0;
        this.finalizada = false;
        this.ganador = null;
    }

    public void iniciarPartida() {
        this.turnos = 0;
        this.jugadorActual = 0;
        this.finalizada = false;
        this.ganador = null;
    }

    public void finalizarPartida() {
        this.finalizada = true;
    }

    public Jugador getJugadorActual() {
        return jugadores.get(jugadorActual);
    }

    public void avanzarTurno() {
        jugadorActual++;

        if (jugadorActual >= jugadores.size()) {
            jugadorActual = 0;
            turnos++;
        }
    }

    public void moverJugador(Jugador jugador, int pasos) {
        int nuevaPos = jugador.getPosicion() + pasos;

        if (nuevaPos < 0) {
            nuevaPos = 0;
        }

        if (nuevaPos >= tablero.getTamano()) {
            nuevaPos = tablero.getTamano() - 1;
        }

        jugador.setPosicion(nuevaPos);
    }

    public void saltarTurno(Jugador jugador) {
        jugador.setTurnosPerdidos(jugador.getTurnosPerdidos() + 1);
    }

    public void jugarTurno(int resultadoDado) {

        if (finalizada) {
            return;
        }

        Jugador jugador = getJugadorActual();

        if (jugador instanceof Foca) {
            Foca foca = (Foca) jugador;

            if (foca.estaBloqueada()) {
                foca.reducirBloqueo();
                avanzarTurno();
                return;
            }
        }

        if (jugador.getTurnosPerdidos() > 0) {
            jugador.setTurnosPerdidos(jugador.getTurnosPerdidos() - 1);
            avanzarTurno();
            return;
        }

        moverJugador(jugador, resultadoDado);

        Casilla casilla = tablero.getCasilla(jugador.getPosicion());

        if (casilla != null) {
            casilla.realizarAccion(this, jugador);
            normalizarPosicion(jugador);
        }

        if (jugador.getPosicion() == tablero.getTamano() - 1) {
            System.out.println("Ha ganado " + jugador.getNombre());
            ganador = jugador;
            finalizada = true;
            return;
        }

        avanzarTurno();
    }

    private void normalizarPosicion(Jugador jugador) {

        if (jugador.getPosicion() < 0) {
            jugador.setPosicion(0);
        }

        if (jugador.getPosicion() >= tablero.getTamano()) {
            jugador.setPosicion(tablero.getTamano() - 1);
        }
    }

    public boolean estaFinalizada() {
        return finalizada;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    public void setJugadorActual(int jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public void setGanador(Jugador ganador) {
        this.ganador = ganador;
    }
}