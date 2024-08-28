package com.example.tap2024b;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private VBox vBox;
    private HBox hBox;


    public void CrearUI(){
        btn1 = new Button("Boton 1");
        btn2 = new Button("Boton 2");
        btn3 = new Button("Boton 3");

        vBox = new VBox(btn1, btn2, btn3);

        hBox = new HBox(btn1, btn2, btn3);
    }


    @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        CrearUI();
        Scene scene = new Scene(hBox), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}