package Vistas;

import java.util.ArrayList;

import Controladores.GestorBBDD;
import Controladores.GestorPartida;
import Modelos.Agujero;
import Modelos.AudioManager;
import Modelos.Casilla;
import Modelos.Dado;
import Modelos.Dado_lento;
import Modelos.Dado_rapido;
import Modelos.Foca;
import Modelos.Interrogante;
import Modelos.Inventario;
import Modelos.Jugador;
import Modelos.Oso;
import Modelos.Partida;
import Modelos.Pinguino;
import Modelos.SueloQuebradizo;
import Modelos.Trineo;
import Modelos.Usuario;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controlador principal de la pantalla de joc.
 * Gestiona tota la interfície visual durant la partida: el tauler, les fitxes,
 * les animacions, els sons, els torns i els esdeveniments de les caselles.
 */
public class PantallaJuego {

	/** Botó per activar/desactivar el so */
	@FXML
	private Button btnSonido;

	/** GridPane que representa visualment el tauler de joc */
	@FXML
	private GridPane tablero;

	/** Panell de sortida on es mostren les fitxes que encara no han entrat al tauler */
	@FXML
	private StackPane sortidaPane;

	/** Botó desplegable per seleccionar el tipus de dau i tirar-lo */
	@FXML
	private SplitMenuButton dadoMenu;

	/** Botó per consultar l'inventari del jugador actual */
	@FXML
	private Button inventarioButton;

	/** Text que mostra l'últim esdeveniment de la partida */
	@FXML
	private Text eventos;

	/** Text que mostra el resultat de l'últim llançament del dau */
	@FXML
	private Text dadoResultText;

	/** Àrea de text que mostra l'historial complet d'esdeveniments de la partida */
	@FXML
	private TextArea consolaEventos;

	/** Indica si el so està activat (true) o silenciat (false) */
	private boolean sonidoActivo = true;

	/** Gestor d'àudio per a música i efectes de so */
	private AudioManager audio = new AudioManager();

	/** Gestor de la partida que controla la lògica del joc */
	private GestorPartida gestorPartida;

	/** Nombre de jugadors humans de la partida */
	private int numeroJugadores = 2;

	/** Nombre de columnes del tauler visual */
	private static final int COLUMNS = 5;

	/** Llista de les cel·les visuals (StackPane) del tauler */
	private final ArrayList<StackPane> casillasVista = new ArrayList<>();

	/** Llista de les fitxes visuals (Circle) de cada jugador */
	private final ArrayList<Circle> fichasVista = new ArrayList<>();

	/** Tipus de dau seleccionat per al pròxim llançament ("normal", "rapido" o "lento") */
	private String tipoDadoSeleccionado = "normal";

	/** Tooltip que mostra el contingut de l'inventari del jugador actual */
	private Tooltip tooltipInventari = new Tooltip();

	/** Llista d'usuaris que han iniciat sessió per a la partida */
	private ArrayList<Usuario> usuarios = new ArrayList<>();

	/** Índex de la casella de sòl trencadís activa (per mostrar la imatge corresponent) */
	private int sueloQuebradizoActivo = -1;

	/** Última posició on s'ha animat una fitxa (per activar l'animació de bot) */
	private int ultimaPosicionAnimada = -1;

	/**
	 * Mètode d'inicialització cridat automàticament per JavaFX en carregar el FXML.
	 * Mostra un missatge de preparació a la consola d'esdeveniments.
	 */
	@FXML
	private void initialize() {
		afegirMissatge("Preparando partida...");
	}

	/**
	 * Configura el nombre de jugadors i la llista d'usuaris abans de preparar la pantalla.
	 *
	 * @param numeroJugadores Nombre de jugadors humans de la partida
	 * @param usuario         Llista d'usuaris que jugaran
	 */
	public void configurarPartida(int numeroJugadores, ArrayList<Usuario> usuario) {
		this.numeroJugadores = numeroJugadores;
		this.usuarios = usuario;
	}

	/**
	 * Prepara i inicialitza una nova partida: crea el gestor, registra les partides jugades
	 * a la BBDD, inicia la partida i configura els components visuals i l'àudio.
	 */
	public void prepararPantalla() {
		gestorPartida = new GestorPartida();
		for (Usuario u : usuarios) {
			GestorBBDD.sumarPartidaJugada(u.getNombre());
		}
		gestorPartida.iniciarPartida(numeroJugadores, usuarios);
		inicializarComponentesVisuales();
		audio.reproducirMusica("/audio/tablero.mp3");
		afegirMissatge("Partida iniciada con " + numeroJugadores + " jugadores.");
	}

	/**
	 * Carrega una partida guardada i inicialitza els components visuals i l'àudio.
	 *
	 * @param partida Partida a carregar (obtinguda des de la BBDD)
	 */
	public void cargarPartida(Partida partida) {
		gestorPartida = new GestorPartida();
		gestorPartida.setPartida(partida);
		inicializarComponentesVisuales();
		audio.reproducirMusica("/audio/tablero.mp3");
		afegirMissatge("Partida cargada correctamente.");
	}

