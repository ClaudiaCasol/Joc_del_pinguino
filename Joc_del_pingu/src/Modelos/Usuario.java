package Modelos;

// Representa el perfil persistent d'un jugador humà a la base de dades Oracle.
// Conté les credencials d'accés i les estadístiques globals de partides.
// Cada Pinguino té associat un Usuario per vincular els resultats a la BBDD.
public class Usuario {

	// Nom d'usuari únic per identificar el jugador a la BBDD (clau de cerca)
	private String nombre;

	// Contrasenya d'accés del jugador (s'envia directament a la funció Oracle EXISTE())
	private String contraseña;

	// Nombre total de partides que l'usuari ha jugat (s'incrementa en iniciar una partida)
	private int numPartidasJugadas;

	// Nombre total de partides que l'usuari ha guanyat (s'incrementa en guanyar)
	private int numPartidasGanadas;

	// Constructor buit: necessari per reconstruir objectes des de la BBDD (sense paràmetres)
	public Usuario() {
	}

	// Constructor amb credencials: usat en el login i en crear un usuari nou
	public Usuario(String nombre, String contraseña) {
		this.nombre = nombre;
		this.contraseña = contraseña;
	}

	// Retorna el nom d'usuari
	public String getNombre() {
		return nombre;
	}

	// Assigna un nou nom d'usuari (usat en reconstruir des de la BBDD)
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// Retorna la contrasenya de l'usuari
	public String getContraseña() {
		return contraseña;
	}

	// Retorna el nombre de partides jugades per aquest usuari
	public int getNumPartidasJugadas() {
		return numPartidasJugadas;
	}

	// Assigna el nombre de partides jugades (usat en reconstruir des de la BBDD)
	public void setNumPartidasJugadas(int numPartidasJugadas) {
		this.numPartidasJugadas = numPartidasJugadas;
	}

	// Retorna el nombre de partides guanyades per aquest usuari
	public int getNumPartidasGanadas() {
		return numPartidasGanadas;
	}

	// Assigna el nombre de partides guanyades (usat en reconstruir des de la BBDD)
	public void setNumPartidasGanadas(int numPartidasGanadas) {
		this.numPartidasGanadas = numPartidasGanadas;
	}
}