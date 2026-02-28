package JuegoPinguino;

import java.util.ArrayList;

public class Partida {

    private Tablero tablero;
    private ArrayList<Jugador> jugadores;
    private int turnoActual;
    private boolean finalizada;

    public Partida(ArrayList<Jugador> jugadores) {
        this.tablero = new Tablero();
        this.jugadores = jugadores;
        this.turnoActual = 0;
        this.finalizada = false;
    }

    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public void avanzarTurno() {
        turnoActual++;

        if (turnoActual >= jugadores.size()) {
            turnoActual = 0;
        }
    }

    public void moverJugador(Jugador jugador, int pasos) {
        int nuevaPos = jugador.getPosicion() + pasos;

        if (nuevaPos < 0) {
            nuevaPos = 0;
        }

        if (nuevaPos >= tablero.getTamaño()) {
            nuevaPos = tablero.getTamaño() - 1;
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

        // Si el jugador tiene turnos perdidos
        if (jugador.getTurnosPerdidos() > 0) {
            jugador.setTurnosPerdidos(jugador.getTurnosPerdidos() - 1);
            avanzarTurno();
            return;
        }

        // Movimiento por dado
        moverJugador(jugador, resultadoDado);

        // Aplicar casilla
        Casilla casilla = tablero.getCasilla(jugador.getPosicion());

        if (casilla != null) {
            casilla.realizarAccion(this, jugador);

            // Por si alguna casilla mueve directamente al jugador
            normalizarPosicion(jugador);
        }

        // Comprobar victoria
        if (jugador.getPosicion() == tablero.getTamaño() - 1) {
            System.out.println("Ha ganado " + jugador.getNombre());
            finalizada = true;
            return;
        }

        avanzarTurno();
    }

    private void normalizarPosicion(Jugador jugador) {

        if (jugador.getPosicion() < 0) {
            jugador.setPosicion(0);
        }

        if (jugador.getPosicion() >= tablero.getTamaño()) {
            jugador.setPosicion(tablero.getTamaño() - 1);
        }
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public Tablero getTablero() {
        return tablero;
    }
}