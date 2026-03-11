package Modelos;

import java.util.Random;

public class Interrogante extends Casilla {

	private Random random = new Random();

	public Interrogante(int posicion) {
		super(posicion);
	}

	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {

		int evento = random.nextInt(6); // 0-5

		switch (evento) {

            case 0:
                // Obtener pez
            	if (jugador instanceof Pinguino) {
            		Pinguino p = (Pinguino) jugador;
                if (p.getInventario().getPez().size() < 2) {
                	p.getInventario().agregarPez(new Pez("Pez"));
                    System.out.println("¡Has obtenido un pez!");
                } else {
                    System.out.println("Ya tienes el máximo de peces.");
                }
            	  }
                break;
                
            case 1:
            	// Obtener 1-3 bolas de nieve
            	if (jugador instanceof Pinguino) {
            		Pinguino p = (Pinguino) jugador;
                int bolas = random.nextInt(3) + 1;

                for(int i = 0; i < bolas; i++) {
                	p.getInventario().agregarBolaNieve(new BolaNieve("Bola"));
                }

                System.out.println("¡Has obtenido " + bolas + " bolas de nieve!");
            	}
                break;
            case 2:
                // Dado rápido (probabilidad baja)
            	if (jugador instanceof Pinguino) {
            		Pinguino p = (Pinguino) jugador;
                if (random.nextInt(100) < 25) { // 25% probabilidad
                    int avance = random.nextInt(6) + 5; // 5-10
                    p.moverPosicion(avance);
                    System.out.println("¡Dado rápido! Avanzas " + avance + " casillas.");
                } else {
                    System.out.println("Intentaste dado rápido pero fallaste.");
                }
            	}
                break;

            case 3:
                // Dado lento (probabilidad alta)
            	if (jugador instanceof Pinguino) {
            		Pinguino p = (Pinguino) jugador;
            		
                int avanceLento = random.nextInt(3) + 1; // 1-3
                p.moverPosicion(avanceLento);
                System.out.println("Dado lento: avanzas " + avanceLento + " casillas.");
            	}
                break;
        }
    }
}