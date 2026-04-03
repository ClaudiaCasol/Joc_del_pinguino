package Controladores;

import java.sql.Connection;
import java.util.ArrayList;

import Modelos.*;

public class GestorBBDD {

	private static BBDD bbdd;
	private static String urlBBDD;
	private static String username;
	private static String password;
	
	public static Connection con;


	public static void guardar(Partida t1) {
		con = BBDD.conectarBaseDatos();


		int turnos_tablero = t1.getTurnos();

		Jugador jugador_actual = t1.getJugadorActual();

		ArrayList<Casilla> casillas = t1.getTablero().getCasillas();

		/*int n_peces = t1.getJugador(0).getInventario().getPez().size();

		int n_bolas = t1.getJugador(0).getInventario().getBolas().size();

		int n_dadoR = 0;
		int n_dadoL = 0;

		for (Dado dado : t1.getJugador(0).getInventario().getDado()) {

			if (dado instanceof DadoRapido) {

				n_dadoR++;

			} else if (dado instanceof DadoLento) {

				n_dadoL++;

			}
		}*/
		ArrayList<Jugador> jugadores = t1.getJugadores();
		
		int posActual = 0;
		
		for(int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i) == t1.getJugadorActual()) {
				posActual = i;
			}
		}
		
		ArrayList<String> casillasBBDD = new ArrayList<>();
		for(int i = 0; i < casillas.size(); i++) {
			if(casillas.get(i) instanceof Agujero) {
				casillasBBDD.add("Agujero");
			}
			else if(casillas.get(i) instanceof Interrogante) {
				casillasBBDD.add("Interrogante");
			}
			else if(casillas.get(i) instanceof SueloQuebradizo) {
				casillasBBDD.add("SueloQuebradizo");
			}
			else if(casillas.get(i) instanceof Trineo) {
				casillasBBDD.add("Trineo");
			}
			else if(casillas.get(i) instanceof Casilla) {
				casillasBBDD.add("Casilla");
			}
			else if(casillas.get(i) instanceof Oso) {
				casillasBBDD.add("Oso");
			}
		}
		String varray = "";
		for(int i = 0; i < casillasBBDD.size() - 1; i++) {
			varray+= " '" + casillasBBDD.get(i) + "',";
		}
		varray += " '" + casillasBBDD.get(49) + "'";
		String sql = "INSERT INTO PARTIDA VALUES(seq_tablero.NEXTVAL, "+turnos_tablero + ", " + turnos_tablero + ", ARRAY_CASILLAS(" + varray + "), SYSDATE, 1)";
		System.out.println(sql);

		BBDD.print(con, "SELECT COUNT(*) AS TOTAL FROM TABLERO",
				new String[]{"TOTAL"});

		BBDD.insert(con, sql);

	}

	public Partida cargarBBDD(int id) {
		// TODO: implementar carga desde base de datos
		return null;
	}
}