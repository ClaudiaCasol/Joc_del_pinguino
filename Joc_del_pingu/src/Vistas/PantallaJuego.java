package Vistas;

import java.util.ArrayList;

import Controladores.GestorBBDD;
import Controladores.GestorPartida;
import Modelos.Agujero;
import Modelos.AudioManager;
import Modelos.BolaNieve;
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
import javafx.animation.KeyFrame;
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
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PantallaJuego {

    @FXML private Button btnSonido;
    private boolean sonidoActivo = true;
    AudioManager audio = new AudioManager();

    @FXML private GridPane tablero;
    @FXML private StackPane sortidaPane;
    @FXML private SplitMenuButton dadoMenu;
    @FXML private Button inventarioButton;
    @FXML private Text eventos;
    @FXML private Text dadoResultText;
    @FXML private TextArea consolaEventos;

    private GestorPartida gestorPartida;
    private int numeroJugadores = 2;
    private static final int COLUMNS = 5;

    private final ArrayList casillasVista = new ArrayList<>();
    private final ArrayList fichasVista = new ArrayList<>();

    private String tipoDadoSeleccionado = "normal";
    private Tooltip tooltipInventari = new Tooltip();
    private ArrayList usuarios;
    private Partida partida;

    private int sueloQuebradizoActivo = -1;
    private int ultimaPosicionAnimada = -1;

    @FXML
    private void handleNewGame(ActionEvent event) {
        prepararPantalla();
    }

	@FXML
	private TextArea consolaEventos;

	private GestorPartida gestorPartida;
	private int numeroJugadores = 2;

	private static final int COLUMNS = 5;

	private final ArrayList<StackPane> casillasVista = new ArrayList<>();
	private final ArrayList<Circle> fichasVista = new ArrayList<>();

	private String tipoDadoSeleccionado = "normal";
	private Tooltip tooltipInventari = new Tooltip();
	private ArrayList<Usuario> usuarios;
	private Partida partida;

	// mostra el gel trencat només el torn en què s'activa
	private int sueloQuebradizoActivo = -1;

	// per animar la fitxa/casella on acaba el moviment
	private int ultimaPosicionAnimada = -1;

	@FXML
	private void handleNewGame(ActionEvent event) {
		prepararPantalla();
	}

	@FXML
	private void handleSaveGame(ActionEvent event) {
		GestorBBDD.guardar(gestorPartida.getPartida());	}

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

	@FXML
	private void handleQuitGame(ActionEvent event) {
		System.exit(0);
	}

    @FXML
    private void initialize() {
        afegirMissatge("Preparando partida...");
    }

    public void configurarPartida(int numeroJugadores, ArrayList usuario) {
        this.numeroJugadores = numeroJugadores;
        this.usuarios = usuario;
    }

    public void setUsuarios(ArrayList usuarios) {
        this.usuarios = usuarios;
    }

	public void prepararPantalla() {
		gestorPartida = new GestorPartida();

		for(Usuario u : usuarios) {

			GestorBBDD.sumarPartidaJugada(u.getNombre());

		}

		gestorPartida.iniciarPartida(numeroJugadores, usuarios);

        generarTableroVisual();
        crearFichas();
        actualizarPosicionesVisuales();
        actualizarTextoTurno();
        inventarioButton.setTooltip(tooltipInventari);
        actualizarTooltipInventario();
        audio.reproducirMusica("/audio/tablero.mp3");
        afegirMissatge("Partida iniciada con " + numeroJugadores + " jugadores.");
    }

    public void cargarPartida(Partida partida) {
        gestorPartida = new GestorPartida();
        gestorPartida.setPartida(partida);
        generarTableroVisual();
        crearFichas();
        actualizarPosicionesVisuales();
        actualizarTextoTurno();
        actualizarTooltipInventario();
        inventarioButton.setTooltip(tooltipInventari);
        actualizarTooltipInventario();
        audio.reproducirMusica("/audio/tablero.mp3");
        afegirMissatge("Partida iniciada con " + numeroJugadores + " jugadores.");
    }

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

	}

	//Creamos la función equivalente apreparar pantalla pero para la funcionalidad de cargar partida desde BBDD
	public void cargarPartida(Partida partida) {
		gestorPartida = new GestorPartida();
		gestorPartida.setPartida(partida);

		generarTableroVisual();
		crearFichas();
		actualizarPosicionesVisuales();
		actualizarTextoTurno();
		actualizarTooltipInventario();

		inventarioButton.setTooltip(tooltipInventari);
		actualizarTooltipInventario();
		audio.reproducirMusica("/audio/tablero.mp3");
		afegirMissatge("Partida iniciada con " + numeroJugadores + " jugadores.");
	}

        Text numero = new Text(String.valueOf(indice + 1));
        numero.setStyle("-fx-font-size: 10; -fx-fill: #2c3e50; -fx-font-weight: bold;");
        StackPane.setAlignment(numero, Pos.TOP_RIGHT);
        numero.setTranslateX(-5);
        numero.setTranslateY(5);
        celda.getChildren().add(numero);

		if (sonidoActivo) {
			btnSonido.setText("🔊");
			audio.setVolumen(1.0);
		} else {
			btnSonido.setText("🔇");
			audio.setVolumen(0.0);
		}
	}

	private void generarTableroVisual() {
		tablero.getChildren().clear();
		casillasVista.clear();

		ArrayList<Casilla> casillas = gestorPartida.getPartida().getTablero().getCasillas();
		System.out.println(gestorPartida.getPartida().getTablero().getCasillas().toString());

        return celda;
    }

    private ImageView cargarImagen(String path, double size) {
        Image img = new Image(getClass().getResourceAsStream(path));
        ImageView iv = new ImageView(img);
        iv.setFitWidth(size);
        iv.setFitHeight(size);
        return iv;
    }

    private String estiloCasilla(Casilla casilla) {
        String base = "-fx-background-radius: 8; -fx-border-radius: 8; "
                + "-fx-border-color: rgba(60,60,60,0.35); -fx-border-width: 1;";
        if (casilla instanceof Oso)             return "-fx-background-color: #e07a5f; " + base;
        if (casilla instanceof Trineo)          return "-fx-background-color: #ffd166; " + base;
        if (casilla instanceof Agujero)         return "-fx-background-color: #5dade2; " + base;
        if (casilla instanceof Interrogante)    return "-fx-background-color: #cdb4db; " + base;
        if (casilla instanceof SueloQuebradizo) return "-fx-background-color: #d6f0ff; " + base;
        return "-fx-background-color: #f5fdff; " + base;
    }


		StackPane celda = new StackPane();
		celda.setPrefSize(140, 78);
		celda.setStyle(estiloCasilla(casilla));

		Text numero = new Text(String.valueOf(indice + 1));
		numero.setStyle("-fx-font-size: 10; -fx-fill: #2c3e50; -fx-font-weight: bold;");

		StackPane.setAlignment(numero, Pos.TOP_RIGHT);
		numero.setTranslateX(-5);
		numero.setTranslateY(5);

		celda.getChildren().add(numero);

		ImageView img = null;

		if (casilla instanceof Oso) {
			img = cargarImagen("/imatges/oso.png", 40);
		}

		if (casilla instanceof Trineo) {
			img = cargarImagen("/imatges/trineo.png", 40);
		}

		if (casilla instanceof Interrogante) {
			img = cargarImagen("/imatges/interrogante.png", 35);
		}

		if (casilla instanceof SueloQuebradizo && indice == sueloQuebradizoActivo) {
			img = cargarImagen("/imatges/hielo-roto.png", 40);
		}

		if (img != null) {
			celda.getChildren().add(img);
		}

		String estiloBase = estiloCasilla(casilla);

		celda.setOnMouseEntered(e -> {
			celda.setStyle(estiloBase + "-fx-border-color: #0b3d91; -fx-border-width: 2;");
		});

		celda.setOnMouseExited(e -> {
			celda.setStyle(estiloBase);
		});

		return celda;
	}

	private ImageView cargarImagen(String path, double size) {
		Image img = new Image(getClass().getResourceAsStream(path));
		ImageView iv = new ImageView(img);
		iv.setFitWidth(size);
		iv.setFitHeight(size);
		return iv;
	}

	private String estiloCasilla(Casilla casilla) {
		String base = "-fx-background-radius: 8; "
				+ "-fx-border-radius: 8; "
				+ "-fx-border-color: rgba(60,60,60,0.35); "
				+ "-fx-border-width: 1;";

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

	private void crearFichas() {
		fichasVista.clear();

		ArrayList<Jugador> jugadores = gestorPartida.getPartida().getJugadores();

		for (int i = 0; i < jugadores.size(); i++) {

			Circle ficha = new Circle(11);
			ficha.setStroke(Color.web("#2c3e50"));
			ficha.setStrokeWidth(1.5);

			String ruta;

			switch (i) {
			case 0:
				ruta = "/imatges/skipper.jpg";
				break;
			case 1:
				ruta = "/imatges/kowalski.jpg";
				break;
			case 2:
				ruta = "/imatges/rico.jpg";
				break;
			case 3:
				ruta = "/imatges/private.jpg";
				break;
			default:
				ruta = "/imatges/focaPingu.jpg";
				break;
			}

			Image img = new Image(getClass().getResource(ruta).toExternalForm());

			//ajusta la imagen al círculo

			ficha.setFill(new ImagePattern(img, 0, 0, 1, 1, true));
			ficha.setStyle("-fx-effect: dropshadow(gaussian, black, 6, 0.5, 0, 2);");      
			fichasVista.add(ficha);
		}

	}

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

		for (int i = 0; i < jugadores.size(); i++) {
			Jugador j = jugadores.get(i);
			Circle ficha = fichasVista.get(i);
			Jugador actual = gestorPartida.getPartida().getJugadorActual();

			// estilo normal para resetear
			ficha.setStroke(Color.web("#2c3e50"));
			ficha.setStrokeWidth(1.5);
			ficha.setStyle("-fx-effect: dropshadow(gaussian, black, 6, 0.5, 0, 2);");

			// si es el jugador actual lo resalta
			if (j == actual) {
				ficha.setStroke(Color.web("#FFD700")); // dorado  suave
				ficha.setStrokeWidth(2.5);
				ficha.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.6), 8, 0.5, 0, 0);");
			}
			if (j.getPosicion() < 0) {
				mini.add(ficha, i % 2, i / 2);
			}else {
				StackPane celda = casillasVista.get(j.getPosicion());

				// reset posición por si venía de otra casilla
				ficha.setTranslateX(0);
				ficha.setTranslateY(0);

				// colocar bien las fichas dentro de la celda
				StackPane.setAlignment(ficha, Pos.CENTER);

				double[][] posiciones = {
						{-10, -10},
						{10, -10},
						{-10, 10},
						{10, 10}
				};

				if (i < posiciones.length) {
					ficha.setTranslateX(posiciones[i][0]);
					ficha.setTranslateY(posiciones[i][1]);
				}

				celda.getChildren().add(ficha); 

				if (j.getPosicion() == ultimaPosicionAnimada) {
					animarFicha(ficha); 
				}
			}
		}

		sortidaPane.getChildren().add(mini);
	}

	private int[] convertirIndiceASerp(int indice) {
		int fila = indice / COLUMNS;
		int col = indice % COLUMNS;

		if (fila % 2 == 1) {
			col = COLUMNS - 1 - col;
		}

		return new int[] { fila, col };
	}

	@FXML
	private void handleDadoNormal(ActionEvent event) {

		// jugador antes de mover
		Pinguino p = (Pinguino) gestorPartida.getPartida().getJugadorActual();

		// ejecutar turno
		jugarTurno();

		// sonido del dado
		System.out.println("SONIDO DADO");
		audio.reproducirEfecto("/audio/dados.mp3");

		// obtener casilla
		int pos = p.getPosicion();

		if (pos < 0) {
			return;
		}

		Casilla casilla = gestorPartida.getPartida()
				.getTablero()
				.getCasillas()
				.get(pos);

		// delay para que no se solapen sonidos
		PauseTransition pausa = new PauseTransition(Duration.seconds(1.2));

		pausa.setOnFinished(e -> {

			if (casilla instanceof Oso) {
				System.out.println("SONIDO OSO");
				audio.reproducirEfecto("/audio/oso.mp3");
			}
			else if (casilla instanceof Agujero) {
				System.out.println("SONIDO agujero");
				audio.reproducirEfecto("/audio/agujero.mp3");

			}
			else if (casilla instanceof Trineo) {
				System.out.println("SONIDO trineo");
				audio.reproducirEfecto("/audio/trineo.mp3");

			}
			else if (casilla instanceof SueloQuebradizo) {
				System.out.println("SONIDO suelo");
				audio.reproducirEfecto("/audio/sueloQuebradizo.mp3");

			}
			else if (casilla instanceof Interrogante) {
				System.out.println("SONIDO suelo");
				audio.reproducirEfecto("/audio/exclamacion.mp3");

			}

		});

		pausa.play();
	}

	@FXML
	private void seleccionarDadoNormal(ActionEvent event) {
		tipoDadoSeleccionado = "normal";
		dadoMenu.setText("Tirar dado");
	}

	@FXML
	private void seleccionarDadoRapido(ActionEvent event) {
		tipoDadoSeleccionado = "rapido";
		dadoMenu.setText("Tirar rápido");
	}

	@FXML
	private void seleccionarDadoLento(ActionEvent event) {
		tipoDadoSeleccionado = "lento";
		dadoMenu.setText("Tirar lento");
	}

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

	private void ejecutarUnTurno() {

		Partida partida = gestorPartida.getPartida();
		Jugador jugador = partida.getJugadorActual();

		Dado dadoElegido = null;

		if (!(jugador instanceof Foca)) {
			dadoElegido = buscarDadoSegunTipo(jugador, tipoDadoSeleccionado);
		}

		int resultado = gestorPartida.tirarDado(jugador, dadoElegido);
		dadoResultText.setText("Ha salido: " + resultado);
		afegirMissatge(jugador.getNombre() + " ha tret un " + resultado);

		int antes = jugador.getPosicion();
		partida.jugarTurno(resultado);
		int despues = jugador.getPosicion();

		consumirDadoEspecialSiHaceFalta(jugador, dadoElegido);

		ultimaPosicionAnimada = despues;

		// reset visual del hielo roto
		sueloQuebradizoActivo = -1;

		// si cae en suelo quebradizo, mostramos imagen solo este turno
		if (despues >= 0 && despues < partida.getTablero().getCasillas().size()) {
			Casilla c = partida.getTablero().getCasillas().get(despues);
			if (c instanceof SueloQuebradizo) {
				sueloQuebradizoActivo = despues;
			}
		}

		if (dadoElegido instanceof Dado_rapido) {
			afegirMissatge(jugador.getNombre() + " usa dado rápido y pasa de " + (antes + 1) + " a " + (despues + 1));
		} else if (dadoElegido instanceof Dado_lento) {
			afegirMissatge(jugador.getNombre() + " usa dado lento y pasa de " + (antes + 1) + " a " + (despues + 1));
		} else {
			afegirMissatge(jugador.getNombre() + " pasa de " + (antes + 1) + " a " + (despues + 1));
		}

		generarTableroVisual();
		actualizarPosicionesVisuales();
		actualizarTooltipInventario();

		if (despues >= 0 && despues < casillasVista.size()) {
			animarCasillaDestino(casillasVista.get(despues));
		}

		if (partida.estaFinalizada()) {

			afegirMissatge(
					"Ha ganado " +
							partida.getGanador().getNombre()
					);

			dadoMenu.setDisable(true);

			if(partida.getGanador() instanceof Pinguino) {

				Pinguino ganador =
						(Pinguino) partida.getGanador();

				GestorBBDD.sumarPartidaGanada(
						ganador.getUsuario().getNombre()
						);
			}
		}
	}

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

			if (tipo.equalsIgnoreCase("normal")
					&& !(d instanceof Dado_rapido)
					&& !(d instanceof Dado_lento)) {
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

	private void consumirDadoEspecialSiHaceFalta(Jugador jugador, Dado dadoUsado) {
		if (!(jugador instanceof Pinguino) || dadoUsado == null) {
			return;
		}

		Pinguino p = (Pinguino) jugador;

		if (!(dadoUsado instanceof Dado_rapido) && !(dadoUsado instanceof Dado_lento)) {
			return;
		}

		p.getInventario().eliminarDado(dadoUsado);

		tipoDadoSeleccionado = "normal";
		dadoMenu.setText("Tirar dado");
	}

	private void afegirMissatge(String text) {
		if (eventos != null) {
			eventos.setText(text);
		}

		if (consolaEventos != null) {
			consolaEventos.appendText(text + "\n");
		}
	}

	private void actualizarTextoTurno() {
		Jugador actual = gestorPartida.getPartida().getJugadorActual();

		if (actual instanceof Foca) {
			afegirMissatge("Turno de la Foca CPU");
		} else {
			afegirMissatge("Turno de " + actual.getNombre());
		}
	}

	private void actualizarTooltipInventario() {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			tooltipInventari.setText("Sense inventari");
			return;
		}

		Jugador actual = gestorPartida.getPartida().getJugadorActual();

		if (actual instanceof Foca) {
			tooltipInventari.setText("La Foca CPU no tiene inventario");
			return;
		}

		if (!(actual instanceof Pinguino)) {
			tooltipInventari.setText("Este jugador no tiene inventario");
			return;
		}

		Pinguino p = (Pinguino) actual;
		Inventario inv = p.getInventario();

		if (inv == null) {
			tooltipInventari.setText("Inventario vacío");
			return;
		}

		int dadosNormales = 0;
		int dadosRapidos = 0;
		int dadosLentos = 0;

		for (Dado d : inv.getDado()) {
			if (d instanceof Dado_rapido) {
				dadosRapidos++;
			} else if (d instanceof Dado_lento) {
				dadosLentos++;
			} else {
				dadosNormales++;
			}
		}

		String texto = "Inventari de " + actual.getNombre()
		+ "\nPECES: " + inv.getPez().size()
		+ "\nBOLAS DE NIEVE: " + inv.getBolaNieve().size()
		+ "\nDADO NORMAL: " + dadosNormales
		+ "\nDADO RÁPIDO: " + dadosRapidos
		+ "\nDADO LENTO: " + dadosLentos;

		tooltipInventari.setText(texto);
	}

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

	private void animarFicha(Circle ficha) {
		TranslateTransition tt = new TranslateTransition(Duration.millis(180), ficha);
		tt.setFromY(0);
		tt.setToY(-6);
		tt.setCycleCount(2);
		tt.setAutoReverse(true);
		tt.play();
	}
   


    private void jugarTurnAnimat() {
        if (gestorPartida == null || gestorPartida.getPartida() == null) return;
        Partida partida = gestorPartida.getPartida();
        if (partida.estaFinalizada()) return;

        Jugador jugador = partida.getJugadorActual();
        if (jugador instanceof Foca) {
            ejecutarUnTurno();
            while (!partida.estaFinalizada() && partida.getJugadorActual() instanceof Foca) {
                ejecutarUnTurno();
            }
            return;
        }

        Dado dadoElegido  = buscarDadoSegunTipo(jugador, tipoDadoSeleccionado);
        int resultado     = gestorPartida.tirarDado(jugador, dadoElegido);

        dadoResultText.setText("Ha salido: " + resultado);
        afegirMissatge(jugador.getNombre() + " ha tret un " + resultado);

        int posOrigen     = jugador.getPosicion();
        int posDestiFinal = calcularPosFinal(jugador, resultado, partida);

        dadoMenu.setDisable(true);
        animarMovimentCasellaACasella(jugador, posOrigen, posDestiFinal, dadoElegido, partida);
    }

    private int calcularPosFinal(Jugador jugador, int pasos, Partida partida) {
        int posActual = jugador.getPosicion() < 0 ? -1 : jugador.getPosicion();
        int ultimaPos = partida.getTablero().getTamano() - 1;
        int novaPos   = posActual + pasos;
        if (novaPos > ultimaPos) {
            int exceso = novaPos - ultimaPos;
            novaPos = ultimaPos - exceso;
        }
        if (novaPos < -1) novaPos = -1;
        return novaPos;
    }

    private void animarMovimentCasellaACasella(Jugador jugador, int posOrigen,
            int posDestiFinal, Dado dadoElegido, Partida partida) {

        ArrayList<Integer> posicions = new ArrayList<>();
        int inici = posOrigen < 0 ? 0 : posOrigen + 1;
        for (int i = inici; i <= posDestiFinal; i++) posicions.add(i);

        if (posicions.isEmpty()) {
            aplicarEfecteCasellaAmbRetard(jugador, posDestiFinal, dadoElegido, partida);
            return;
        }

        final int retardPerCasella = 120;
        Timeline timeline = new Timeline();

        for (int i = 0; i < posicions.size(); i++) {
            final int posIntermedia = posicions.get(i);
            timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(retardPerCasella * (i + 1)),
                e -> {
                    jugador.setPosicion(posIntermedia);
                    ultimaPosicionAnimada = posIntermedia;
                    generarTableroVisual();
                    actualizarPosicionesVisuales();
                }
            ));
        }

        timeline.setOnFinished(e -> {
            if (posDestiFinal >= 0 && posDestiFinal < partida.getTablero().getCasillas().size()) {
                mostrarMissatgeCasella(
                    (Casilla) partida.getTablero().getCasillas().get(posDestiFinal), jugador);
            }
            afegirMissatge(jugador.getNombre() + " arriba a la casella " + (posDestiFinal + 1));
            aplicarEfecteCasellaAmbRetard(jugador, posDestiFinal, dadoElegido, partida);
        });

        timeline.play();
    }

    private void aplicarEfecteCasellaAmbRetard(Jugador jugador, int posDestiFinal,
            Dado dadoElegido, Partida partida) {

        int posAbansEfecte = jugador.getPosicion();

        if (posDestiFinal >= 0 && posDestiFinal < partida.getTablero().getCasillas().size()) {
            Casilla casilla = (Casilla) partida.getTablero().getCasillas().get(posDestiFinal);
            casilla.realizarAccion(partida, jugador);
        }

        int posDesprésEfecte = jugador.getPosicion();
        boolean hiHaRetroces = posDesprésEfecte < posAbansEfecte;

        consumirDadoEspecialSiHaceFalta(jugador, dadoElegido);
        ultimaPosicionAnimada = posDesprésEfecte;

        sueloQuebradizoActivo = -1;
        if (posDesprésEfecte >= 0 && posDesprésEfecte < partida.getTablero().getCasillas().size()) {
            Casilla c = (Casilla) partida.getTablero().getCasillas().get(posDesprésEfecte);
            if (c instanceof SueloQuebradizo) sueloQuebradizoActivo = posDesprésEfecte;
        }

        if (hiHaRetroces) {
            generarTableroVisual();
            actualizarPosicionesVisuales();
            PauseTransition retard = new PauseTransition(Duration.millis(600));
            retard.setOnFinished(ev -> {
                afegirMissatge("⬅️ " + jugador.getNombre()
                        + " retrocedeix a la casella " + (posDesprésEfecte + 1));
                ultimaPosicionAnimada = posDesprésEfecte;
                generarTableroVisual();
                actualizarPosicionesVisuales();
                actualizarTooltipInventario();
                finalitzarTornAnimat(jugador, partida);
            });
            retard.play();
        } else {
            generarTableroVisual();
            actualizarPosicionesVisuales();
            actualizarTooltipInventario();
            finalitzarTornAnimat(jugador, partida);
        }
    }

    private void finalitzarTornAnimat(Jugador jugador, Partida partida) {
        if (jugador.getPosicion() == partida.getTablero().getTamano() - 1) {
            afegirMissatge("🏆 Ha guanyat " + jugador.getNombre() + "!");
            dadoMenu.setDisable(true);
            return;
        }

        partida.avanzarTurno();
        while (!partida.estaFinalizada() && partida.getJugadorActual() instanceof Foca) {
            ejecutarUnTurno();
        }

        actualizarTextoTurno();
        actualizarTooltipInventario();

        if (jugador instanceof Pinguino && jugador.getPosicion() >= 0) {
            Casilla casilla = (Casilla) partida.getTablero().getCasillas().get(jugador.getPosicion());
            comprobarCombateEnCasilla((Pinguino) jugador, jugador.getPosicion(), casilla);
        }

        dadoMenu.setDisable(false);
    }

    private void mostrarMissatgeCasella(Casilla casilla, Jugador jugador) {
        String missatge = null;
        if (casilla instanceof Oso) {
            Pinguino p = (Pinguino) jugador;
            if (p.getInventario().tienePez()) {
                missatge = "🐟 Ós! " + jugador.getNombre() + " usa un peix per escapar!";
            } else {
                missatge = "🐻 Ós! " + jugador.getNombre() + " torna a l'inici!";
            }
        } else if (casilla instanceof Trineo) {
            missatge = "🛷 Trineu! " + jugador.getNombre() + " avança al següent trineu!";
        } else if (casilla instanceof Agujero) {
            missatge = "🕳️ Forat! " + jugador.getNombre() + " cau al forat anterior.";
        } else if (casilla instanceof Interrogante) {
            missatge = "❓ Interrogant! " + jugador.getNombre() + " rep un objecte aleatori!";
        } else if (casilla instanceof SueloQuebradizo) {
            Pinguino p = (Pinguino) jugador;
            int total  = p.getInventario().getPez().size()
                       + p.getInventario().getBolaNieve().size()
                       + p.getInventario().getDado().size();
            if (total > 5) {
                missatge = "🧊 Terra trencat! Massa pes (" + total + " obj). Torna a l'inici!";
            } else if (total > 0) {
                missatge = "🧊 Terra trencat! " + jugador.getNombre() + " perd un torn.";
            } else {
                missatge = "🧊 Terra trencat! Sense objectes, passa sense penalització.";
            }
        }
        if (missatge != null) afegirMissatge(missatge);
    }

    private void comprobarCombateEnCasilla(Pinguino jugadorMovido, int posicion, Casilla casilla) {
        ArrayList jugadores = gestorPartida.getPartida().getJugadores();
        for (Object obj : jugadores) {
            Jugador otro = (Jugador) obj;
            if (otro == jugadorMovido) continue;
            if (!(otro instanceof Pinguino)) continue;
            if (otro.getPosicion() != posicion) continue;

            Pinguino otroPinguino = (Pinguino) otro;
            afegirMissatge("⚔️ " + jugadorMovido.getNombre()
                    + " i " + otroPinguino.getNombre()
                    + " es troben! Guerra de boles de neu!");
            executarGuerra(jugadorMovido, otroPinguino);
            break;
        }
    }

    private void executarGuerra(Pinguino p1, Pinguino p2) {
        int bolas1 = p1.getInventario().getBolaNieve().size();
        int bolas2 = p2.getInventario().getBolaNieve().size();

        afegirMissatge(p1.getNombre() + ": " + bolas1 + " boles ❄️  |  "
                + p2.getNombre() + ": " + bolas2 + " boles ❄️");

        p1.getInventario().getBolaNieve().clear();
        p2.getInventario().getBolaNieve().clear();

        if (bolas1 > bolas2) {
            int retroceso = bolas1 - bolas2;
            int novaPos   = Math.max(-1, p2.getPosicion() - retroceso);
            p2.setPosicion(novaPos);
            afegirMissatge("🏆 " + p1.getNombre() + " guanya! "
                    + p2.getNombre() + " retrocedeix " + retroceso + " caselles.");
        } else if (bolas2 > bolas1) {
            int retroceso = bolas2 - bolas1;
            int novaPos   = Math.max(-1, p1.getPosicion() - retroceso);
            p1.setPosicion(novaPos);
            afegirMissatge("🏆 " + p2.getNombre() + " guanya! "
                    + p1.getNombre() + " retrocedeix " + retroceso + " caselles.");
        } else {
            afegirMissatge("🤝 Empat! Cap jugador retrocedeix.");
        }

        ultimaPosicionAnimada = -1;
        generarTableroVisual();
        actualizarPosicionesVisuales();
        actualizarTooltipInventario();
    }
}