	/**
	 * Inicialitza tots els components visuals de la partida:
	 * genera el tauler, crea les fitxes, actualitza posicions i tooltip de l'inventari.
	 */
	private void inicializarComponentesVisuales() {
		generarTableroVisual();
		crearFichas();
		actualizarPosicionesVisuales();
		actualizarTextoTurno();
		inventarioButton.setTooltip(tooltipInventari);
		actualizarTooltipInventario();
	}

	/**
	 * Gestiona el clic a "Nova Partida" del menú. Reinicia la pantalla.
	 *
	 * @param event Esdeveniment del menú
	 */
	@FXML
	private void handleNewGame(ActionEvent event) {
		prepararPantalla();
	}

	/**
	 * Gestiona el clic a "Guardar Partida" del menú. Desa l'estat actual a la BBDD.
	 *
	 * @param event Esdeveniment del menú
	 */
	@FXML
	private void handleSaveGame(ActionEvent event) {
		GestorBBDD.guardar(gestorPartida.getPartida());
	}

	/**
	 * Gestiona el clic a "Carregar Partida" del menú. Navega a la pantalla de càrrega.
	 *
	 * @param event Esdeveniment del menú
	 */
	@FXML
	private void handleLoadGame(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/CargarPartida.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) tablero.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setTitle("Cargar Partida");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gestiona el clic a "Sortir" del menú. Tanca l'aplicació.
	 *
	 * @param event Esdeveniment del menú
	 */
	@FXML
	private void handleQuitGame(ActionEvent event) {
		System.exit(0);
	}

	/**
	 * Activa o desactiva el so del joc. Actualitza el botó i el volum de l'àudio.
	 */
	@FXML
	private void toggleSonido() {
		sonidoActivo = !sonidoActivo;
		if (sonidoActivo) {
			btnSonido.setText("🔊");
			audio.setVolumen(1.0);
		} else {
			btnSonido.setText("🔇");
			audio.setVolumen(0.0);
		}
	}

	/**
	 * Gestiona el clic principal al botó de tirar el dau.
	 * Executa el torn, reprodueix el so del dau i, amb retard, el so de la casella.
	 *
	 * @param event Esdeveniment del botó del dau
	 */
	@FXML
	private void handleDadoNormal(ActionEvent event) {
		Pinguino p = (Pinguino) gestorPartida.getPartida().getJugadorActual();
		jugarTurno();
		audio.reproducirEfecto("/audio/dados.mp3");

		int pos = p.getPosicion();
		if (pos < 0) {
			return;
		}

		Casilla casilla = gestorPartida.getPartida().getTablero().getCasillas().get(pos);
		PauseTransition pausa = new PauseTransition(Duration.seconds(1.2));
		pausa.setOnFinished(e -> reproducirSonidoCasilla(casilla));
		pausa.play();
	}

	/**
	 * Reprodueix l'efecte de so corresponent al tipus de casella on ha caigut el jugador.
	 *
	 * @param casilla Casella on ha caigut el jugador
	 */
	private void reproducirSonidoCasilla(Casilla casilla) {
		if (casilla instanceof Oso) {
			audio.reproducirEfecto("/audio/oso.mp3");
		} else if (casilla instanceof Agujero) {
			audio.reproducirEfecto("/audio/agujero.mp3");
		} else if (casilla instanceof Trineo) {
			audio.reproducirEfecto("/audio/trineo.mp3");
		} else if (casilla instanceof SueloQuebradizo) {
			audio.reproducirEfecto("/audio/sueloQuebradizo.mp3");
		} else if (casilla instanceof Interrogante) {
			audio.reproducirEfecto("/audio/exclamacion.mp3");
		}
	}

	/**
	 * Selecciona el dau normal per al pròxim llançament i actualitza el text del botó.
	 */
	@FXML
	private void seleccionarDadoNormal() {
		tipoDadoSeleccionado = "normal";
		dadoMenu.setText("Tirar dado");
	}

	/**
	 * Selecciona el dau ràpid per al pròxim llançament i actualitza el text del botó.
	 */
	@FXML
	private void seleccionarDadoRapido() {
		tipoDadoSeleccionado = "rapido";
		dadoMenu.setText("Tirar rápido");
	}

	/**
	 * Selecciona el dau lent per al pròxim llançament i actualitza el text del botó.
	 */
	@FXML
	private void seleccionarDadoLento() {
		tipoDadoSeleccionado = "lento";
		dadoMenu.setText("Tirar lento");
	}

	/**
	 * Executa el torn del jugador actual i, si el següent jugador és la Foca (CPU),
	 * executa automàticament el seu torn també.
	 */
	private void jugarTurno() {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			return;
		}
		Partida partida = gestorPartida.getPartida();
		if (partida.estaFinalizada()) {
			return;
		}

