package Modelos;

public class Usuario {
    
    private String nombre;
    private String contraseña;
    private int numPartidasJugadas;
    private int numPartidasGanadas;
    
    public Usuario() {
        
    }

    public Usuario(String nombre, String contraseña) {

        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public String getNombre(){

        return nombre;

    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {

        return contraseña;

    }
    
    public int getNumPartidasJugadas() {
        return numPartidasJugadas;
    }

    public void setNumPartidasJugadas(int numPartidasJugadas) {
        this.numPartidasJugadas = numPartidasJugadas;
    }

    public int getNumPartidasGanadas() {
        return numPartidasGanadas;
    }

    public void setNumPartidasGanadas(int numPartidasGanadas) {
        this.numPartidasGanadas = numPartidasGanadas;
    }
}