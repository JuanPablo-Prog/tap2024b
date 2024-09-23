package com.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class calculadora extends Stage {
    private Button[][] arBtns;
    private Button btnC;
    private TextField txtPantalla;
    private GridPane gdpTeclado;
    private VBox vBox;
    private Scene escena;
    private String[] strTeclas = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "+", "0", ".", "=", "-"};

    private String opdr = "";
    private StringBuilder num1 = new StringBuilder();
    private StringBuilder num2 = new StringBuilder();
    private boolean esSegNum = false;

    private void CrearUI() {
        arBtns = new Button[4][4];
        txtPantalla = new TextField("0");
        txtPantalla.setPrefHeight(55);
        txtPantalla.setAlignment(Pos.CENTER_RIGHT);
        txtPantalla.setEditable(false);
        gdpTeclado = new GridPane();
        CrearTeclado();

        // Crear botón de borrar
        btnC = new Button("Clear");
        btnC.setId("font-button");
        btnC.setOnAction(actionEvent -> {
            txtPantalla.clear();
            txtPantalla.setText("0");
            num1.setLength(0);
            num2.setLength(0);
            opdr = "";
            esSegNum = false;
        });

        vBox = new VBox(txtPantalla, gdpTeclado, btnC);
        escena = new Scene(vBox, 300, 400);
        escena.getStylesheets().add(getClass().getResource("/styles/cal.css").toExternalForm());
    }

    private void CrearTeclado() {
        for (int i = 0; i < arBtns.length; i++) {
            for (int j = 0; j < arBtns[i].length; j++) {
                arBtns[j][i] = new Button(strTeclas[4 * i + j]);
                arBtns[j][i].setPrefSize(100, 80);
                int finalI = i;
                int finalJ = j;
                arBtns[j][i].setOnAction(actionEvent -> detectarTecla(strTeclas[4 * finalI + finalJ]));
                gdpTeclado.add(arBtns[j][i], j, i);
            }
        }
    }

    public calculadora() {
        CrearUI();
        this.setTitle("Calculadorsita bonita");

        this.setScene(escena);
        this.show();
    }

    private void detectarTecla(String tecla) {
        if ("0123456789.".contains(tecla)) { // cuando ingresamos un numero o un punto decimal
            aggNum(tecla);
        } else if ("+-*/".contains(tecla)) { // cuando se ingresa un operador
            aggOperador(tecla);
        } else if ("=".equals(tecla)) { // Si se presiona el boton de "="
            calcResu();
        }
    }

    private void aggNum(String tecla) {
        // Si se ingresa primero el punto decimal jaja se le pone un 0 automaticamente
        if (tecla.equals(".") &&
                ((esSegNum && num2.length() == 0) ||
                        (!esSegNum && num1.length() == 0))) {
            tecla = "0.";
        }

        /*ahora verificamos que el punto decimal se quiera ingresar de forma correcta :D y que no se pueda
        ingresar mas de un punto seguidos*/
        if (tecla.equals(".") &&
                ((esSegNum && num2.toString().contains(".")) ||
                        (!esSegNum && num1.toString().contains(".")))) {
            return;
        }

        if (esSegNum) {
            num2.append(tecla);
            txtPantalla.setText(num1.toString() + opdr + num2.toString());
        } else {
            num1.append(tecla);
            txtPantalla.setText(num1.toString());
        }
    }

    // para que no se ingrese un operador si aun no ponemos un segundo numero jaja
    private void  aggOperador(String tecla) {

        if (!esSegNum && num1.length() > 0) {
            opdr = tecla;
            esSegNum = true;
            txtPantalla.setText(num1.toString() + opdr);
        }
    }

    private void  calcResu() {
        // Verificar si num2 es solo un punto decimal o comienza con un punto sin dígitos adicionales
        if (num2.toString().equals(".") || (num2.length() == 1 && num2.charAt(0) == '.')) {
            txtPantalla.setText("Error: .");
            return;
        }

        if (num1.length() > 0 && num2.length() > 0 && !opdr.isEmpty()) {
            double rstdo = 0;
            double nro1 = Double.parseDouble(num1.toString());
            double nro2 = Double.parseDouble(num2.toString());
            switch (opdr) {
                case "-":
                    rstdo = nro1 - nro2;
                    break;

                case "+":
                    rstdo = nro1 + nro2;
                    break;

                case "*":
                    rstdo = nro1 * nro2;
                    break;
                case "/":
                    if (nro2 == 0) {
                        txtPantalla.setText("Entre 0 no amigo");
                        return;
                    }
                    rstdo = nro1 / nro2;
                    break;
            }
            txtPantalla.setText(String.valueOf(rstdo));
            num1.setLength(0);
            num1.append(rstdo);
            num2.setLength(0);
            opdr = "";
            esSegNum = false;
        }
    }
}
