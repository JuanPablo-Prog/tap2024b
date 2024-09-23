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

    public calculadora() {
        CrearUI();
        this.setTitle("Calculadora");

        this.setScene(escena);
        this.show();
    }

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

    private void detectarTecla(String tecla) {
        if ("0123456789.".contains(tecla)) {
            aggNum(tecla);
        } else if (tecla.equals("-") && puedeAgregarSignoNegativo()) {
            aggNum(tecla);
        } else if ("+-*/".contains(tecla)) {
            aggOperador(tecla);
        } else if ("=".equals(tecla)) {
            calcResu();
        }
    }

    private boolean puedeAgregarSignoNegativo() {
        if (!esSegNum && num1.length() == 0 && opdr.isEmpty()) {
            return true;
        } else if (esSegNum && num2.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void aggNum(String tecla) {
        if (tecla.equals("-")) {
            // Solo permitimos agregar "-" al inicio del número
            if (esSegNum) {
                if (num2.length() == 0) {
                    num2.append(tecla);
                    txtPantalla.setText(num1.toString() + opdr + num2.toString());
                }
            } else {
                if (num1.length() == 0) {
                    num1.append(tecla);
                    txtPantalla.setText(num1.toString());
                }
            }
            return;
        }

        // Agregar "0." si se inicia con un punto decimal
        if (tecla.equals(".") &&
                ((esSegNum && num2.length() == 0) ||
                        (!esSegNum && num1.length() == 0))) {
            tecla = "0.";
        }

        // Evitar múltiples puntos decimales
        if (tecla.equals(".") &&
                ((esSegNum && num2.toString().contains(".")) ||
                        (!esSegNum && num1.toString().contains(".")))) {
            return;
        }

        // Agregar dígitos al número correspondiente
        if (esSegNum) {
            num2.append(tecla);
            txtPantalla.setText(num1.toString() + opdr + num2.toString());
        } else {
            num1.append(tecla);
            txtPantalla.setText(num1.toString());
        }
    }

    private void aggOperador(String tecla) {
        if (!esSegNum && num1.length() > 0) {
            opdr = tecla;
            esSegNum = true;
            txtPantalla.setText(num1.toString() + opdr);
        }
    }

    private boolean esNumeroValido(String numStr) {
        if (numStr == null || numStr.isEmpty()) {
            return false;
        }
        int len = numStr.length();
        int i = 0;
        if (numStr.charAt(0) == '-') {
            if (len == 1) {
                return false; // Solo un signo negativo no es válido
            }
            i = 1;
        }
        boolean tieneDigito = false;
        boolean tienePuntoDecimal = false;
        for (; i < len; i++) {
            char c = numStr.charAt(i);
            if (c >= '0' && c <= '9') {
                tieneDigito = true;
            } else if (c == '.') {
                if (tienePuntoDecimal) {
                    return false; // Múltiples puntos decimales
                }
                tienePuntoDecimal = true;
            } else {
                return false; // Caracter inválido
            }
        }
        return tieneDigito;
    }

    private void calcResu() {
        if (num1.length() > 0 && num2.length() > 0 && !opdr.isEmpty()) {
            if (!esNumeroValido(num1.toString()) || !esNumeroValido(num2.toString())) {
                txtPantalla.setText("Error: Número inválido");
                return;
            }
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
                        txtPantalla.setText("Error: División entre 0");
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
