package Vistas;

import java.util.ArrayList;

import Controladores.GestorPartida;
import Modelos.Agujero;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class PantallaJuego {

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
        return celda;
    }

    private String estiloCasilla(Casilla casilla) {
        String base = "-fx-background-radius: 8; "
                + "-fx-border-radius: 8; "
                + "-fx-border-color: rgba(60,60,60,0.35); "
                + "-fx-border-width: 1;";

        if (casilla instanceof Oso) {
            return "-fx-background-color: #fb8500; " + base;
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
            return "-fx-background-color: #b0bec5; " + base;
        }

        return "-fx-background-color: #f5fdff; " + base;
    }

    private void crearFichas() {
        fichasVista.clear();

        ArrayList<Jugador> jugadores = gestorPartida.getPartida().getJugadores();

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);

            Circle ficha = new Circle(12);
            ficha.setStroke(Color.BLACK);

            if (jugador instanceof Foca) {
                ficha.setFill(Color.GRAY);
            } else {
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
                        ficha.setFill(Color.WHITE);
                        break;
                }
            }

            fichasVista.add(ficha);
        }
    }

    private void actualizarPosicionesVisuales() {
        for (StackPane celda : casillasVista) {
            celda.getChildren().removeIf(node -> node instanceof Circle);
        }

        sortidaPane.getChildren().clear();

        GridPane miniSalida = new GridPane();
        miniSalida.setHgap(6);
        miniSalida.setVgap(6);
        miniSalida.setAlignment(Pos.CENTER);

        ArrayList<Jugador> jugadores = gestorPartida.getPartida().getJugadores();

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            Circle ficha = fichasVista.get(i);

            if (jugador.getPosicion() < 0) {
                miniSalida.add(ficha, i % 2, i / 2);
            } else {
                casillasVista.get(jugador.getPosicion()).getChildren().add(ficha);
            }
        }

        sortidaPane.getChildren().add(miniSalida);
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

        int posicionAntes = jugador.getPosicion();
        partida.jugarTurno(resultado);
        int posicionDespues = jugador.getPosicion();

        // consumir dado especial si se ha usado
        consumirDadoEspecialSiHaceFalta(jugador, dadoElegido);

        eventos.setText(jugador.getNombre() + " pasa de " + posicionAntes + " a " + posicionDespues);

        actualizarPosicionesVisuales();
        actualizarTooltipInventario();

        if (partida.estaFinalizada()) {
            eventos.setText("Ha ganado " + partida.getGanador().getNombre());
            dadoMenu.setDisable(true);
        } else {
            actualizarTextoTurno();
        }
    }

    private void consumirDadoEspecialSiHaceFalta(Jugador jugador, Dado dadoUsado) {
        if (!(jugador instanceof Pinguino) || dadoUsado == null) {
            return;
        }

        Pinguino p = (Pinguino) jugador;

        // el dado normal no se consume
        if (!(dadoUsado instanceof Dado_rapido) && !(dadoUsado instanceof Dado_lento)) {
            return;
        }

        p.getInventario().eliminarDado(dadoUsado);

        // volver al normal por defecto después de usar uno especial
        tipoDadoSeleccionado = "normal";
        dadoMenu.setText("Tirar dado");
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
}