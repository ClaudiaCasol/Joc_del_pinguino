package JuegoPinguino;

public class Foca extends Jugador {

    private int turnosBloqueada = 0;

    public Foca(int posicion, String nombre, String color) {
        super(posicion, nombre, color);
    }

    public boolean estaBloqueada() {
        return turnosBloqueada > 0;
    }

    public void bloquear(int turnos) {
        if (turnos > 0) {
            this.turnosBloqueada += turnos;
        }
    }

    public void reducirBloqueo() {
        if (turnosBloqueada > 0) {
            turnosBloqueada--;
        }
    }

    public int getTurnosBloqueada() {
        return turnosBloqueada;
    }

    @Override
    public String toString() {
        return "Foca " + getNombre() +
               " (" + getColor() + ") - Posición: " +
               getPosicion() + " - Bloqueada: " + turnosBloqueada;
    }
}