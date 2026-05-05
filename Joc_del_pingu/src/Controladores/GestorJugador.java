package Controladores;

import Modelos.BolaNieve;
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

        if (nuevaPos < -1) {
            nuevaPos = -1;
        }

        j.setPosicion(nuevaPos);
        System.out.println(j.getNombre() + " es mou a la casella " + nuevaPos);
    }

    public void jugadorFinalizaTurno(Jugador j) {
        if (j.getTurnosPerdidos() > 0) {
            j.setTurnosPerdidos(j.getTurnosPerdidos() - 1);
        }
        System.out.println(j.getNombre() + " acaba el torn.");
    }

    public void jugadorUsaObjeto(Pinguino p, String nombreObjeto) {
        System.out.println("El jugador usa l'objecte: " + nombreObjeto);
    }

    public void pinguinoEvento(Pinguino p) {
        System.out.println("Esdeveniment per al pingüí " + p.getNombre());
    }

    public void pinguinoGuerra(Pinguino p1, Pinguino p2) {
        int bolas1 = p1.getInventario().getBolaNieve().size();
        int bolas2 = p2.getInventario().getBolaNieve().size();

        System.out.println("Guerra de boles de neu entre " + p1.getNombre() + " i " + p2.getNombre());

        if (bolas1 > bolas2) {
            int diferencia = bolas1 - bolas2;
            p2.retroceder(diferencia);
            System.out.println(p1.getNombre() + " guanya! " + p2.getNombre() + " retrocedeix " + diferencia + " caselles.");
        } else if (bolas2 > bolas1) {
            int diferencia = bolas2 - bolas1;
            p1.retroceder(diferencia);
            System.out.println(p2.getNombre() + " guanya! " + p1.getNombre() + " retrocedeix " + diferencia + " caselles.");
        } else {
            System.out.println("Empat! Cap jugador retrocedeix.");
        }

        p1.getInventario().getBolaNieve().clear();
        p2.getInventario().getBolaNieve().clear();
    }

    public void focaInteractua(Pinguino p, Foca f) {
        if (p.getPosicion() == f.getPosicion()) {
            System.out.println("La foca es troba amb " + p.getNombre());

            if (p.getInventario().tienePez()) {
                p.getInventario().usarPez();
                f.bloquear(2);
                System.out.println(p.getNombre() + " usa un peix per subornar la foca. Bloquejada 2 torns.");
            } else {
                p.getInventario().perderMitadInventario();
                System.out.println("La foca colpeja " + p.getNombre() + " i perd la meitat de l'inventari.");
            }
        }
    }
}