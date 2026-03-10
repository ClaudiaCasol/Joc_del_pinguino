package Joc_del_pingu;

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
			if (jugador.getInventario().getPez().size() < 2) {
				jugador.getInventario().agregarPez(new Pez("Pez"));
				System.out.println("¡Has obtenido un pez!");
			} else {
				System.out.println("Ya tienes el máximo de peces.");
			}
			break;

		case 1:
			// Obtener 1-3 bolas de nieve

			int bolas = random.nextInt(3) + 1;

			for(int i = 0; i < bolas; i++) {
				jugador.getInventario().agregarBolaNieve(new BolaNieve("Bola"));
			}

			System.out.println("¡Has obtenido " + bolas + " bolas de nieve!");
			break;
		case 2:
			//dado lento y dado rápido.
			int probabilidad = random.nextInt(100); 

			if(probabilidad < 25) {
				jugador.getInventario().agregarDado(new Dado_rapido("Dado rápido"));

				System.out.println("Has conseguido un dado rápido.");
			} else {
				jugador.getInventario().agregarDado(new Dado_lento("Dado lento"));

				System.out.println("Has conseguido un dado lento.");
			}

		case 3:
			//moto de nieve.
			System.out.println("Has caído en una moto de nieve. Avanzas hasta el siguiente trineo.");

			for(int i = jugador.getPosicion() + 1; i < partida.getTablero().getTamano(); i++) { //es + 1 para que el bucle no detecte la casilla a la que ya está el jugador.
				if(partida.getTablero().getCasilla(i) instanceof Trineo) {
					jugador.setPosicion(i);

					break;
				}
			}
			break;

		case 4:
			//perder un turno.

			jugador.setTurnosPerdidos(jugador.getTurnosPerdidos() + 1);

			System.out.println("Pierdes un turno");

			break;

		case 5:
			//perder un objeto aleatorio.
			jugador.getInventario().perderObjetoAleatorio();

			System.out.println("Has perdido un objeto aleatorio del inventario.");

			break;
		}
	}
}