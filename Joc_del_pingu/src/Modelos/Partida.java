package Modelos;

import java.util.ArrayList;

/**
 * Classe que representa una partida del Joc del Pingüí.
 * Gestiona l'estat de la partida: jugadors, tauler, torns, guanyador i si ha finalitzat.
 */
public class Partida {

    /** Tauler de joc associat a la partida */
    private Tablero tablero;

    /** Llista de jugadors que participen a la partida */
    private ArrayList<Jugador> jugadores;

    /** Data en què va ser guardada la partida */
    private String fecha;

    /** Número de ronda global completada */
    private int turnos;

    /** Índex del jugador que té el torn actual */
    private int jugadorActual;

    /** Identificador únic de la partida a la base de dades */
    private int id;

    /** Indica si la partida ha finalitzat */
    private boolean finalizada;

    /** Jugador que ha guanyat la partida (null si encara no ha acabat) */
    private Jugador ganador;

    /**
     * Constructor per crear una nova partida amb una llista de jugadors.
     * Inicialitza el tauler per defecte i el torn al primer jugador.
     *
     * @param jugadores Llista de jugadors que participaran
     */
    public Partida(ArrayList<Jugador> jugadores) {
        this.tablero = new Tablero();
        this.jugadores = jugadores;
        this.jugadorActual = 0;
        this.turnos = 0;
        this.finalizada = false;
        this.ganador = null;
    }

    /**
     * Constructor per carregar una partida guardada amb totes les seves dades.
     *
     * @param id            Identificador de la partida a la BBDD
     * @param jugadores     Llista de jugadors de la partida
     * @param tablero       Tauler amb les caselles de la partida
     * @param fecha         Data en què es va guardar la partida
     * @param turnos        Número de torns jugats fins al moment
     * @param jugadorActual Índex del jugador que té el torn
     */
    public Partida(int id, ArrayList<Jugador> jugadores, Tablero tablero, String fecha, int turnos, int jugadorActual) {
        this.id = id;
    	this.jugadores = jugadores;
        this.tablero = tablero;
        this.fecha = fecha;
        this.turnos = turnos;
        this.jugadorActual = jugadorActual;
        this.finalizada = false;
        this.ganador = null;
    }

    /**
     * Inicialitza (o reinicialitza) l'estat de la partida des de zero.
     * Posa els torns a 0, el jugador actual al primer i buida el guanyador.
     */
    public void iniciarPartida() {
        this.turnos = 0;
        this.jugadorActual = 0;
        this.finalizada = false;
        this.ganador = null;
    }

    /**
     * Marca la partida com a finalitzada.
     */
    public void finalizarPartida() {
        this.finalizada = true;
    }

    /**
     * Retorna l'identificador de la partida.
     *
     * @return ID de la partida
     */
    public int getId() {
    	return id;
    }

    /**
     * Retorna la data en què es va guardar la partida.
     *
     * @return Data de la partida en format String
     */
    public String getFecha() {
    	return fecha;
    }

    /**
     * Retorna un jugador de la llista a partir del seu índex.
     *
     * @param i Índex del jugador a obtenir
     * @return Objecte Jugador corresponent a l'índex
     */
    public Jugador getJugador(int i) {
        return jugadores.get(i);
    }

    /**
     * Retorna el jugador que té el torn en aquest moment.
     *
     * @return Jugador actual
     */
    public Jugador getJugadorActual() {
        return jugadores.get(jugadorActual);
    }

    /**
     * Retorna l'índex del jugador actual dins la llista.
     *
     * @return Índex del jugador actual
     */
    public int getJugadorActualIndex() {
        return jugadorActual;
    }

    /**
     * Avança al torn del següent jugador.
     * Si tots han jugat, torna al primer i incrementa el comptador de rondes.
     */
    public void avanzarTurno() {
        jugadorActual++;

        if (jugadorActual >= jugadores.size()) {
            jugadorActual = 0;
            turnos++;
        }
    }

