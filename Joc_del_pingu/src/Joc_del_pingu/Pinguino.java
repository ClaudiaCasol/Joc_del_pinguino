package JuegoPinguino;

public class Pinguino extends Jugador {

    public Pinguino(int posicion, String nombre, String color) {
        super(posicion, nombre, color);
    }

    @Override
    public String toString() {
        return "Pinguino " + getNombre() +
               " (" + getColor() + ") - Posición: " +
               getPosicion();
    }
}