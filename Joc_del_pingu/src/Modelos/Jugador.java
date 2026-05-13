package Modelos;

// Classe abstracta base per a tots els jugadors de la partida.
// Defineix els atributs i comportaments comuns a Pinguino (jugador humà) i Foca (CPU).
// No es pot instanciar directament; cal usar les subclasses.
public abstract class Jugador {

	// Posició actual del jugador al taulell (0-49). -1 indica que encara no ha entrat
	private int posicion = 0;

	// Nom visible del jugador a la pantalla (ex: "Jugador1", "FocaCPU")
	private String nombre = "";

	// Color de la fitxa del jugador (ex: "Verde", "Azul", "Rosa", "Amarillo", "Gris")
	private String color = "";

	// Nombre de torns que el jugador ha de saltar per penalitzacions de caselles
	// Quan és > 0, el jugador perd el seu torn i el valor es decrementa en 1
	private int turnosPerdidos = 0;

	// Referència a l'inventari personal del jugador (dados, boles de neu, peixos)
	private Inventario inventario;

	// Referència a l'usuari registrat a la BBDD (només usat per Pinguino, no per Foca)
	private Usuario usuario;

	// Constructor complet: inicialitza el jugador amb posició, nom, color i inventari
	public Jugador(int posicion, String nombre, String color, Inventario inventario) {
		this.posicion = posicion;
		this.nombre = nombre;
		this.color = color;
		this.inventario = inventario;
	}

	// Constructor amb torns perduts inicials: usat en carregar partides de la BBDD
	public Jugador(int posicion, String nombre, String color, Inventario inventario, int turnosPerdidos) {
		this.posicion = posicion;
		this.nombre = nombre;
		this.color = color;
		this.inventario = inventario;
		this.turnosPerdidos = turnosPerdidos;
	}

	// Retorna la posició actual del jugador al taulell
	public int getPosicion() {
		return posicion;
	}

	// Assigna directament una nova posició al jugador (usat per caselles especials)
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	// Retorna el nom del jugador
	public String getNombre() {
		return nombre;
	}

	// Assigna un nou nom al jugador
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// Retorna el color de la fitxa del jugador
	public String getColor() {
		return color;
	}

	// Assigna un nou color a la fitxa del jugador
	public void setColor(String color) {
		this.color = color;
	}

	// Retorna el nombre de torns que el jugador ha de saltar
	public int getTurnosPerdidos() {
		return turnosPerdidos;
	}

	// Assigna el nombre de torns perduts (usat per caselles de penalització)
	public void setTurnosPerdidos(int turnosPerdidos) {
		this.turnosPerdidos = turnosPerdidos;
	}

	// Mou el jugador endavant la quantitat indicada.
	// Si la nova posició és negativa, la fixa a 0 (no pot anar per sota del principi).
	public void moverPosicion(int cantidad) {
		this.posicion += cantidad;
		if (this.posicion < 0) {
			this.posicion = 0;
		}
	}

	// Mou el jugador enrere el nombre de passos indicat.
	// Si la nova posició és negativa, la fixa a 0.
	public void retroceder(int pasos) {
		this.posicion -= pasos;
		if (this.posicion < 0) {
			this.posicion = 0;
		}
	}

	// Retorna l'inventari del jugador (dados, boles de neu, peixos)
	public Inventario getInventario() {
		return inventario;
	}
}