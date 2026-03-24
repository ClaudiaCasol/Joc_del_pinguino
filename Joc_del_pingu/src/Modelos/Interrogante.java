package Modelos;

import java.util.Random;

public class Interrogante extends Casilla {

    private Random rand = new Random();

    public Interrogante(int posicion) {
        super(posicion);
    }

    @Override
    public void realizarAccion(Partida partida, Jugador jugador) {
        if (!(jugador instanceof Pinguino)) {
            return;
        }

        Pinguino p = (Pinguino) jugador;

        int opcion = rand.nextInt(100);

        if (opcion < 15) {
            // 15%: dado rápido
            p.getInventario().agregarDado(new Dado_rapido());
            System.out.println("Has conseguido un dado rápido.");
        } else if (opcion < 60) {
            // 45%: dado lento
            p.getInventario().agregarDado(new Dado_lento());
            System.out.println("Has conseguido un dado lento.");
        } else if (opcion < 80) {
            // 20%: pez
            p.getInventario().agregarPez(new Pez("pez"));
            System.out.println("Has conseguido un pez.");
        } else {
            // 20%: bola de nieve
            p.getInventario().agregarBolaNieve(new BolaNieve("bolaNieve"));
            System.out.println("Has conseguido una bola de nieve.");
        }
    }
}