package JuegoPinguino;

import java.util.Random;

public class Evento extends Casilla {

    private String evento;
    private Random random = new Random();

    public Evento(int posicion) {
        super(posicion);
    }

    @Override
    public void realizarAccion(Partida partida, Jugador jugador) {

        int numeroEvento = random.nextInt(3); // 0, 1 o 2

        switch (numeroEvento) {

            case 0:
                evento = "Encuentras un pez. Avanzas 2 posiciones.";
                System.out.println(evento);
                jugador.moverPosicion(2);
                break;

            case 1:
                evento = "Resbalas en el hielo. Retrocedes 2 posiciones.";
                System.out.println(evento);
                jugador.moverPosicion(-2);
                break;

            case 2:
                evento = "Pierdes el turno.";
                System.out.println(evento);
                partida.saltarTurno(); // SOLO si tienes algo así implementado
                break;
        }
    }
}