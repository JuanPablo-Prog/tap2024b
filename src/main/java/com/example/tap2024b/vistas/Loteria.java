package com.example.tap2024b.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.*;

public class Loteria extends Stage {
    private HBox hboxmain, hboxbuttons;
    private VBox vbxTablilla, vbxMazo;
    private Button btnAnterior, btnSiguiente, btnIniciar, btnReiniciar;
    private Label lblTimer, lbltbl, lblResultado;
    private GridPane gdpTablilla;
    private ImageView imvMazo;
    private Scene escena;
    private String[] arImages = {"barril.jpeg", "botella.jpeg", "catrin.jpeg",
            "chavorruco.jpeg", "concha.jpeg", "luchador.jpeg", "tacos.jpeg", "venado.jpeg",
            "maceta.jpeg", "rosa.jpeg", "chapulin.jpg", "borracha.jpg", "chabe.jpg", "chila.jpg",
            "coca.jpg", "copete.jpg", "cora.jpg", "diablito.jpg", "godin.jpg", "guaja.jpg",
            "joker.jpg", "la mano.jpg", "lady.jpg", "luchador.jpg", "mango.jpg", "maria.jpg",
            "mezcal.jpg", "miche.jpg", "mini.jpg", "mirrey.jpg", "muñeco.jpg", "niño.jpg",
            "nov.jpg", "peda.jpg", "pikachu.jpg", "quince.jpg", "sirena.jpg", "soldado.jpg",
            "suegra.jpg", "tamales.jpg", "toxi.jpg", "uber.jpg"};
    private Button[][] arbtns;
    private Panel pnlPrincipal;

    private ArrayList<String[][]> tablillasCreadas;  // Lista para almacenar las tablillas
    private int currentTablillaIndex; // Índice de la tablilla actual
    private int maxTablillas = 5; // Máximo de 5 tablillas

    private List<String> mazo; // Lista de cartas del mazo
    private Set<String> cartasReveladas; // Cartas que han sido reveladas
    private Set<String> cartasMarcadas; // Cartas que el usuario ha marcado
    private Timeline timelineMazo; // Timeline para revelar cartas cada 5 segundos
    private boolean juegoEnCurso = false; // Para saber si el juego está en curso

    public Loteria() {
        CrearUI();
        this.setTitle("Lotería Mexicana :D");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvbtnAnt, imvbtnSig;
        imvbtnAnt = new ImageView(new Image(getClass().getResource("/images_loteria/anteriorloteria.png").toString()));
        imvbtnSig = new ImageView(new Image(getClass().getResource("/images_loteria/sigloteria.png").toString()));
        imvbtnAnt.setFitHeight(50);
        imvbtnAnt.setFitWidth(50);
        imvbtnSig.setFitHeight(50);
        imvbtnSig.setFitWidth(50);

        gdpTablilla = new GridPane();
        vbxTablilla = new VBox();

        // Inicializamos los botones y el HBox antes de añadirlos al vbxTablilla
        btnAnterior = new Button();
        btnAnterior.setGraphic(imvbtnAnt);
        btnAnterior.setOnAction(actionEvent -> mostrarTablillaAnterior());

        btnSiguiente = new Button();
        btnSiguiente.setGraphic(imvbtnSig);
        btnSiguiente.setOnAction(actionEvent -> mostrarTablillaSiguiente());

        lbltbl = new Label("1");
        lbltbl.setId("lbltablilla");
        lbltbl.setMinWidth(200);

        hboxbuttons = new HBox(btnAnterior, lbltbl, btnSiguiente);
        hboxbuttons.setAlignment(Pos.CENTER); // Centramos los botones

        // Añadimos los elementos iniciales al vbxTablilla
        vbxTablilla.getChildren().addAll(hboxbuttons, gdpTablilla);
        vbxTablilla.setAlignment(Pos.CENTER); // Centramos el contenido
        vbxTablilla.setSpacing(10);

        // Creamos todas las tablillas al inicio
        generarTodasLasTablillas();

        // Mostramos la primera tablilla
        mostrarTablilla(tablillasCreadas.get(currentTablillaIndex));

        CrearMazo();

        hboxmain = new HBox(vbxTablilla, vbxMazo);
        pnlPrincipal = new Panel("Lotería Mexicana :)");
        pnlPrincipal.getStyleClass().add("panel-success");
        pnlPrincipal.setId("Titulo");
        pnlPrincipal.setBody(hboxmain);
        hboxmain.setSpacing(20);
        hboxmain.setPadding(new Insets(20));
        hboxmain.setAlignment(Pos.CENTER); // Centramos el contenido

        escena = new Scene(pnlPrincipal, 1000, 650);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        escena.getStylesheets().add(getClass().getResource("/styles/loteria.css").toExternalForm());
    }

