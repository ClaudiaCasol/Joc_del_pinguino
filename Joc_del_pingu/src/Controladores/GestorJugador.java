package Controladores;

import Modelos.Foca;
import Modelos.Jugador;
import Modelos.Pinguino;
import Modelos.Tablero;

public class GestorJugador {

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

	public void jugadorFinalizaturno(Jugador j) {
		if (j.getTurnosPerdidos() > 0) {
			j.setTurnosPerdidos(j.getTurnosPerdidos() - 1);
		}

		System.out.println(j.getNombre() + " termina su turno.");
	}

	public void jugadorUsaObjeto(Pinguino p, String nombreObjeto) {
		System.out.println("El jugador usa el objeto " + nombreObjeto);
	}

	public void pinguinoEvento(Pinguino p) {
		System.out.println("Evento para el pinguino " + p.getNombre());
	}

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