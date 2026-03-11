package Joc_del_pingu;

public abstract class Jugador {

	private int posicion = 0;
	private String nombre = "";
	private String color = "";
	private int turnosPerdidos = 0;
	private Inventario inventario;

	public Jugador(int posicion, String nombre, String color) {
		this.posicion = posicion;
		this.nombre = nombre;
		this.color = color;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getColor() {
		return color;
	}
	

	public void setColor(String color) {
		this.color = color;
	}

	public int getTurnosPerdidos() {
		return turnosPerdidos;
	}

	public void setTurnosPerdidos(int turnosPerdidos) {
		this.turnosPerdidos = turnosPerdidos;
	}

	public void moverPosicion(int cantidad) {
		this.posicion += cantidad;
		if (this.posicion < 0) {
			this.posicion = 0;
		}
	}

	public void retroceder(int pasos) {
		this.posicion -= pasos;
		if (this.posicion < 0) {
			this.posicion = 0;
		}
	}
	public Inventario getInventario() {
		return inventario;
	}
}