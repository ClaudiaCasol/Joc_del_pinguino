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
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

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

    @FXML
    private TextArea consolaEventos;

    @FXML
    private StackPane notificacionPane;

    @FXML
    private Text notificacionText;

    @FXML
    private MenuItem opcionNormal;

    @FXML
    private MenuItem opcionRapido;

    @FXML
    private MenuItem opcionLento;

    @FXML
    private AnchorPane capaAnimacion;

    private GestorPartida gestorPartida;
    private int numeroJugadores = 2;

    private static final int COLUMNS = 5;

    private final ArrayList<StackPane> casillasVista = new ArrayList<>();
    private final ArrayList<Circle> fichasVista = new ArrayList<>();

    private String tipoDadoSeleccionado = "normal";
    private Tooltip tooltipInventari = new Tooltip();

    private int sueloQuebradizoActivo = -1;
    private int ultimaPosicionAnimada = -1;
    private boolean animandoMovimiento = false;

    @FXML
    private void handleNewGame(ActionEvent event) {
        System.out.println("Nuevo juego");
    }

    @FXML
    private void handleSaveGame() {
        System.out.println("Guardar partida");
    }

    @FXML
    private void handleLoadGame() {
        System.out.println("Cargar partida");
    }

    @FXML
    private void handleQuitGame() {
        System.exit(0);
    }

    @FXML
    private void initialize() {
        afegirMissatge("Preparant partida...");
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
        actualizarOpcionesDados();

        audio.reproducirMusica("/audio/tablero.mp3");
        afegirMissatge("Partida iniciada amb " + numeroJugadores + " jugadors.");
    }

    // =========================
    // TABLERO
    // =========================
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

        celda.setPrefSize(90, 90);
        celda.setMinSize(90, 90);
        celda.setMaxSize(90, 90);

        celda.setStyle(estiloCasilla(casilla));

        Text numero = new Text(String.valueOf(indice + 1));
        numero.setStyle("-fx-font-size: 11; -fx-fill: #2c3e50; -fx-font-weight: bold;");

        StackPane.setAlignment(numero, Pos.TOP_RIGHT);
        numero.setTranslateX(-6);
        numero.setTranslateY(6);

        celda.getChildren().add(numero);

        ImageView img = null;

        if (casilla instanceof Oso) {
            img = cargarImagen("/imatges/oso.png", 34);
        }

        if (casilla instanceof Trineo) {
            img = cargarImagen("/imatges/trineo.png", 34);
        }

        if (casilla instanceof Interrogante) {
            img = cargarImagen("/imatges/interrogante.png", 28);
        }

        if (casilla instanceof SueloQuebradizo && indice == sueloQuebradizoActivo) {
            img = cargarImagen("/imatges/hielo-roto.png", 34);
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
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
        return iv;
    }

    private String estiloCasilla(Casilla casilla) {
        String base = "-fx-background-radius: 12; "
                + "-fx-border-radius: 12; "
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

    private int[] convertirIndiceASerp(int indice) {
        int fila = indice / COLUMNS;
        int col = indice % COLUMNS;

        if (fila % 2 == 1) {
            col = COLUMNS - 1 - col;
        }

        return new int[] { fila, col };
    }

    // =========================
    // FICHAS
    // =========================
    private void crearFichas() {
        fichasVista.clear();

        ArrayList<Jugador> jugadores = gestorPartida.getPartida().getJugadores();

        for (int i = 0; i < jugadores.size(); i++) {
            Circle ficha = new Circle(12);
            ficha.setStroke(Color.BLACK);

            switch (i) {
                case 0 -> ficha.setFill(Color.web("#56ff1f"));
                case 1 -> ficha.setFill(Color.DODGERBLUE);
                case 2 -> ficha.setFill(Color.web("#ff1fce"));
                case 3 -> ficha.setFill(Color.web("#fff01f"));
                default -> ficha.setFill(Color.GRAY);
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
            } else if (j.getPosicion() < casillasVista.size()) {
                StackPane celda = casillasVista.get(j.getPosicion());

                ficha.setTranslateX((i % 2) * 18 - 9);
                ficha.setTranslateY((i / 2) * 18 - 9);

                celda.getChildren().add(ficha);

                if (j.getPosicion() == ultimaPosicionAnimada) {
                    animarFicha(ficha);
                }
            }
        }

        sortidaPane.getChildren().add(mini);
    }

    // =========================
    // DAUS
    // =========================
    @FXML
    private void handleDadoNormal(ActionEvent event) {
        if (animandoMovimiento) {
            return;
        }

        if (gestorPartida == null || gestorPartida.getPartida() == null) {
            return;
        }

        audio.reproducirEfecto("/audio/dados.mp3");
        jugarTurno();
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

        ejecutarUnTurno(() -> {
            if (!partida.estaFinalizada() && partida.getJugadorActual() instanceof Foca) {
                jugarTurno();
            }
        });
    }

    private void ejecutarUnTurno(Runnable alFinalizarTurno) {
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
        int destinoDado = calcularDestinoDado(antes, resultado, partida.getTablero().getTamano());

        partida.jugarTurno(resultado);
        int despues = jugador.getPosicion();

        consumirDadoEspecialSiHaceFalta(jugador, dadoElegido);

        if (dadoElegido instanceof Dado_rapido) {
            afegirMissatge(jugador.getNombre() + " usa dado rápido");
        } else if (dadoElegido instanceof Dado_lento) {
            afegirMissatge(jugador.getNombre() + " usa dado lento");
        }

        Casilla casillaDado = null;
        if (destinoDado >= 0 && destinoDado < partida.getTablero().getCasillas().size()) {
            casillaDado = partida.getTablero().getCasillas().get(destinoDado);
        }

        Casilla casillaFinal = null;
        if (despues >= 0 && despues < partida.getTablero().getCasillas().size()) {
            casillaFinal = partida.getTablero().getCasillas().get(despues);
        }

        ArrayList<Integer> rutaDado = construirRutaMovimiento(antes, resultado, partida.getTablero().getTamano());

        jugador.setPosicion(antes);
        ultimaPosicionAnimada = -1;
        sueloQuebradizoActivo = -1;
        actualizarPosicionesVisuales();

        Casilla casillaDadoRef = casillaDado;
        Casilla casillaFinalRef = casillaFinal;
        int posicionFinalReal = despues;

        animarMovimientoJugador(jugador, rutaDado, destinoDado, () -> {
            jugador.setPosicion(destinoDado);
            ultimaPosicionAnimada = destinoDado;
            generarTableroVisual();
            actualizarPosicionesVisuales();

            reproducirEfectoCasilla(casillaDadoRef);

            if (posicionFinalReal != destinoDado) {
                PauseTransition pausa = new PauseTransition(Duration.millis(450));
                pausa.setOnFinished(e -> animarDesplazamientoDirecto(jugador, destinoDado, posicionFinalReal, () -> {
                    finalizarVisualTurno(jugador, posicionFinalReal, casillaFinalRef, partida, alFinalizarTurno);
                }));
                pausa.play();
            } else {
                finalizarVisualTurno(jugador, posicionFinalReal, casillaFinalRef, partida, alFinalizarTurno);
            }
        });
    }

    private void finalizarVisualTurno(Jugador jugador, int posicionFinalReal, Casilla casillaFinalRef, Partida partida, Runnable alFinalizarTurno) {
        jugador.setPosicion(posicionFinalReal);
        ultimaPosicionAnimada = posicionFinalReal;

        sueloQuebradizoActivo = -1;
        if (casillaFinalRef instanceof SueloQuebradizo) {
            sueloQuebradizoActivo = posicionFinalReal;
        }

        generarTableroVisual();
        actualizarPosicionesVisuales();
        actualizarTooltipInventario();

        if (posicionFinalReal >= 0 && posicionFinalReal < casillasVista.size()) {
            animarCasillaDestino(casillasVista.get(posicionFinalReal));
        }

        if (partida.estaFinalizada()) {
            afegirMissatge("Ha ganado " + partida.getGanador().getNombre());
            dadoMenu.setDisable(true);
        } else {
            actualizarTextoTurno();
            actualizarOpcionesDados();
            dadoMenu.setDisable(false);
        }

        if (alFinalizarTurno != null) {
            alFinalizarTurno.run();
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

    // =========================
    // INVENTARI I TEXTOS
    // =========================
    private void afegirMissatge(String text) {
        if (eventos != null) {
            eventos.setText(text);
        }

        if (consolaEventos != null) {
            consolaEventos.appendText(text + "\n");
        }
    }

    private void mostrarNotificacion(String mensaje) {
        if (notificacionPane == null || notificacionText == null) {
            return;
        }

        notificacionText.setText(mensaje);
        notificacionPane.setOpacity(0);
        notificacionPane.setVisible(true);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(180), notificacionPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition pausa = new PauseTransition(Duration.seconds(1.1));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), notificacionPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> notificacionPane.setVisible(false));

        new SequentialTransition(fadeIn, pausa, fadeOut).play();
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

    private void actualizarOpcionesDados() {
        if (gestorPartida == null || gestorPartida.getPartida() == null) {
            return;
        }

        Jugador actual = gestorPartida.getPartida().getJugadorActual();

        if (!(actual instanceof Pinguino)) {
            opcionRapido.setDisable(true);
            opcionLento.setDisable(true);
            tipoDadoSeleccionado = "normal";
            dadoMenu.setText("Tirar dado");
            return;
        }

        Pinguino p = (Pinguino) actual;
        Inventario inv = p.getInventario();

        if (inv == null) {
            opcionRapido.setDisable(true);
            opcionLento.setDisable(true);
            tipoDadoSeleccionado = "normal";
            dadoMenu.setText("Tirar dado");
            return;
        }

        int dadosRapidos = 0;
        int dadosLentos = 0;

        for (Dado d : inv.getDado()) {
            if (d instanceof Dado_rapido) {
                dadosRapidos++;
            } else if (d instanceof Dado_lento) {
                dadosLentos++;
            }
        }

        opcionRapido.setDisable(dadosRapidos == 0);
        opcionLento.setDisable(dadosLentos == 0);

        if (tipoDadoSeleccionado.equalsIgnoreCase("rapido") && dadosRapidos == 0) {
            tipoDadoSeleccionado = "normal";
            dadoMenu.setText("Tirar dado");
        }

        if (tipoDadoSeleccionado.equalsIgnoreCase("lento") && dadosLentos == 0) {
            tipoDadoSeleccionado = "normal";
            dadoMenu.setText("Tirar dado");
        }
    }

    // =========================
    // MOVIMENT / ANIMACIO
    // =========================
    private int calcularDestinoDado(int inicio, int pasos, int tamano) {
        int posicion = inicio;
        int ultima = tamano - 1;
        boolean rebotando = false;

        for (int i = 0; i < pasos; i++) {
            if (!rebotando) {
                if (posicion < ultima) {
                    posicion++;
                } else {
                    rebotando = true;
                    posicion--;
                }
            } else {
                posicion--;
            }
        }

        return posicion;
    }

    private ArrayList<Integer> construirRutaMovimiento(int inicio, int pasos, int tamano) {
        ArrayList<Integer> ruta = new ArrayList<>();
        int posicion = inicio;
        int ultima = tamano - 1;
        boolean rebotando = false;

        for (int i = 0; i < pasos; i++) {
            if (!rebotando) {
                if (posicion < ultima) {
                    posicion++;
                } else {
                    rebotando = true;
                    posicion--;
                }
            } else {
                posicion--;
            }
            ruta.add(posicion);
        }

        return ruta;
    }

    private void animarMovimientoJugador(Jugador jugador, ArrayList<Integer> ruta, int posicionFinal, Runnable alFinalizar) {
        if (ruta == null || ruta.isEmpty()) {
            jugador.setPosicion(posicionFinal);
            if (alFinalizar != null) {
                alFinalizar.run();
            }
            return;
        }

        animandoMovimiento = true;
        dadoMenu.setDisable(true);

        SequentialTransition secuencia = new SequentialTransition();

        for (Integer paso : ruta) {
            PauseTransition pausa = new PauseTransition(Duration.millis(180));
            pausa.setOnFinished(e -> {
                jugador.setPosicion(paso);
                actualizarPosicionesVisuales();
            });
            secuencia.getChildren().add(pausa);
        }

        secuencia.setOnFinished(e -> {
            jugador.setPosicion(posicionFinal);
            animandoMovimiento = false;
            if (alFinalizar != null) {
                alFinalizar.run();
            }
        });

        secuencia.play();
    }

    private void animarDesplazamientoDirecto(Jugador jugador, int origen, int destino, Runnable alFinalizar) {
        if (origen < 0 || destino < 0 || origen >= casillasVista.size() || destino >= casillasVista.size()) {
            jugador.setPosicion(destino);
            if (alFinalizar != null) {
                alFinalizar.run();
            }
            return;
        }

        animandoMovimiento = true;
        dadoMenu.setDisable(true);

        Point2D puntoOrigen = obtenerCentroCasilla(origen);
        Point2D puntoDestino = obtenerCentroCasilla(destino);

        Circle fichaTemporal = crearFichaTemporal(jugador);
        fichaTemporal.setLayoutX(puntoOrigen.getX());
        fichaTemporal.setLayoutY(puntoOrigen.getY());

        capaAnimacion.getChildren().add(fichaTemporal);

        jugador.setPosicion(-999);
        actualizarPosicionesVisuales();

        TranslateTransition tt = new TranslateTransition(Duration.millis(350), fichaTemporal);
        tt.setByX(puntoDestino.getX() - puntoOrigen.getX());
        tt.setByY(puntoDestino.getY() - puntoOrigen.getY());

        tt.setOnFinished(e -> {
            capaAnimacion.getChildren().remove(fichaTemporal);
            animandoMovimiento = false;
            if (alFinalizar != null) {
                alFinalizar.run();
            }
        });

        tt.play();
    }

    private Circle crearFichaTemporal(Jugador jugador) {
        int index = gestorPartida.getPartida().getJugadores().indexOf(jugador);
        Circle ficha = new Circle(12);
        ficha.setStroke(Color.BLACK);

        switch (index) {
            case 0 -> ficha.setFill(Color.web("#56ff1f"));
            case 1 -> ficha.setFill(Color.DODGERBLUE);
            case 2 -> ficha.setFill(Color.web("#ff1fce"));
            case 3 -> ficha.setFill(Color.web("#fff01f"));
            default -> ficha.setFill(Color.GRAY);
        }

        return ficha;
    }

    private Point2D obtenerCentroCasilla(int indice) {
        StackPane celda = casillasVista.get(indice);
        Bounds boundsScene = celda.localToScene(celda.getBoundsInLocal());
        Point2D centroScene = new Point2D(
                boundsScene.getMinX() + boundsScene.getWidth() / 2,
                boundsScene.getMinY() + boundsScene.getHeight() / 2
        );
        return capaAnimacion.sceneToLocal(centroScene);
    }

    private void reproducirEfectoCasilla(Casilla casilla) {
        if (casilla == null) {
            return;
        }

        if (casilla instanceof Oso) {
            audio.reproducirEfecto("/audio/oso.mp3");
            mostrarNotificacion("UN OSO TE HA ATACADO");
        } else if (casilla instanceof Agujero) {
            audio.reproducirEfecto("/audio/agujero.mp3");
            mostrarNotificacion("HAS CAIDO EN UN AGUJERO");
        } else if (casilla instanceof Trineo) {
            audio.reproducirEfecto("/audio/trineo.mp3");
            mostrarNotificacion("UN TRINEO TE IMPULSA");
        } else if (casilla instanceof SueloQuebradizo) {
            audio.reproducirEfecto("/audio/sueloQuebradizo.mp3");
            mostrarNotificacion("EL HIELO SE HA ROTO");
        } else if (casilla instanceof Interrogante) {
            audio.reproducirEfecto("/audio/exclamacion.mp3");
            mostrarNotificacion("CASILLA SORPRESA");
        }
    }

    // =========================
    // ANIMACIONS VISUALS
    // =========================
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