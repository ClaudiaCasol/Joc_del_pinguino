package JuegoPinguino;

public class Pinguino extends Jugador {

    private Inventario inv;

    public Pinguino(int posicion, String nombre, String color) {
        super(posicion, nombre, color);
        this.inv = new Inventario();
    }

    public Inventario getInv() {
        return inv;
    }

    public void setInv(Inventario inv) {
        this.inv = inv;
    }

    public void gestionarBatalla(Pinguino p2) {
        // TODO: implementar cuando toque la "guerra"
    }

    public void usarObjeto(Objetos item) {
        if (item != null) {
            item.usar(this);
        }
    }

    public void anadirItem(Objetos item) {
        if (item != null && inv != null) {
            inv.agregarObjeto(item);
        }
    }

    public void quitarItem(Objetos item) {
        if (item != null && inv != null) {
            inv.eliminarObjeto(item);
        }
    }

    @Override
    public String toString() {
        return "Pinguino " + getNombre() +
                " (" + getColor() + ") - Posición: " +
                getPosicion();
    }
}