package Modelos;

// Representa la CPU antagonista de la partida (disponible en mode 4 jugadors + CPU).
// La Foca es mou automàticament cada torn i pot atacar els pinguins que no tinguin peix.
// Pot ser bloquejada durant N torns si un pingui li dona un peix.
// Hereta de Jugador.
public class Foca extends Jugador {

	// Indica si la foca està bloquejada (true) o pot actuar (false)
	// S'activa quan un pingui li dona un peix; es desactiva quan turnosBloqueada arriba a 0
	private boolean bloqueada = false;

	// Nombre de torns que li queden a la foca bloquejada sense poder actuar.
	// Cada torn que passa es decrementa en 1. Quan arriba a 0, la foca es desbloqueja.
	private int turnosBloqueada = 0;

	// Constructor sense torns perduts: usat en crear una partida nova
	public Foca(int posicion, String nombre, String color, Inventario inventario) {
		super(posicion, nombre, color, inventario);
	}

	// Constructor amb torns perduts inicials: usat en carregar una partida de la BBDD
	public Foca(int posicion, String nombre, String color, Inventario inventario, int turnosPerdidos) {
		super(posicion, nombre, color, inventario, turnosPerdidos);
	}

	// Retorna true si el flag de bloqueig està actiu (pot ser redundant amb estaBloqueada)
	public boolean isBloqueada() {
		return bloqueada;
	}

	// Retorna el nombre exacte de torns que li queden de bloqueig a la foca
	public int getTurnosBloqueada() {
		return turnosBloqueada;
	}

	// Retorna true si la foca té almenys un torn de bloqueig pendent
	public boolean estaBloqueada() {
		return turnosBloqueada > 0;
	}

	// Bloqueja la foca durant el nombre de torns indicat (acumulatiu).
	// S'activa quan un pingui li dona un peix com a suborn.
	public void bloquear(int turnos) {
		if (turnos > 0) {
			this.bloqueada = true;
			this.turnosBloqueada += turnos; // s'acumula si ja estava bloquejada
		}
	}

	// Decrementa en 1 el comptador de bloqueig de la foca.
	// Quan el comptador arriba a 0, desactiva el flag de bloqueig.
	// S'executa al principi del torn de la foca si està bloquejada.
	public void reducirBloqueo() {
		if (turnosBloqueada > 0) {
			turnosBloqueada--;
		}
		if (turnosBloqueada == 0) {
			bloqueada = false; // la foca es desbloqueja quan acaba el compte enrere
		}
	}

	// Ataca un pingui fent-li perdre la meitat de l'inventari.
	// S'activa quan la foca coincideix amb un pingui que no té peix.
	public void atacarJugador(Pinguino p) {
		p.getInventario().perderMitadInventario();
	}

	// Representació textual de la foca: nom, color, posició i torns de bloqueig restants
	@Override
	public String toString() {
		return "Foca " + getNombre() +
				" (" + getColor() + ") - Posición: " +
				getPosicion() + " - Bloqueada: " + turnosBloqueada;
	}
}