    private void generarTodasLasTablillas() {
        tablillasCreadas = new ArrayList<>();
        currentTablillaIndex = 0;
        // Creamos todas las tablillas al inicio
        for (int i = 0; i < maxTablillas; i++) {
            String[][] nuevaTablilla = generarTablillaAleatoria();
            tablillasCreadas.add(nuevaTablilla);
        }
    }

    private void CrearMazo() {
        // Label para mostrar el resultado
        lblResultado = new Label();
        lblResultado.setWrapText(true);
        lblResultado.setStyle("-fx-font-size: 16px; -fx-text-fill: red; -fx-font-weight: bold;");

        lblTimer = new Label("00:00");
        lblTimer.setMinWidth(150);

        lblTimer.setId("lbltablilla");
        lblTimer.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Agrupamos lblResultado y lblTimer en un HBox
        HBox hboxResultadoTimer = new HBox(lblResultado, lblTimer);
        hboxResultadoTimer.setSpacing(20);
        hboxResultadoTimer.setAlignment(Pos.CENTER);

        imvMazo = new ImageView((getClass().getResource("/images_loteria/dorso.jpeg").toString()));
        imvMazo.setFitHeight(300);
        imvMazo.setFitWidth(200);

        btnIniciar = new Button("Iniciar Mazo");
        btnIniciar.setId("botoncillo");
        btnIniciar.getStyleClass().setAll("btn-sml", "btn-warning");
        btnIniciar.setPrefSize(150, 50);

        btnIniciar.setOnAction(e -> iniciarJuego()); // Acción al presionar "Iniciar"

        // Botón "Reiniciar Juego"
        btnReiniciar = new Button("Reiniciar Juego");
        btnReiniciar.getStyleClass().setAll("btn-sml", "btn-warning");
        btnReiniciar.setId("botoncillo");
        btnReiniciar.setPrefSize(150, 50);

        btnReiniciar.setOnAction(e -> reiniciarJuego()); // Acción al presionar "Reiniciar"

        HBox hboxBotonesMazo = new HBox(btnIniciar, btnReiniciar);
        hboxBotonesMazo.setSpacing(20);
        hboxBotonesMazo.setAlignment(Pos.CENTER);

        vbxMazo = new VBox(hboxResultadoTimer, imvMazo, hboxBotonesMazo);
        vbxMazo.setPadding(new Insets(20));
        vbxMazo.setSpacing(20);
        vbxMazo.setAlignment(Pos.CENTER);
    }

