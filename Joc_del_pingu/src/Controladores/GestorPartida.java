package Controladores;

import java.util.ArrayList;
import java.util.Random;

import Modelos.Dado;
import Modelos.Foca;
import Modelos.Inventario;
import Modelos.Jugador;
import Modelos.Partida;
import Modelos.Pinguino;
import Modelos.Tablero;
import Modelos.Usuario;

/**
 * Classe controladora que gestiona l'inici i el flux d'una partida.
 * S'encarrega de crear jugadors, inicialitzar el tauler i tirar els daus.
 */
public class GestorPartida {

	/** Partida actual en curs */
	private Partida partida;

	/** Gestor del tauler associat a la partida */
	private GestorTablero gestorTablero;

	/** Gestor dels jugadors associat a la partida */
	private GestorJugador gestorJugador;

	/** Generador de números aleatoris per als daus */
	private Random random;

	/**
	 * Constructor que inicialitza els gestors i el generador aleatori.
	 */
	public GestorPartida() {
		gestorTablero = new GestorTablero();
		gestorJugador = new GestorJugador();
		random = new Random();
		partida = null;
	}

	/**
	 * Retorna la partida actual en curs.
	 *
	 * @return Objecte Partida actual
	 */
	public Partida getPartida() {
		return partida;
	}

	/**
	 * Tira un dau per a un jugador.
	 * Si s'especifica un dau opcional de l'inventari, l'utilitza; si no, tira un dau estàndard (1-6).
	 *
	 * @param j            Jugador que tira el dau
	 * @param dadoOpcional Dau especial de l'inventari (pot ser null per usar el dau normal)
	 * @return Resultat del llançament del dau
	 */
	public int tirarDado(Jugador j, Dado dadoOpcional) {
		int resultado;

		if (dadoOpcional != null) {
			resultado = dadoOpcional.tirarDado();
		} else {
			resultado = random.nextInt(6) + 1;
		}

		return resultado;
	}

	/**
	 * Inicia una nova partida creant els jugadors i el tauler.
	 * Si hi ha 4 jugadors, s'afegeix automàticament una Foca com a CPU.
	 *
	 * @param numeroJugadores Nombre de jugadors humans (màxim 4)
	 * @param usuarios        Llista d'usuaris que participen a la partida
	 */
	public void iniciarPartida(int numeroJugadores, ArrayList<Usuario> usuarios) {

		ArrayList<Jugador> jugadores = new ArrayList<>();

		String[] nombres = { "Jugador1", "Jugador2", "Jugador3", "Jugador4" };
		String[] colores = { "Verde", "Azul", "Rosa", "Amarillo" };

		for (int i = 0; i < numeroJugadores; i++) {
			Inventario inventario = new Inventario(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
			Dado dado = new Dado();
			inventario.agregarDado(dado);

			jugadores.add(new Pinguino(-1, nombres[i], colores[i], inventario, usuarios.get(i)));
		}

		if (numeroJugadores == 4) {
			Inventario inventario = new Inventario(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
			Dado dado = new Dado();
			inventario.agregarDado(dado);
			jugadores.add(new Foca(-1, "FocaCPU", "Gris", inventario));
		}

		Tablero tablero = new Tablero();

		partida = new Partida(jugadores);
		partida.setTablero(tablero);
		partida.iniciarPartida();
	}

	/**
	 * Inicia una nova partida amb una llista de jugadors i un tauler ja creats.
	 *
	 * @param jugadores Llista de jugadors que participaran
	 * @param tablero   Tauler sobre el qual es jugarà
	 */
	public void nuevaPartida(ArrayList<Jugador> jugadores, Tablero tablero) {
		partida = new Partida(jugadores);
		partida.setTablero(tablero);
		partida.iniciarPartida();
	}

	/**
	 * Estableix la partida actual (per exemple, quan es carrega una partida guardada).
	 *
	 * @param partida Partida que es vol establir com a activa
	 */
	public void setPartida(Partida partida) {
		this.partida = partida;
	}
}