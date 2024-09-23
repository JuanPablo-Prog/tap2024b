package com.example.tap2024b;

import com.example.tap2024b.vistas.Loteria;
import com.example.tap2024b.vistas.calculadora;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    private BorderPane bdPrincipal;

    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2, menSalir;
    private MenuItem mitCalculadora, mitLoteria;


    public void CrearUI() {
        mitLoteria = new MenuItem("Loteria");
        mitLoteria.setOnAction(actionEvent -> new Loteria());
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(actionEvent -> new calculadora());
        menCompetencia1 = new Menu("Competencia1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitLoteria);
        mnbPrincipal = new MenuBar(menCompetencia1);
        bdPrincipal = new BorderPane();
        bdPrincipal.setTop(mnbPrincipal);
    }


    @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        CrearUI();
        Scene scene = new Scene(bdPrincipal,320, 240);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String... args) {
        launch();
    }
}