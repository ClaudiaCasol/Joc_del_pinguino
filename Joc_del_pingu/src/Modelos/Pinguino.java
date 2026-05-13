package Modelos;

// Representa un jugador humà a la partida.
// Hereta de Jugador i afegeix la referència a l'usuari registrat a la BBDD
// i els mètodes per gestionar l'inventari de forma tipada.
public class Pinguino extends Jugador {

	// Referència a l'usuari registrat (nom + contrasenya + estadístiques globals)
	// Permet vincular els resultats de la partida al perfil persistent de la BBDD
	private Usuario usuario;

	// Constructor sense torns perduts inicials: usat en crear una partida nova
	public Pinguino(int posicion, String nombre, String color, Inventario inventario, Usuario usuario) {
		super(posicion, nombre, color, inventario);
		this.usuario = usuario;
	}

	// Constructor amb torns perduts inicials: usat en carregar una partida de la BBDD
	public Pinguino(int posicion, String nombre, String color, Inventario inventario, int turnosPerdidos, Usuario usuario) {
		super(posicion, nombre, color, inventario, turnosPerdidos);
		this.usuario = usuario;
	}

	// Reservat per implementar la lògica de batalla entre dos pinguins (pendent)
	public void gestionarBatalla(Pinguino p2) {
		// TODO: implementar quan es desenvolupi la guerra de boles de neu en detall
	}

	// Afegeix un objecte a l'inventari del pingui de forma polimòrfica.
	// Detecta el tipus real de l'objecte i crida el mètode corresponent de l'inventari.
	public void anadirItem(Objetos item) {
		if (item instanceof BolaNieve) {
			getInventario().agregarBolaNieve((BolaNieve) item);
		} else if (item instanceof Dado) {
			getInventario().agregarDado((Dado) item);
		} else if (item instanceof Pez) {
			getInventario().agregarPez((Pez) item);
		}
	}

	// Elimina un objecte de l'inventari del pingui de forma polimòrfica.
	// Detecta el tipus real de l'objecte i crida el mètode d'eliminació corresponent.
	public void eliminarItem(Objetos item) {
		if (item instanceof BolaNieve) {
			getInventario().eliminarBolaNieve((BolaNieve) item);
		} else if (item instanceof Dado) {
			getInventario().eliminarDado((Dado) item);
		} else if (item instanceof Pez) {
			getInventario().eliminarPez((Pez) item);
		}
	}

	// Representació textual del pingui: nom, color i posició actual
	@Override
	public String toString() {
		return "Pinguino " + getNombre() +
			   " (" + getColor() + ") - Posición: " +
			   getPosicion();
	}

	// Retorna l'objecte Usuario associat a aquest pingui
	public Usuario getUsuario() {
		return this.usuario;
	}
}