    private String[][] generarTablillaAleatoria() {
        ArrayList<String> shuffledImages = new ArrayList<>(Arrays.asList(arImages));
        Collections.shuffle(shuffledImages);

        String[][] tablilla = new String[4][4];
        int imageIndex = 0;

        // Llenamos la tablilla con imágenes aleatorias
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tablilla[i][j] = shuffledImages.get(imageIndex);
                imageIndex++;
            }
        }
        return tablilla;
    }

    private void mostrarTablilla(String[][] tablilla) {
        gdpTablilla.getChildren().clear(); // Limpiamos el GridPane actual
        gdpTablilla.setAlignment(Pos.CENTER); // Centramos el GridPane
        gdpTablilla.setHgap(5);
        gdpTablilla.setVgap(5);

        arbtns = new Button[4][4]; // Inicializamos el arreglo de botones
        // Añadimos las imágenes al GridPane
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Image img = new Image(getClass().getResource("/images_loteria/" + tablilla[i][j]).toString());
                ImageView imv = new ImageView(img);
                imv.setFitWidth(90);
                imv.setFitHeight(110);
                Button btn = new Button();
                btn.setGraphic(imv);

                final String carta = tablilla[i][j]; // Necesario para el manejo de eventos

                // Evento al presionar el botón de la carta
                btn.setOnAction(e -> {
                    if (juegoEnCurso) {
                        // Marcamos la carta y deshabilitamos el botón
                        btn.setDisable(true);
                        cartasMarcadas.add(carta);

                        // Verificamos si el usuario ha marcado todas las cartas de su tablilla
                        if (cartasMarcadas.size() == 16) {
                            // El usuario ha marcado todas sus cartas
                            // Detenemos el juego y verificamos si ganó o perdió
                            terminarJuego(false); // mazoFinalizado = false
                        }
                    }
                });

                arbtns[i][j] = btn; // Guardamos el botón en el arreglo
                gdpTablilla.add(btn, j, i);
            }
        }
        // Actualizamos la interfaz
        vbxTablilla.getChildren().set(1, gdpTablilla); // Aseguramos que estamos modificando el VBox correctamente
    }

    private void mostrarTablillaAnterior() {
        // Ciclo a la tablilla anterior (si estamos en la primera, vamos a la última)
        currentTablillaIndex = (currentTablillaIndex - 1 + maxTablillas) % maxTablillas;
        String[][] anteriorTablilla = tablillasCreadas.get(currentTablillaIndex);

        // Mostramos la tablilla anterior
        mostrarTablilla(anteriorTablilla);
        lbltbl.setText(String.valueOf(currentTablillaIndex + 1));
    }

    private void mostrarTablillaSiguiente() {
        // Ciclo a la siguiente tablilla (si estamos en la última, volvemos a la primera)
        currentTablillaIndex = (currentTablillaIndex + 1) % maxTablillas;
        String[][] siguienteTablilla = tablillasCreadas.get(currentTablillaIndex);

        // Mostramos la siguiente tablilla
        mostrarTablilla(siguienteTablilla);
        lbltbl.setText(String.valueOf(currentTablillaIndex + 1));
    }

    private void iniciarJuego() {
        // Limpiamos el mensaje de resultado
        lblResultado.setText("");

        // Deshabilitamos los botones para cambiar de tablilla
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);

        // Deshabilitamos el botón "Iniciar Mazo"
        btnIniciar.setDisable(true);

        // Inicializamos las estructuras de datos
        cartasReveladas = new HashSet<>();
        cartasMarcadas = new HashSet<>();
        juegoEnCurso = true;

        // Creamos y barajamos el mazo
        mazo = new ArrayList<>(Arrays.asList(arImages));
        Collections.shuffle(mazo);

        // Iniciamos la cuenta regresiva de 5 segundos antes de iniciar el mazo
        IntegerProperty secondsLeft = new SimpleIntegerProperty(5);
        lblTimer.textProperty().bind(secondsLeft.asString("00:%02d"));
        Timeline countdown = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    secondsLeft.set(secondsLeft.get() - 1);
                })
        );
        countdown.setCycleCount(5);
        countdown.setOnFinished(event -> {
            lblTimer.textProperty().unbind();
            iniciarMazo();
        });
        countdown.play();
    }


    private void iniciarMazo() {
        final Iterator<String> iterMazo = mazo.iterator();

        timelineMazo = new Timeline();
        timelineMazo.setCycleCount(Timeline.INDEFINITE);

        IntegerProperty secondsLeft = new SimpleIntegerProperty(0); // Iniciar en 0 para mostrar la primera carta inmediatamente
        lblTimer.textProperty().bind(secondsLeft.asString("00:%02d"));

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            if (secondsLeft.get() > 0) {
                secondsLeft.set(secondsLeft.get() - 1);
            } else {
                if (iterMazo.hasNext()) {
                    String cartaActual = iterMazo.next();
                    cartasReveladas.add(cartaActual);

                    // Mostramos la carta en el mazo
                    Image img = new Image(getClass().getResource("/images_loteria/" + cartaActual).toString());
                    imvMazo.setImage(img);

                    // Reiniciamos el contador
                    secondsLeft.set(5);
                } else {
                    // Se acabaron las cartas
                    terminarJuego(true); // mazoFinalizado = true
                    timelineMazo.stop();
                    lblTimer.textProperty().unbind();
                    lblTimer.setText("00:00");
                }
            }
        });

        timelineMazo.getKeyFrames().add(keyFrame);
        timelineMazo.play();
    }

    private void terminarJuego(boolean mazoFinalizado) {
        juegoEnCurso = false;

        // Detenemos el timeline del mazo si está en ejecución
        if (timelineMazo != null) {
            timelineMazo.stop();
        }

        // Obtener las cartas de la tablilla actual
        String[][] tablillaActual = tablillasCreadas.get(currentTablillaIndex);
        Set<String> cartasTablilla = new HashSet<>();
        for (int i = 0; i < tablillaActual.length; i++) {
            for (int j = 0; j < tablillaActual[i].length; j++) {
                cartasTablilla.add(tablillaActual[i][j]);
            }
        }

        // Verificar si el usuario marcó cartas que no han sido reveladas (error)
        boolean marcadasNoReveladas = false;
        for (String carta : cartasMarcadas) {
            if (!cartasReveladas.contains(carta)) {
                marcadasNoReveladas = true;
                break;
            }
        }

        // Verificar si quedan cartas de su tablilla por salir en el mazo
        Set<String> cartasFaltantes = new HashSet<>(cartasTablilla);
        cartasFaltantes.removeAll(cartasReveladas);

        String mensaje;

        if (cartasMarcadas.size() == 16) {
            // El usuario llenó su tabla
            if (cartasFaltantes.isEmpty()) {
                // No quedan cartas por pasar en el mazo, gana
                mensaje = "¡Felicidades! Has ganado el juego.";
            } else {
                // Quedan cartas por pasar en el mazo, pierde
                mensaje = "¡Has perdido! Quedaban cartas de tu tabla por salir en el mazo.";
            }
        } else if (mazoFinalizado) {
            // Se acabó el mazo y el usuario no llenó su tabla
            mensaje = "¡Has perdido! No lograste llenar tu tabla.";
        } else if (marcadasNoReveladas) {
            // El usuario marcó cartas que no habían sido reveladas
            mensaje = "¡Has perdido! Marcaste cartas que no habían salido.";
        } else {
            // Otro caso (debería ser inalcanzable)
            mensaje = "El juego ha terminado.";
        }

        // Mostrar el mensaje en el Label
        lblResultado.setText(mensaje);

        // El usuario puede presionar "Reiniciar Juego" para empezar de nuevo
    }

    private void reiniciarJuego() {
        // Reiniciamos todas las variables y estados
        if (timelineMazo != null) {
            timelineMazo.stop();
        }

        juegoEnCurso = false;

        // Limpiamos el mensaje de resultado
        lblResultado.setText("");

        // Habilitamos los botones para cambiar de tablilla
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);

        // **Habilitamos el botón "Iniciar Mazo"**
        btnIniciar.setDisable(false);

        // Reiniciamos el timer
        lblTimer.textProperty().unbind();
        lblTimer.setText("00:00");

        // Restauramos la imagen del mazo
        imvMazo.setImage(new Image(getClass().getResource("/images_loteria/dorso.jpeg").toString()));

        // Generamos nuevas tablillas
        generarTodasLasTablillas();
        mostrarTablilla(tablillasCreadas.get(currentTablillaIndex));

        // Habilitamos todos los botones de la tablilla
        Platform.runLater(() -> {
            for (Button[] fila : arbtns) {
                for (Button btn : fila) {
                    btn.setDisable(false);
                }
            }
        });
    }

}