    /**
     * Mou un jugador un nombre de passos sobre el tauler.
     * Si sobrepassaria l'última casella, fa rebot cap enrere.
     * Si quedaria per sota de -1, s'estableix a -1.
     *
     * @param jugador Jugador que es mou
     * @param pasos   Nombre de caselles que avança (pot ser negatiu)
     */
    public void moverJugador(Jugador jugador, int pasos) {
        int posicionActual = jugador.getPosicion();
        int ultimaPosicion = tablero.getTamano() - 1;

        int nuevaPos = posicionActual + pasos;

        if (nuevaPos < -1) {
            nuevaPos = -1;
        }

        // Rebot si se sobrepassa la casella final
        if (nuevaPos > ultimaPosicion) {
            int exceso = nuevaPos - ultimaPosicion;
            nuevaPos = ultimaPosicion - exceso;
        }

        if (nuevaPos < -1) {
            nuevaPos = -1;
        }

        jugador.setPosicion(nuevaPos);
    }

    /**
     * Fa que un jugador perdi un torn afegint-li un torn perdut.
     *
     * @param jugador Jugador al qual se li aplica la penalització
     */
    public void saltarTurno(Jugador jugador) {
        jugador.setTurnosPerdidos(jugador.getTurnosPerdidos() + 1);
    }

    /**
     * Executa el torn del jugador actual amb el resultat del dau indicat.
     * Gestiona els casos de foca bloquejada, torns perduts, moviment,
     * execució de casella i comprovació de victòria.
     *
     * @param resultadoDado Resultat del llançament del dau
     */
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

        if (jugador.getPosicion() >= 0) {
            Casilla casilla = tablero.getCasilla(jugador.getPosicion());

            if (casilla != null) {
                casilla.realizarAccion(this, jugador);
                normalizarPosicion(jugador);
            }
        }

        // Només guanya si cau exactament a l'última casella
        if (jugador.getPosicion() == tablero.getTamano() - 1) {
            System.out.println("Ha ganado " + jugador.getNombre());
            ganador = jugador;
            finalizada = true;
            return;
        }

        avanzarTurno();
    }

    /**
     * Normalitza la posició d'un jugador perquè no quedi fora dels límits del tauler.
     *
     * @param jugador Jugador al qual s'ha de normalitzar la posició
     */
    private void normalizarPosicion(Jugador jugador) {
        if (jugador.getPosicion() < -1) {
            jugador.setPosicion(-1);
        }

        if (jugador.getPosicion() >= tablero.getTamano()) {
            jugador.setPosicion(tablero.getTamano() - 1);
        }
    }

    /**
     * Indica si la partida ha finalitzat.
     *
     * @return true si la partida ha acabat, false si continua
     */
    public boolean estaFinalizada() {
        return finalizada;
    }

    /**
     * Retorna el tauler de la partida.
     *
     * @return Objecte Tablero de la partida
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Estableix el tauler de la partida.
     *
     * @param tablero Nou tauler a assignar
     */
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    /**
     * Retorna la llista completa de jugadors de la partida.
     *
     * @return Llista de jugadors
     */
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Estableix la llista de jugadors de la partida.
     *
     * @param jugadores Nova llista de jugadors
     */
    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    /**
     * Retorna el nombre de rondes jugades fins ara.
     *
     * @return Número de torns globals
     */
    public int getTurnos() {
        return turnos;
    }

    /**
     * Estableix el comptador de torns de la partida.
     *
     * @param turnos Nou valor de torns
     */
    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    /**
     * Estableix l'índex del jugador actual.
     *
     * @param jugadorActual Nou índex del jugador amb el torn
     */
    public void setJugadorActual(int jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    /**
     * Retorna el jugador que ha guanyat la partida.
     *
     * @return Jugador guanyador, o null si la partida no ha acabat
     */
    public Jugador getGanador() {
        return ganador;
    }

    /**
     * Estableix el jugador guanyador de la partida.
     *
     * @param ganador Jugador que ha guanyat
     */
    public void setGanador(Jugador ganador) {
        this.ganador = ganador;
    }
}