package Modelos;

public class Pinguino extends Jugador {
    
    private Usuario usuario;
    
     public Pinguino(int posicion, String nombre, String color, Inventario inventario, Usuario usuario) {
            
         super(posicion, nombre, color, inventario);
            this.usuario = usuario;
          
        }
     
     public Pinguino(int posicion, String nombre, String color, Inventario inventario, int turnosPerdidos, int partidasJugadas, int turno, Usuario usuario) {
            super(posicion, nombre, color, inventario, turnosPerdidos, partidasJugadas, turno);
            this.usuario = usuario;
        }

        public void gestionarBatalla(Pinguino p2) {
            // implementar cuando toque la guerra
        }

        public void anadirItem(Objetos item) {

            if (item instanceof BolaNieve) {
                getInventario().agregarBolaNieve((BolaNieve) item);
            } 
            else if (item instanceof Dado) {
                getInventario().agregarDado((Dado) item);
            } 
            else if (item instanceof Pez) {
                getInventario().agregarPez((Pez) item);
            }
        }

        public void eliminarItem(Objetos item) {

            if (item instanceof BolaNieve) {
                getInventario().eliminarBolaNieve((BolaNieve) item);
            } 
            else if (item instanceof Dado) {
                getInventario().eliminarDado((Dado) item);
            } 
            else if (item instanceof Pez) {
                getInventario().eliminarPez((Pez) item);
            }
        }
        
        @Override
        public String toString() {
            return "Pinguino " + getNombre() +
                   " (" + getColor() + ") - Posición: " +
                   getPosicion();
        }
}