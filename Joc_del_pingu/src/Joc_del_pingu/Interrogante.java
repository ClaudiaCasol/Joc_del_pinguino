package Joc_del_pingu;
import java.util.Random;

public class Interrogante extends Casilla {

    private Random random = new Random();

    public Interrogante(int posicion) {
        super(posicion);
    }

    @Override
    public void realizarAccion(Partida partida, Jugador jugador) {

        int evento = random.nextInt(4); // 0-3

        switch (evento) {

            case 0:
                // Obtener pez
                if (jugador.getPeces() < 2) {
                    jugador.addPez();
                    System.out.println("¡Has obtenido un pez!");
                } else {
                    System.out.println("Ya tienes el máximo de peces.");
                }
                break;

            case 1:
                // Obtener 1-3 bolas de nieve
                int bolas = random.nextInt(3) + 1;
                jugador.addBolaNieve(bolas);
                System.out.println("¡Has obtenido " + bolas + " bolas de nieve!");
                break;

            case 2:
                // Dado rápido (probabilidad baja)
                if (random.nextInt(100) < 25) { // 25% probabilidad
                    int avance = random.nextInt(6) + 5; // 5-10
                    jugador.moverPosicion(avance);
                    System.out.println("¡Dado rápido! Avanzas " + avance + " casillas.");
                } else {
                    System.out.println("Intentaste dado rápido pero fallaste.");
                }
                break;

            case 3:
                // Dado lento (probabilidad alta)
                int avanceLento = random.nextInt(3) + 1; // 1-3
                jugador.moverPosicion(avanceLento);
                System.out.println("Dado lento: avanzas " + avanceLento + " casillas.");
                break;
        }
    }
}