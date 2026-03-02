package JuegoPinguino;

public class Foca extends Jugador {

    private boolean bloqueada = false;
    private int turnosBloqueada = 0;

    public Foca(int posicion, String nombre, String color) {
        super(posicion, nombre, color);
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
    }

    public void perderMitadInventario(Pinguino p) {
        if (p != null && p.getInv() != null) {
            p.getInv().perderObjetoAleatorio();
        }
    }

    @Override
    public String toString() {
        return "Foca " + getNombre() +
                " (" + getColor() + ") - Posición: " +
                getPosicion() + " - Bloqueada: " + turnosBloqueada;
    }
}