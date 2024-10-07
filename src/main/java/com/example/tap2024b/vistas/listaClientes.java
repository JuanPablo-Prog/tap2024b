package com.example.tap2024b.vistas;

import com.example.tap2024b.models.ClienteDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class listaClientes extends Stage {
    private TableView<ClienteDAO> tbvClientes;
    private Button btnAgregar;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    public listaClientes(){
        CrearUI();
        this.setTitle("lista de clientes :D");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        tlbMenu = new ToolBar();
        tbvClientes = new TableView<>();
        vBox = new VBox(tlbMenu, tbvClientes);
        escena = new Scene(vBox, 500, 250);

    }
}