		ejecutarUnTurno();
		while (!partida.estaFinalizada() && partida.getJugadorActual() instanceof Foca) {
			ejecutarUnTurno();
		}
	}

	/**
	 * Executa un únic torn: determina el dau, tira el dau, calcula la posició final
	 * i llança l'animació de moviment casella a casella.
	 */
	private void ejecutarUnTurno() {
		Partida partida = gestorPartida.getPartida();
		Jugador jugador = partida.getJugadorActual();
		Dado dadoElegido = (jugador instanceof Foca) ? null : buscarDadoSegunTipo(jugador, tipoDadoSeleccionado);

		int resultado = gestorPartida.tirarDado(jugador, dadoElegido);
		dadoResultText.setText("Ha salido: " + resultado);
		afegirMissatge(jugador.getNombre() + " ha tret un " + resultado);

		int antes = jugador.getPosicion();

		int despues = calcularPosFinal(jugador, resultado, partida);

		animarMovimentCasellaACasella(jugador, antes, despues, dadoElegido, partida);
	}

	/**
	 * Actualitza la interfície gràfica després d'un torn: refresca el tauler,
	 * les posicions, el tooltip de l'inventari i anima la casella de destinació.
	 *
	 * @param jugador    Jugador que acaba de jugar
	 * @param antes      Posició abans del moviment
	 * @param despues    Posició final després del moviment
	 * @param dadoElegido Dau que s'ha utilitzat
	 */
	private void actualizarIUTrasTurno(Jugador jugador, int antes, int despues, Dado dadoElegido) {
		String msg = (dadoElegido instanceof Dado_rapido) ? " usa dado rápido"
				: (dadoElegido instanceof Dado_lento) ? " usa dado lento" : "";
		afegirMissatge(jugador.getNombre() + msg + " pasa de " + (antes + 1) + " a " + (despues + 1));

		generarTableroVisual();
		actualizarPosicionesVisuales();
		actualizarTooltipInventario();

		if (despues >= 0 && despues < casillasVista.size()) {
			animarCasillaDestino(casillasVista.get(despues));
		}

		verificarGanador();
	}

	/**
	 * Genera i omple el tauler visual amb les cel·les corresponents a cada casella de la partida.
	 * Les caselles es col·loquen seguint el patró de serp (esquerra-dreta, dreta-esquerra alternant files).
	 */
	private void generarTableroVisual() {
		tablero.getChildren().clear();
		casillasVista.clear();
		ArrayList<Casilla> casillas = gestorPartida.getPartida().getTablero().getCasillas();

		for (int i = 0; i < casillas.size(); i++) {
			StackPane celda = crearCeldaVisual(casillas.get(i), i);
			int[] coord = convertirIndiceASerp(i);
			tablero.add(celda, coord[1], coord[0]);
			casillasVista.add(celda);
		}
	}

	/**
	 * Crea la representació visual d'una casella amb el seu estil, número i imatge si n'hi ha.
	 * Afegeix efectes d'hover (ressaltat de vora en passar el ratolí).
	 *
	 * @param casilla Casella del model a representar
	 * @param indice  Índex de la casella al tauler (0-based)
	 * @return StackPane amb la representació visual de la casella
	 */
	private StackPane crearCeldaVisual(Casilla casilla, int indice) {
		StackPane celda = new StackPane();
		celda.setPrefSize(140, 78);
		String estiloBase = estiloCasilla(casilla);
		celda.setStyle(estiloBase);

		Text numero = new Text(String.valueOf(indice + 1));
		numero.setStyle("-fx-font-size: 10; -fx-fill: #2c3e50; -fx-font-weight: bold;");
		StackPane.setAlignment(numero, Pos.TOP_RIGHT);
		numero.setTranslateX(-5);
		numero.setTranslateY(5);

		ImageView img = null;
		if (casilla instanceof Oso) {
			img = cargarImagen("/imatges/oso.png", 40);
		} else if (casilla instanceof Trineo) {
			img = cargarImagen("/imatges/trineo.png", 40);
		} else if (casilla instanceof Interrogante) {
			img = cargarImagen("/imatges/interrogante.png", 35);
		} else if (casilla instanceof SueloQuebradizo && indice == sueloQuebradizoActivo) {
			img = cargarImagen("/imatges/hielo-roto.png", 40);
		}

		celda.getChildren().add(numero);
		if (img != null) {
			celda.getChildren().add(img);
		}

		celda.setOnMouseEntered(e -> celda.setStyle(estiloBase + "-fx-border-color: #0b3d91; -fx-border-width: 2;"));
		celda.setOnMouseExited(e -> celda.setStyle(estiloBase));

		return celda;
	}

	/**
	 * Crea les fitxes visuals (cercles amb imatge) per a cada jugador de la partida.
	 * Assigna una imatge diferent per a cada pingüí i la foca.
	 */
	private void crearFichas() {
		fichasVista.clear();
		ArrayList<Jugador> jugadores = gestorPartida.getPartida().getJugadores();
		for (int i = 0; i < jugadores.size(); i++) {
			Circle ficha = new Circle(11);
			ficha.setStroke(Color.web("#2c3e50"));
			ficha.setStrokeWidth(1.5);
			String ruta = switch (i) {
			case 0 -> "/imatges/skipper.jpg";
			case 1 -> "/imatges/kowalski.jpg";
			case 2 -> "/imatges/rico.jpg";
			case 3 -> "/imatges/private.jpg";
			default -> "/imatges/focaPingu.jpg";
			};
			Image img = new Image(getClass().getResource(ruta).toExternalForm());
			ficha.setFill(new ImagePattern(img, 0, 0, 1, 1, true));
			ficha.setStyle("-fx-effect: dropshadow(gaussian, black, 6, 0.5, 0, 2);");
			fichasVista.add(ficha);
		}
	}

	/**
	 * Actualitza la posició visual de totes les fitxes al tauler.
	 * El jugador amb el torn actiu rep un ressaltat daurat a la vora de la fitxa.
	 * Els jugadors que encara no han entrat al tauler es mostren al panell de sortida.
	 */
	private void actualizarPosicionesVisuales() {
		for (StackPane celda : casillasVista) {
			celda.getChildren().removeIf(n -> n instanceof Circle);
		}
		sortidaPane.getChildren().clear();
		GridPane mini = new GridPane();
		mini.setHgap(6);
		mini.setVgap(6);
		mini.setAlignment(Pos.CENTER);

		ArrayList<Jugador> jugadores = gestorPartida.getPartida().getJugadores();
		Jugador actual = gestorPartida.getPartida().getJugadorActual();

		for (int i = 0; i < jugadores.size(); i++) {
			Jugador j = jugadores.get(i);
			Circle ficha = fichasVista.get(i);

			if (j == actual) {
				ficha.setStroke(Color.web("#FFD700"));
				ficha.setStrokeWidth(2.5);
			} else {
				ficha.setStroke(Color.web("#2c3e50"));
				ficha.setStrokeWidth(1.5);
			}

			if (j.getPosicion() < 0) {
				mini.add(ficha, i % 2, i / 2);
			} else {
				StackPane celda = casillasVista.get(j.getPosicion());
				double[][] posOffsets = { { -10, -10 }, { 10, -10 }, { -10, 10 }, { 10, 10 } };
				ficha.setTranslateX(posOffsets[i % 4][0]);
				ficha.setTranslateY(posOffsets[i % 4][1]);
				celda.getChildren().add(ficha);
				if (j.getPosicion() == ultimaPosicionAnimada) {
					animarFicha(ficha);
				}
			}
		}
		sortidaPane.getChildren().add(mini);
	}

	/**
	 * Retorna l'estil CSS d'una casella en funció del seu tipus.
	 * Cada tipus de casella té un color de fons diferent.
	 *
	 * @param casilla Casella de la qual es vol obtenir l'estil
	 * @return String amb l'estil CSS de la casella
	 */
	private String estiloCasilla(Casilla casilla) {
		String base = "-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: rgba(60,60,60,0.35); -fx-border-width: 1;";
		if (casilla instanceof Oso) {
			return "-fx-background-color: #e07a5f; " + base;
		}
		if (casilla instanceof Trineo) {
			return "-fx-background-color: #ffd166; " + base;
		}
		if (casilla instanceof Agujero) {
			return "-fx-background-color: #5dade2; " + base;
		}
		if (casilla instanceof Interrogante) {
			return "-fx-background-color: #cdb4db; " + base;
		}
		if (casilla instanceof SueloQuebradizo) {
			return "-fx-background-color: #d6f0ff; " + base;
		}
		return "-fx-background-color: #f5fdff; " + base;
	}

	/**
	 * Converteix un índex lineal de casella a coordenades de fila i columna
	 * seguint el patró de serp (files parells d'esquerra a dreta, imparells al revés).
	 *
	 * @param indice Índex de la casella (0-based)
	 * @return Array de 2 elements: [fila, columna]
	 */
	private int[] convertirIndiceASerp(int indice) {
		int fila = indice / COLUMNS;
		int col = indice % COLUMNS;
		if (fila % 2 == 1) {
			col = COLUMNS - 1 - col;
		}
		return new int[] { fila, col };
	}

	/**
	 * Carrega una imatge des d'una ruta relativa i retorna un ImageView amb la mida indicada.
	 *
	 * @param path Ruta relativa del recurs d'imatge
	 * @param size Mida en píxels (amplada i alçada iguals)
	 * @return ImageView amb la imatge carregada i redimensionada
	 */
	private ImageView cargarImagen(String path, double size) {
		Image img = new Image(getClass().getResourceAsStream(path));
		ImageView iv = new ImageView(img);
		iv.setFitWidth(size);
		iv.setFitHeight(size);
		return iv;
	}

	/**
	 * Afegeix un missatge a la consola d'esdeveniments i al text d'últim event.
	 *
	 * @param text Missatge a mostrar
	 */
	private void afegirMissatge(String text) {
		if (eventos != null) {
			eventos.setText(text);
		}
		if (consolaEventos != null) {
			consolaEventos.appendText(text + "\n");
		}
	}

	/**
	 * Comprova si la partida ha finalitzat i, si és així, mostra el missatge de victòria,
	 * desactiva el dau i registra la victòria a la BBDD si el guanyador és un pingüí.
	 */
	private void verificarGanador() {
		Partida partida = gestorPartida.getPartida();
		if (partida.estaFinalizada()) {
			afegirMissatge("Ha ganado " + partida.getGanador().getNombre());
			dadoMenu.setDisable(true);
			if (partida.getGanador() instanceof Pinguino) {
				GestorBBDD.sumarPartidaGanada(((Pinguino) partida.getGanador()).getUsuario().getNombre());
			}
		}
	}

	/**
	 * Actualitza el text del torn actual mostrant el nom del jugador que ha de jugar.
	 * Si és la Foca CPU, mostra un missatge específic.
	 */
	private void actualizarTextoTurno() {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			return;
		}

		Jugador actual = gestorPartida.getPartida().getJugadorActual();
		if (actual instanceof Foca) {
			afegirMissatge("❄️ Turno de la Foca CPU");
		} else {
			afegirMissatge("🐧 Turno de " + actual.getNombre());
		}
	}

	/**
	 * Calcula la posició final d'un jugador tenint en compte els límits del tauler.
	 * Si sobrepassaria l'última posició, s'ajusta a ella.
	 *
	 * @param jugador Jugador que es mou
	 * @param pasos   Nombre de caselles a avançar
	 * @param partida Partida en curs
	 * @return Posició final calculada
	 */
	private int calcularPosFinal(Jugador jugador, int pasos, Partida partida) {
		int posActual = jugador.getPosicion() < 0 ? -1 : jugador.getPosicion();
		int ultimaPos = partida.getTablero().getTamano() - 1;
		int novaPos = posActual + pasos;

		if (novaPos > ultimaPos) {
			novaPos = ultimaPos;
		}

		if (novaPos < -1) {
			novaPos = -1;
		}
		return novaPos;
	}

	/**
	 * Anima el moviment d'un jugador casella a casella fins a la posició de destinació.
	 * Un cop finalitzada l'animació, aplica l'efecte de la casella amb retard.
	 *
	 * @param jugador       Jugador que es mou
	 * @param posOrigen     Posició d'origen
	 * @param posDestiFinal Posició final de destinació
	 * @param dadoElegido   Dau que s'ha utilitzat
	 * @param partida       Partida en curs
	 */
	private void animarMovimentCasellaACasella(Jugador jugador, int posOrigen, int posDestiFinal, Dado dadoElegido,
			Partida partida) {
		ArrayList<Integer> posicions = new ArrayList<>();
		int inici = posOrigen < 0 ? 0 : posOrigen + 1;

		for (int i = inici; i <= posDestiFinal; i++) {
			posicions.add(i);
		}

		if (posicions.isEmpty()) {
			aplicarEfecteCasellaAmbRetard(jugador, posDestiFinal, dadoElegido, partida);
			return;
		}

		final int retardPerCasella = 120; // Milisegundos entre casillas
		Timeline timeline = new Timeline();

		for (int i = 0; i < posicions.size(); i++) {
			final int posIntermedia = posicions.get(i);
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(retardPerCasella * (i + 1)), e -> {
				jugador.setPosicion(posIntermedia);
				ultimaPosicionAnimada = posIntermedia;
				generarTableroVisual();
				actualizarPosicionesVisuales();
			}));
		}

		timeline.setOnFinished(e -> {
			if (posDestiFinal >= 0 && posDestiFinal < partida.getTablero().getCasillas().size()) {
				Casilla c = partida.getTablero().getCasillas().get(posDestiFinal);

				mostrarMissatgeCasella1(c, jugador);
			}
			afegirMissatge(jugador.getNombre() + " llega a la casilla " + (posDestiFinal + 1));
			aplicarEfecteCasellaAmbRetard(jugador, posDestiFinal, dadoElegido, partida);
		});

		timeline.play();
	}

	/**
	 * Aplica l'efecte de la casella de destinació, actualitza la IU i finalitza el torn animat.
	 * Si el jugador ha retrocedit per l'efecte, mostra un missatge de retrocés.
	 *
	 * @param jugador       Jugador que ha caigut a la casella
	 * @param posDestiFinal Posició de la casella on ha caigut
	 * @param dadoElegido   Dau que s'ha utilitzat
	 * @param partida       Partida en curs
	 */
	private void aplicarEfecteCasellaAmbRetard(Jugador jugador, int posDestiFinal, Dado dadoElegido, Partida partida) {
		int posAbansEfecte = jugador.getPosicion();

		if (posDestiFinal >= 0 && posDestiFinal < partida.getTablero().getCasillas().size()) {
			Casilla casilla = partida.getTablero().getCasillas().get(posDestiFinal);
			casilla.realizarAccion(partida, jugador);
		}

		int posDespresEfecte = jugador.getPosicion();
		boolean hiHaRetroces = posDespresEfecte < posAbansEfecte;

		consumirDadoEspecialSiHaceFalta(jugador, dadoElegido);
		ultimaPosicionAnimada = posDespresEfecte;

		generarTableroVisual();
		actualizarPosicionesVisuales();
		actualizarTooltipInventario();

		if (hiHaRetroces) {
			afegirMissatge("↩️ " + jugador.getNombre() + " retrocedeix a la casilla " + (posDespresEfecte + 1));
		}

		finalitzarTornAnimat(jugador, partida);
	}

	/**
	 * Finalitza el torn animat: comprova si hi ha guanyador, avança al pròxim torn
	 * i si el següent és la Foca CPU, executa el seu torn automàticament amb un retard.
	 *
	 * @param jugador Jugador que acaba de jugar
	 * @param partida Partida en curs
	 */
	private void finalitzarTornAnimat(Jugador jugador, Partida partida) {
		if (jugador.getPosicion() == partida.getTablero().getTamano() - 1) {
			afegirMissatge("🏆 ¡Ha guanyat " + jugador.getNombre() + "!");
			dadoMenu.setDisable(true);
			return;
		}

		partida.avanzarTurno();
		actualizarTextoTurno();
		actualizarTooltipInventario();

		if (partida.getJugadorActual() instanceof Foca) {
			PauseTransition pausaFoca = new PauseTransition(Duration.seconds(1));
			pausaFoca.setOnFinished(e -> ejecutarUnTurno());
			pausaFoca.play();
		}

		dadoMenu.setDisable(false);
	}

	/**
	 * Actualitza el tooltip de l'inventari mostrant els objectes del jugador actual.
	 * Si el jugador és la Foca o no té inventari, mostra un missatge adequat.
	 */
	private void actualizarTooltipInventario() {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			tooltipInventari.setText("Sense inventari");
			return;
		}
		Jugador actual = gestorPartida.getPartida().getJugadorActual();
		if (!(actual instanceof Pinguino)) {
			tooltipInventari.setText(actual instanceof Foca ? "La Foca CPU no tiene inventario" : "Sin inventario");
			return;
		}

		Pinguino p = (Pinguino) actual;
		Inventario inv = p.getInventario();
		if (inv == null) {
			tooltipInventari.setText("Inventario vacío");
			return;
		}

		int dr = 0, dl = 0, dn = 0;
		for (Dado d : inv.getDado()) {
			if (d instanceof Dado_rapido) {
				dr++;
			} else if (d instanceof Dado_lento) {
				dl++;
			} else {
				dn++;
			}
		}

		String texto = "Inventari de " + actual.getNombre() + "\nPECES: " + inv.getPez().size() + "\nBOLAS DE NIEVE: "
				+ inv.getBolaNieve().size() + "\nDADO NORMAL: " + dn + "\nDADO RÁPIDO: " + dr + "\nDADO LENTO: " + dl;
		tooltipInventari.setText(texto);
	}

	/**
	 * Cerca a l'inventari del jugador un dau del tipus indicat.
	 * Si no en troba cap del tipus demanat, retorna el primer dau normal disponible.
	 *
	 * @param jugador Jugador del qual es busca el dau
	 * @param tipo    Tipus de dau: "rapido", "lento" o "normal"
	 * @return Dau trobat, o null si el jugador no té inventari o no és un pingüí
	 */
	private Dado buscarDadoSegunTipo(Jugador jugador, String tipo) {
		if (!(jugador instanceof Pinguino)) {
			return null;
		}
		Pinguino p = (Pinguino) jugador;
		if (p.getInventario() == null) {
			return null;
		}

		ArrayList<Dado> dados = p.getInventario().getDado();
		for (Dado d : dados) {
			if (tipo.equalsIgnoreCase("rapido") && d instanceof Dado_rapido) {
				return d;
			}
			if (tipo.equalsIgnoreCase("lento") && d instanceof Dado_lento) {
				return d;
			}
			if (tipo.equalsIgnoreCase("normal") && !(d instanceof Dado_rapido) && !(d instanceof Dado_lento)) {
				return d;
			}
		}
		for (Dado d : dados) {
			if (!(d instanceof Dado_rapido) && !(d instanceof Dado_lento)) {
				return d;
			}
		}
		return null;
	}

	/**
	 * Si el jugador ha usat un dau especial (ràpid o lent), l'elimina de l'inventari
	 * i restableix el tipus de dau seleccionat a "normal".
	 *
	 * @param jugador   Jugador que ha jugat el torn
	 * @param dadoUsado Dau que s'ha usat en el torn
	 */
	private void consumirDadoEspecialSiHaceFalta(Jugador jugador, Dado dadoUsado) {
		if (!(jugador instanceof Pinguino) || dadoUsado == null) {
			return;
		}
		Pinguino p = (Pinguino) jugador;
		if (dadoUsado instanceof Dado_rapido || dadoUsado instanceof Dado_lento) {
			p.getInventario().eliminarDado(dadoUsado);
			tipoDadoSeleccionado = "normal";
			dadoMenu.setText("Tirar dado");
		}
	}

	/**
	 * Genera el missatge d'event per a la casella on ha caigut el jugador i el mostra.
	 * Comprova el tipus de casella i construeix el missatge adequat.
	 *
	 * @param casilla Casella on ha caigut el jugador
	 * @param jugador Jugador que ha caigut a la casella
	 */
	private void mostrarMissatgeCasella(Casilla casilla, Jugador jugador) {
		String missatge = null;

		if (casilla instanceof Oso) {
			Pinguino p = (Pinguino) jugador;
			if (p.getInventario().tienePez()) {
				missatge = "🐻 ¡ÓS! " + jugador.getNombre() + " usa un peix per escapar!";
			} else {
				missatge = "🐻 ¡ÓS! " + jugador.getNombre() + " torna a l'inici!";
			}
		} else if (casilla instanceof Trineo) {
			missatge = "🛷 ¡TRINEU! " + jugador.getNombre() + " avança ràpidament!";
		} else if (casilla instanceof Agujero) {
			missatge = "🕳️ ¡FORAT! " + jugador.getNombre() + " cau al forat anterior!";
		} else if (casilla instanceof Interrogante) {
			missatge = "❓ ¡INTERROGANT! " + jugador.getNombre() + " rep un objecte sorpresa!";
		} else if (casilla instanceof SueloQuebradizo) {
			Pinguino p = (Pinguino) jugador;
			int totalObj = p.getInventario().getPez().size() + p.getInventario().getBolaNieve().size()
					+ p.getInventario().getDado().size();

			if (totalObj > 5) {
				missatge = "⚠️ ¡TERRA TRENCAT! Massa pes (" + totalObj + "). Torna a l'inici!";
			} else if (totalObj > 0) {
				missatge = "❄️ ¡TERRA TRENCAT! " + jugador.getNombre() + " perd un torn.";
			} else {
				missatge = "✅ ¡TERRA TRENCAT! Sense objectes, passes segur.";
			}
		}

		if (missatge != null) {
			afegirMissatge(missatge);
		}
	}

	/**
	 * Gestiona la guerra de boles de neu entre dos pingüins que coincideixen a la mateixa casella.
	 * El perdedor retrocedeix la diferència de boles. En cas d'empat, ningú es mou.
	 * Mostra un diàleg d'alerta amb el resultat de la guerra.
	 *
	 * @param p1 Primer pingüí participant
	 * @param p2 Segon pingüí participant
	 */
	private void resolverGuerraBolas(Pinguino p1, Pinguino p2) {
		int bolasP1 = p1.getInventario().getBolaNieve().size();
		int bolasP2 = p2.getInventario().getBolaNieve().size();

		String resultado;
		p1.getInventario().getBolaNieve().clear();
		p2.getInventario().getBolaNieve().clear();

		if (bolasP1 > bolasP2) {
			int retroceso = bolasP1 - bolasP2;
			retrocederJugador(p2, retroceso);
			resultado = "🏆 " + p1.getNombre() + " gana la guerra.\n" + p2.getNombre() + " retrocede " + retroceso
					+ " casillas.";
		} else if (bolasP2 > bolasP1) {
			int retroceso = bolasP2 - bolasP1;
			retrocederJugador(p1, retroceso);
			resultado = "🏆 " + p2.getNombre() + " gana la guerra.\n" + p1.getNombre() + " retrocede " + retroceso
					+ " casillas.";
		} else {
			resultado = "🤝 ¡Empate técnico! Nadie se mueve.";
		}

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("¡Guerra de Bolas de Nieve!");
		alert.setHeaderText("Conflicto en la casilla " + (p1.getPosicion() + 1));
		alert.setContentText(p1.getNombre() + " (" + bolasP1 + " ❄️) vs " + p2.getNombre() + " (" + bolasP2 + " ❄️)\n\n"
				+ resultado);

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/imatges/rico.jpg")));

		alert.showAndWait();

		actualizarPosicionesVisuales();
		actualizarTooltipInventario();
	}

	/**
	 * Fa retrocedir un jugador un nombre de caselles i mostra un missatge informatiu.
	 *
	 * @param j        Jugador que retrocedeix
	 * @param casillas Nombre de caselles que retrocedeix
	 */
	private void retrocederJugador(Jugador j, int casillas) {
		int nuevaPos = Math.max(-1, j.getPosicion() - casillas);
		j.setPosicion(nuevaPos);
		afegirMissatge("↩️ " + j.getNombre() + " retrocede " + casillas + " casillas.");
	}

	/**
	 * Versió alternativa d'afegirMissatge (duplicat intern per compatibilitat).
	 *
	 * @param text Missatge a mostrar
	 */
	private void afegirMissatge1(String text) {
		if (eventos != null) {
			eventos.setText(text);
		}
		if (consolaEventos != null) {
			consolaEventos.appendText(text + "\n");
		}
	}

	/**
	 * Mostra el missatge de casella amb efecte pop-art visual animat.
	 * Determina el color i el text en funció del tipus de casella i llança l'animació.
	 *
	 * @param casilla Casella on ha caigut el jugador
	 * @param jugador Jugador que ha caigut a la casella
	 */
	private void mostrarMissatgeCasella1(Casilla casilla, Jugador jugador) {
		String missatge = null;
		Color colorPop = Color.WHITE;

		if (casilla instanceof Oso) {
			colorPop = Color.ORANGERED;
			Pinguino p = (Pinguino) jugador;
			if (p.getInventario().tienePez()) {
				missatge = "🐻 ¡ÓS! " + jugador.getNombre() + " usa un peix!";
			} else {
				missatge = "🐻 ¡ÓS! " + jugador.getNombre() + " torna a l'inici!";
			}
		} else if (casilla instanceof Trineo) {
			colorPop = Color.GOLD;
			missatge = "🛷 ¡TRINEU! " + jugador.getNombre() + " avança ràpidament!";
		} else if (casilla instanceof Agujero) {
			colorPop = Color.DEEPSKYBLUE;
			missatge = "🕳️ ¡FORAT! " + jugador.getNombre() + " cau al forat anterior!";
		} else if (casilla instanceof Interrogante) {
			colorPop = Color.VIOLET;
			missatge = "❓ ¡INTERROGANT! " + jugador.getNombre() + " rep un objecte!";
		} else if (casilla instanceof SueloQuebradizo) {
			colorPop = Color.LIGHTCYAN;
			Pinguino p = (Pinguino) jugador;
			int totalObj = p.getInventario().getPez().size() + p.getInventario().getBolaNieve().size()
					+ p.getInventario().getDado().size();

			if (totalObj > 5) {
				missatge = "⚠️ ¡TERRA TRENCAT! Massa pes!";
			} else if (totalObj > 0) {
				missatge = "❄️ ¡TERRA TRENCAT! Perd un torn.";
			} else {
				missatge = "✅ ¡TERRA TRENCAT! Passes segur.";
			}
		}

		if (missatge != null) {
			afegirMissatge(missatge);
			// LLAMADA AL POP ART:
			mostrarPopArt(missatge, colorPop);
		}
	}

	/**
	 * Mostra un text amb animació pop-art sobre el tauler (FadeOut + moviment cap amunt).
	 * Un cop acabada l'animació, elimina el text del panell pare.
	 *
	 * @param mensaje Missatge a mostrar
	 * @param color   Color del text pop-art
	 */
	private void mostrarPopArt(String mensaje, Color color) {
		Text popText = new Text(mensaje);
		popText.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 35px; -fx-font-weight: bold;");
		popText.setFill(color);
		popText.setStroke(Color.BLACK);
		popText.setStrokeWidth(1.5);

		popText.setTranslateX(tablero.getLayoutX() + (tablero.getWidth() / 4));
		popText.setTranslateY(tablero.getLayoutY() + (tablero.getHeight() / 2));
		popText.setMouseTransparent(true);

		if (tablero.getParent() instanceof Pane) {
			((Pane) tablero.getParent()).getChildren().add(popText);
		}

		FadeTransition ft = new FadeTransition(Duration.seconds(2), popText);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);

		TranslateTransition tt = new TranslateTransition(Duration.seconds(2), popText);
		tt.setByY(-150);

		ParallelTransition pt = new ParallelTransition(ft, tt);
		pt.setOnFinished(ev -> {
			if (tablero.getParent() instanceof Pane) {
				((Pane) tablero.getParent()).getChildren().remove(popText);
			}
		});
		pt.play();
	}

	/**
	 * Anima la casella de destinació amb un lleuger augment d'escala (efecte "pulse").
	 *
	 * @param celda StackPane de la casella a animar
	 */
	private void animarCasillaDestino(StackPane celda) {
		ScaleTransition st = new ScaleTransition(Duration.millis(180), celda);
		st.setFromX(1.0);
		st.setFromY(1.0);
		st.setToX(1.08);
		st.setToY(1.08);
		st.setCycleCount(2);
		st.setAutoReverse(true);
		st.play();
	}

	/**
	 * Anima la fitxa del jugador amb un petit bot vertical.
	 *
	 * @param ficha Circle (fitxa) a animar
	 */
	private void animarFicha(Circle ficha) {
		TranslateTransition tt = new TranslateTransition(Duration.millis(180), ficha);
		tt.setFromY(0);
		tt.setToY(-6);
		tt.setCycleCount(2);
		tt.setAutoReverse(true);
		tt.play();
	}

	/**
	 * Comprova si el jugador que s'ha mogut coincideix a la mateixa casella que un altre pingüí.
	 * Si és així, inicia una guerra de boles de neu entre tots dos.
	 *
	 * @param jugadorMovido Jugador que s'ha mogut en aquest torn
	 */
	private void comprobarConflictos(Jugador jugadorMovido) {
		if (!(jugadorMovido instanceof Pinguino)) {
			return;
		}

		int posicionActual = jugadorMovido.getPosicion();
		if (posicionActual < 0) {
			return;
		}

		ArrayList<Jugador> jugadores = gestorPartida.getPartida().getJugadores();

		for (Jugador otro : jugadores) {
			if (otro != jugadorMovido && otro instanceof Pinguino && otro.getPosicion() == posicionActual) {
				Pinguino p1 = (Pinguino) jugadorMovido;
				Pinguino p2 = (Pinguino) otro;

				afegirMissatge("⚔️ ¡Conflicto entre " + p1.getNombre() + " y " + p2.getNombre() + "!");
				resolverGuerraBolas(p1, p2);
				break;
			}
		}
	}

}