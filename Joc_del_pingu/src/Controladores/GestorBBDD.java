package Controladores;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
		String sql = "INSERT INTO PARTIDA VALUES(seq_tablero.NEXTVAL, "+ turnos_tablero + ", " + turnos_tablero + ", casillas_varray (" + varray + "), SYSDATE, 1)";
		System.out.println(sql);

		BBDD.print(con, "SELECT COUNT(*) AS TOTAL FROM PARTIDA",
				new String[]{"TOTAL"});

		BBDD.insert(con, sql);

		//Hacemos el insert de jugador
		for(int i = 0; i < t1.getJugadores().size(); i++) {
			if(t1.getJugadores().get(i) instanceof Foca) {
				String sqlJugador = "INSERT INTO JUGADOR VALUES(seq_jugador.NEXTVAL, '" + t1.getJugadores().get(i).getNombre() + "', '"
						+ t1.getJugadores().get(i).getColor() + "', 'SI', " + t1.getJugadores().get(i).getPartidasJugadas() + ", " 
						+ t1.getJugadores().get(i).getPosicion() + ", " + t1.getJugadores().get(i).getTurnosPerdidos() +
						", seq_tablero.CURRVAL)";

				BBDD.print(con, "SELECT COUNT(*) AS TOTAL FROM JUGADOR",
						new String[]{"TOTAL"});

				BBDD.insert(con, sqlJugador);

				//Hacemos el insert de inventario a la vez que el de jugador
				int n_peces = t1.getJugador(i).getInventario().getPez().size();

				int n_bolas = t1.getJugador(i).getInventario().getBolaNieve().size();

				int n_dadoR = 0;
				int n_dadoL = 0;
				int n_dadoN = 0;

				for (Dado dado : t1.getJugador(i).getInventario().getDado()) {

					if (dado instanceof Dado_rapido) {
						n_dadoR++;

					} else if (dado instanceof Dado_lento) {
						n_dadoL++;

					} else {
						n_dadoN++;
					}
				}
				String sqlInventario = "INSERT INTO INVENTARIO VALUES(seq_inventario.NEXTVAL, "+ n_peces + ", "+ n_bolas + ", " + n_dadoN + ", " + n_dadoR +
						", "+ n_dadoL +", seq_jugador.CURRVAL)";

				BBDD.print(con, "SELECT COUNT(*) AS TOTAL FROM INVENTARIO",
						new String[]{"TOTAL"});

				BBDD.insert(con, sqlInventario);
			}
			else {
				String sqlJugador = "INSERT INTO JUGADOR VALUES(seq_jugador.NEXTVAL, '" + t1.getJugadores().get(i).getNombre() + "', '"
						+ t1.getJugadores().get(i).getColor() + "', 'SI', " + t1.getJugadores().get(i).getPartidasJugadas() + ", " 
						+ t1.getJugadores().get(i).getPosicion() + ", " + t1.getJugadores().get(i).getTurnosPerdidos() +
						", seq_tablero.CURRVAL)";

				BBDD.print(con, "SELECT COUNT(*) AS TOTAL FROM JUGADOR",
						new String[]{"TOTAL"});

				BBDD.insert(con, sqlJugador);

				//Hacemos el insert de inventario a la vez que el de jugador
				int n_peces = t1.getJugador(i).getInventario().getPez().size();

				int n_bolas = t1.getJugador(i).getInventario().getBolaNieve().size();

				int n_dadoR = 0;
				int n_dadoL = 0;
				int n_dadoN = 0;

				for (Dado dado : t1.getJugador(i).getInventario().getDado()) {

					if (dado instanceof Dado_rapido) {
						n_dadoR++;

					} else if (dado instanceof Dado_lento) {
						n_dadoL++;

					} else {
						n_dadoN++;
					}
				}

				String sqlInventario = "INSERT INTO INVENTARIO VALUES(seq_inventario.NEXTVAL, "+ n_peces + ", "+ n_bolas + ", " + n_dadoN + ", " + n_dadoR +
						", "+ n_dadoL +", seq_jugador.CURRVAL)";

				BBDD.print(con, "SELECT COUNT(*) AS TOTAL FROM INVENTARIO",
						new String[]{"TOTAL"});

				BBDD.insert(con, sqlInventario);
			}
		}

	}

	public Partida cargarTablero(int indice) {
		con = BBDD.conectarBaseDatos();
		
		//HACEMOS EL SELECT QUE NECESITAMOS DE LA TABLA TABLERO
		ArrayList<LinkedHashMap<String, String>> partida = //se guarda en un arraylist hashmap por si el select te devuelve más de un resultado.
				
				BBDD.select(con, "SELECT ID_PARTIDA, TURNOS, JUGADOR_ACTUAL, FECHA_PARTIDA FROM PARTIDA WHERE ID_TABLERO = " + indice);
		
		LinkedHashMap<String, String> fila = partida.get(0); //sirve para estructurar en una tabla el resultado de un select.

		//con el integer.parseint pasamos los Strings a int.
		int idPartida = Integer.parseInt(fila.get("ID_PARTIDA")); //se coge el resultado de la fila enlazado a idPartida.
		int turnos = Integer.parseInt(fila.get("TURNOS"));
		int jugadorActual = Integer.parseInt(fila.get("JUGADOR_ACTUAL"));
		String fecha = fila.get("FECHA_PARTIDA");
		ArrayList <Casilla> casilla = new ArrayList <>();
		
		
		try (Statement st = con.createStatement(); //el statement y resultset son librerias sql.
				ResultSet rs = st.executeQuery("SELECT CASILLAS FROM PARTIDA WHERE ID_PARTIDA = " + indice)) { //devuelve el resultado de la query de los selects.

			if (rs.next()) { //si existe entra en el if.
				Array array = rs.getArray("CASILLAS"); //guardas en el array de librerias sql un array.
				ArrayList<String> casillas = new ArrayList<>();
				Object[] casillaSQL = (Object[]) array.getArray();

				for (Object c : casillaSQL) { //cada objeto lo pasa a string.
					String tipo = c.toString(); //con el toString indicamos que se ha guardado un String.
					casillas.add(tipo);
				}
				for (int i = 0; i < casillas.size(); i++) {
					if(casillas.get(i).equals("Casilla")) {
						casilla.add(new Casilla(i));
						
					} else if(casillas.get(i).equals("Interrogante")) {
						casilla.add(new Interrogante(i));
						
					} else if(casillas.get(i).equals("SueloQuebradizo")) {
						casilla.add(new SueloQuebradizo(i));
						
					} else if(casillas.get(i).equals("Trineo")) {
						casilla.add(new Trineo(i));
						
					} else if(casillas.get(i).equals("Oso")) {
						casilla.add(new Oso(i));
						
					} else if(casillas.get(i).equals("Agujero")) {
						casilla.add(new Agujero(i));
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//TERMINAMOS EL SELECT DE TABLERO
		//Empezamos el SELECT de jugadores
		ArrayList<Jugador>jugadores = new ArrayList<>();
		
		ArrayList<LinkedHashMap<String, String>> jugador =
				BBDD.select(con, "SELECT * FROM JUGADOR WHERE ID_PARTIDA = " + indice + " ORDER BY TURNO ASC");
		
		for(LinkedHashMap<String, String> tabla : jugador) { 
			
			int idJugador = Integer.parseInt(tabla.get("ID_JUGADOR"));
			
			ArrayList<LinkedHashMap<String, String>> inventario =
					BBDD.select(con, "SELECT NUM_PECES, NUM_BOLAS, NUM_DADOR, NUM_DADOL FROM INVENTARIO WHERE ID_JUGADOR = " + idJugador);
			
			//Realizamos para cada jugador un SELECT de su inventario
			LinkedHashMap<String, String> Objeto = inventario.get(0);

			int num_peces = Integer.parseInt(Objeto.get("NUM_PECES"));
			ArrayList<Pez> peces = new ArrayList<>();
			for(int i = 0; i < num_peces; i++) {
				peces.add(new Pez());
			}
			
			int num_bolas = Integer.parseInt(Objeto.get("NUM_BOLAS"));
			ArrayList<BolaNieve> bolas = new ArrayList<>();
			for(int i = 0; i < num_bolas; i++) {
				bolas.add(new BolaNieve());
			}
			
			int num_dador = Integer.parseInt(Objeto.get("NUM_DADOR"));
			ArrayList<Dado> dados = new ArrayList<>();
			for(int i = 0; i < num_dador; i++) {
				dados.add(new Dado_rapido());
			}
			
			int num_dadoL = Integer.parseInt(Objeto.get("NUM_DADOL"));
			for(int i = 0; i < num_dadoL; i++) {
				dados.add(new Dado_lento());
			}
			
			Inventario inventarios = new Inventario(dados, bolas, peces);
			
			//terminamos el SELECT de inventario
			String foca = tabla.get("FOCA");
			if(foca.equals("SI")) {
				
				int posicion = Integer.parseInt(tabla.get("POSICION"));
				int turno = Integer.parseInt(tabla.get("TURNO"));
				
				String nombre = tabla.get("NOMBRE");
				String color = tabla.get("COLOR");
				
				int turnosPerdidos = Integer.parseInt(tabla.get("TURNOSPERDIDOS"));
				int partidasJugadas = Integer.parseInt(tabla.get("PARTIDASJUGADAS"));
				
				Foca nuevo = new Foca(posicion, nombre, color, inventarios, turnosPerdidos, partidasJugadas, turno);
				jugadores.add(nuevo);

			} else {
				
				int posicion = Integer.parseInt(tabla.get("POSICION"));
				int turno = Integer.parseInt(tabla.get("TURNO"));
				
				String nombre = tabla.get("NOMBRE");
				String color = tabla.get("COLOR");
				
				int turnoPerdido = Integer.parseInt(tabla.get("TURNOSPERDIDOS"));
				int partidasJugadas = Integer.parseInt(tabla.get("PARTIDASJUGADAS"));
				
				Pinguino nuevo = new Pinguino(posicion, nombre, color, inventarios, turnoPerdido, partidasJugadas, turno);
				jugadores.add(nuevo);
			}

		}
		
		//Terminamos el SELECT de Jugador; Ya tenemos el ArrayList DE Jugadores con sus respectivos inventarios

		Tablero tablero = new Tablero(casilla);
		Partida partidaN = new Partida(jugadores, tablero, fecha, turnos, jugadorActual);
		return partidaN;
	}

	public Partida cargarBBDD(int id) {
		// TODO: implementar carga desde base de datos
		return null;
	}
}