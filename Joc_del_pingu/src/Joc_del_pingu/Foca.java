package Joc_del_pingu;

public class Foca extends Jugador {

    private boolean bloqueada = false;
    private int turnosBloqueada = 0;

    public Foca(int posicion, String nombre, String color, Inventario inv) {
        super(posicion, nombre, color,inv);
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public int getTurnosBloqueada() {
        return turnosBloqueada;
    }

    public boolean estaBloqueada() {
        return turnosBloqueada > 0;
    }

    public void bloquear(int turnos) {
        if (turnos > 0) {
            this.bloqueada = true;
            this.turnosBloqueada += turnos;
        }
    }

    public void reducirBloqueo() {
        if (turnosBloqueada > 0) {
            turnosBloqueada--;
        }
        if (turnosBloqueada == 0) {
            bloqueada = false;
        }
    }

    public void atacarJugador(Pinguino p) {
    	p.getInventario().perderMitadInventario();
    }

   
    @Override
    public String toString() {
        return "Foca " + getNombre() +
                " (" + getColor() + ") - Posición: " +
                getPosicion() + " - Bloqueada: " + turnosBloqueada;
    }
}