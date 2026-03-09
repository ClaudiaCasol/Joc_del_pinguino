package Joc_del_pingu;

public class GestorJugador {

	public void jugadorSeMueve(Jugador j, int pasos, Tablero t) {
		int nuevaPos = j.getPosicion() + pasos; //calculamos la nueva posición del jugador.
		
		if(nuevaPos >= t.getTamaño()) { //si se pasa del tablero, se queda en la última casilla.
			nuevaPos = t.getTamaño() - 1; 
		}
		
		if(nuevaPos < 0) { //lo dejamos en 0 si queda en negativo.
			nuevaPos = 0;
		}
		
		j.setPosicion(nuevaPos); //actualizamos la posición del jugador.
		System.out.println(j.getNombre() + " se mueve a la casilla " + nuevaPos);
	}
	
	public void jugadorFinalizaturno(Jugador j) {
		if(j.getTurnosPerdidos() > 0) {
			j.setTurnosPerdidos(j.getTurnosPerdidos() - 1); //si el jugador tiene turnos perdidos, lo reducimos.
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
		
		int bolas1 = p1.getInv().getBolaNieve().size();
		int bolas2 = p2.getInv().getBolaNieve().size();
		
		System.out.println("Guerra de bolas de nieve entre el " + p1.getNombre() + " y el " + p2.getNombre());
		
		if(bolas1 > bolas2) {
			int diferencia = bolas1 - bolas2;
			p1.moverPosicion(diferencia);
			
			System.out.println(p1.getNombre() + " gana la guerra y avanza " + diferencia + " casillas.");
		} else if(bolas2 > bolas1) {
			int diferencia = bolas2 - bolas1;
			p2.moverPosicion(diferencia);
			
			System.out.println(p2.getNombre() + " gana la guerra y avanza " + diferencia + " casillas.");
		} else {
			System.out.println("Empate. Ningún jugador avanza.");
		}
		
		while(p1.getInv().getBolaNieve().size() > 0) { //esto es para que el jugador 1 pierda las bolas de nieve.
			p1.getInv().getBolaNieve().remove(0);
		}
		
		while(p1.getInv().getBolaNieve().size() > 0) { //el jugador 2 pierde las bolas de nieve.
			p2.getInv().getBolaNieve().remove(0);
		}
	}
	
	public void focaInteractua(Pinguino p, Foca f) {
		if(p.getPosicion() == f.getPosicion()) { //si el jugador y la foca estan en la misma casilla.
			System.out.println("La foca se encuentra con " + p.getNombre());
			
			if(p.getInv().tienePez()) {
				System.out.println(p.getNombre() + " usa un pez para sobornar a la foca.");
				
				f.bloquear(2); //la foca queda bloqueada dos turnos.
			} else {
				System.out.println("La foca golpea al pinguino y pierde la mitad del inventario.");
				
				p.getInv().perderMitadInventario();
			}
		}
	}
}
