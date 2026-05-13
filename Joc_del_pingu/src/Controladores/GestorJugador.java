package Controladores;

import Modelos.Foca;
import Modelos.Jugador;
import Modelos.Pinguino;
import Modelos.Tablero;

/**
 * Classe controladora que gestiona les accions dels jugadors durant la partida.
 * Controla el moviment, els torns, l'ús d'objectes i les interaccions entre jugadors.
 */
public class GestorJugador {

	/**
	 * Mou un jugador la quantitat de passos indicada sobre el tauler.
	 * Si la nova posició supera el tauler, s'ajusta a l'última casella.
	 * Si la nova posició és menor que -1, s'estableix a -1 (fora del tauler).
	 *
	 * @param j     Jugador que es mou
	 * @param pasos Nombre de caselles que avança el jugador
	 * @param t     Tauler on es juga la partida
	 */
	public void jugadorSeMueve(Jugador j, int pasos, Tablero t) {
		int nuevaPos = j.getPosicion() + pasos;

		if (nuevaPos >= t.getTamano()) {
			nuevaPos = t.getTamano() - 1;
		}

		// -1 = sortida fora del tauler
		if (nuevaPos < -1) {
			nuevaPos = -1;
		}

		j.setPosicion(nuevaPos);
		System.out.println(j.getNombre() + " se mueve a la casilla " + nuevaPos);
	}

	/**
	 * Finalitza el torn d'un jugador.
	 * Si el jugador té torns perduts, en resta un.
	 *
	 * @param j Jugador que finalitza el seu torn
	 */
	public void jugadorFinalizaturno(Jugador j) {
		if (j.getTurnosPerdidos() > 0) {
			j.setTurnosPerdidos(j.getTurnosPerdidos() - 1);
		}

		System.out.println(j.getNombre() + " termina su turno.");
	}

	/**
	 * Gestiona l'ús d'un objecte de l'inventari per part d'un pingüí.
	 *
	 * @param p            Pingüí que utilitza l'objecte
	 * @param nombreObjeto Nom de l'objecte que s'utilitza
	 */
	public void jugadorUsaObjeto(Pinguino p, String nombreObjeto) {
		System.out.println("El jugador usa el objeto " + nombreObjeto);
	}

	/**
	 * Gestiona un esdeveniment especial per al pingüí.
	 *
	 * @param p Pingüí sobre el qual s'aplica l'esdeveniment
	 */
	public void pinguinoEvento(Pinguino p) {
		System.out.println("Evento para el pinguino " + p.getNombre());
	}

	/**
	 * Gestiona una guerra de boles de neu entre dos pingüins.
	 * El pingüí amb més boles guanya i avança la diferència de caselles.
	 * En acabar, tots dos pingüins perden totes les boles de neu.
	 *
	 * @param p1 Primer pingüí participant en la guerra
	 * @param p2 Segon pingüí participant en la guerra
	 */
	public void pinguinoGuerra(Pinguino p1, Pinguino p2) {
		int bolas1 = p1.getInventario().getBolaNieve().size();
		int bolas2 = p2.getInventario().getBolaNieve().size();

		System.out.println("Guerra de bolas de nieve entre el " + p1.getNombre() + " y el " + p2.getNombre());

		if (bolas1 > bolas2) {
			int diferencia = bolas1 - bolas2;
			p1.moverPosicion(diferencia);
			System.out.println(p1.getNombre() + " gana la guerra y avanza " + diferencia + " casillas.");
		} else if (bolas2 > bolas1) {
			int diferencia = bolas2 - bolas1;
			p2.moverPosicion(diferencia);
			System.out.println(p2.getNombre() + " gana la guerra y avanza " + diferencia + " casillas.");
		} else {
			System.out.println("Empate. Ningún jugador avanza.");
		}

		while (p1.getInventario().getBolaNieve().size() > 0) {
			p1.getInventario().getBolaNieve().remove(0);
		}

		while (p2.getInventario().getBolaNieve().size() > 0) {
			p2.getInventario().getBolaNieve().remove(0);
		}
	}

	/**
	 * Gestiona la interacció entre un pingüí i la foca quan coincideixen a la mateixa casella.
	 * Si el pingüí té un peix, el fa servir per subornar la foca (la bloqueja 2 torns).
	 * Si no té peix, la foca li fa perdre la meitat de l'inventari.
	 *
	 * @param p Pingüí que interactua amb la foca
	 * @param f Foca que interactua amb el pingüí
	 */
	public void focaInteractua(Pinguino p, Foca f) {
		if (p.getPosicion() == f.getPosicion()) {
			System.out.println("La foca se encuentra con " + p.getNombre());

			if (p.getInventario().tienePez()) {
				System.out.println(p.getNombre() + " usa un pez para sobornar a la foca.");
				f.bloquear(2);
			} else {
				System.out.println("La foca golpea al pinguino y pierde la mitad del inventario.");
				p.getInventario().perderMitadInventario();
			}
		}
	}
}