package Vistas;

import java.util.ArrayList;

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
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.PauseTransition;


public class PantallaJuego {
	AudioManager audio = new AudioManager();
    @FXML
    private GridPane tablero;

    @FXML
    private StackPane sortidaPane;

    @FXML
    private SplitMenuButton dadoMenu;

    @FXML
    private Button inventarioButton;

    @FXML
    private Text eventos;

    @FXML
    private Text dadoResultText;

    private GestorPartida gestorPartida;
    private int numeroJugadores = 2;

    private static final int COLUMNS = 5;

    private final ArrayList<StackPane> casillasVista = new ArrayList<>();
    private final ArrayList<Circle> fichasVista = new ArrayList<>();

    private String tipoDadoSeleccionado = "normal";
    private Tooltip tooltipInventari = new Tooltip();

    // mostra el gel trencat només el torn en què s'activa
    private int sueloQuebradizoActivo = -1;

    // per animar la fitxa/casella on acaba el moviment
    private int ultimaPosicionAnimada = -1;

    @FXML
    private void initialize() {
        eventos.setText("Preparant partida...");
    }

    public void configurarPartida(int numeroJugadores) {
        this.numeroJugadores = numeroJugadores;
    }

    public void prepararPantalla() {
        gestorPartida = new GestorPartida();
        gestorPartida.iniciarPartida(numeroJugadores);

        generarTableroVisual();
        crearFichas();
        actualizarPosicionesVisuales();
        actualizarTextoTurno();

        inventarioButton.setTooltip(tooltipInventari);
        actualizarTooltipInventario();
    }

   
    // TABLERO
   
    private void generarTableroVisual() {
        tablero.getChildren().clear();
        casillasVista.clear();

        ArrayList<Casilla> casillas = gestorPartida.getPartida().getTablero().getCasillas();

        for (int i = 0; i < casillas.size(); i++) {
            StackPane celda = crearCeldaVisual(casillas.get(i), i);

            int[] rc = convertirIndiceASerp(i);
            tablero.add(celda, rc[1], rc[0]);
            casillasVista.add(celda);
        }
    }

    private StackPane crearCeldaVisual(Casilla casilla, int indice) {

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

    
    // FICHAS
  
    private void crearFichas() {
        fichasVista.clear();

        ArrayList<Jugador> jugadores = gestorPartida.getPartida().getJugadores();

        for (int i = 0; i < jugadores.size(); i++) {

            Circle ficha = new Circle(12);
            ficha.setStroke(Color.BLACK);

            switch (i) {
                case 0:
                    ficha.setFill(Color.web("#56ff1f"));
                    break;
                case 1:
                    ficha.setFill(Color.DODGERBLUE);
                    break;
                case 2:
                    ficha.setFill(Color.web("#ff1fce"));
                    break;
                case 3:
                    ficha.setFill(Color.web("#fff01f"));
                    break;
                default:
                    ficha.setFill(Color.GRAY);
                    break;
            }

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

            if (j.getPosicion() < 0) {
                mini.add(ficha, i % 2, i / 2);
            } else {
                casillasVista.get(j.getPosicion()).getChildren().add(ficha);

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

  
    // DAUS
    
    @FXML
    private void handleDadoNormal(ActionEvent event) {

        // jugador antes de mover
        Pinguino p = (Pinguino) gestorPartida.getPartida().getJugadorActual();

        // ejecutar turno
        jugarTurno();

        // sonido del dado
        var url = getClass().getResource("/audio/dados.mp3");
        

        if (url != null) {
            audio.reproducirEfecto(url.toExternalForm());
        } else {
            System.out.println("NO ENCUENTRA: dados.mp3");
        }

        // obtener casilla
        Casilla casilla = gestorPartida.getPartida()
            .getTablero()
            .getCasillas()
            .get(p.getPosicion());

        // ⏱ delay para que no se solapen sonidos
        PauseTransition pausa = new PauseTransition(Duration.seconds(0.5));

        pausa.setOnFinished(e -> {

        	if (casilla instanceof Oso) {
        	    var urlOso = getClass().getResource("/audio/oso.mp3");
        	    if (urlOso != null) {
        	        audio.reproducirEfecto(urlOso.toExternalForm());
        	    } else {
        	        System.out.println("NO ENCUENTRA: oso.mp3");
        	    }
        	}
        	else if (casilla instanceof Agujero) {
        	    var urlAgujero = getClass().getResource("/audio/agujero.mp3");
        	    if (urlAgujero != null) {
        	        audio.reproducirEfecto(urlAgujero.toExternalForm());
        	    } else {
        	        System.out.println("NO ENCUENTRA: agujero.mp3");
        	    }
        	}
        	else if (casilla instanceof Trineo) {
        	    var urlTrineo = getClass().getResource("/audio/trineo.mp3");
        	    if (urlTrineo != null) {
        	        audio.reproducirEfecto(urlTrineo.toExternalForm());
        	    } else {
        	        System.out.println("NO ENCUENTRA: trineo.mp3");
        	    }
        	}
        	else if (casilla instanceof SueloQuebradizo) {
        	    var urlSuelo = getClass().getResource("/audio/sueloQuebradizo.mp3");
        	    if (urlSuelo != null) {
        	        audio.reproducirEfecto(urlSuelo.toExternalForm());
        	    } else {
        	        System.out.println("NO ENCUENTRA: sueloQuebradizo.mp3");
        	    }
        	}
            System.out.println(getClass().getResource("/audio/dados.mp3"));
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
            eventos.setText(jugador.getNombre() + " usa dado rápido y pasa de " + antes + " a " + despues);
        } else if (dadoElegido instanceof Dado_lento) {
            eventos.setText(jugador.getNombre() + " usa dado lento y pasa de " + antes + " a " + despues);
        } else {
            eventos.setText(jugador.getNombre() + " pasa de " + antes + " a " + despues);
        }

        generarTableroVisual();
        actualizarPosicionesVisuales();
        actualizarTooltipInventario();

        if (despues >= 0 && despues < casillasVista.size()) {
            animarCasillaDestino(casillasVista.get(despues));
        }

        if (partida.estaFinalizada()) {
            eventos.setText("Ha ganado " + partida.getGanador().getNombre());
            dadoMenu.setDisable(true);
        } else {
            actualizarTextoTurno();
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

    
    // INVENTARI I TEXTOS
   
    private void actualizarTextoTurno() {
        Jugador actual = gestorPartida.getPartida().getJugadorActual();

        if (actual instanceof Foca) {
            eventos.setText("Turno de la Foca CPU");
        } else {
            eventos.setText("Turno de " + actual.getNombre());
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

  
    // ANIMACIONS

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
}