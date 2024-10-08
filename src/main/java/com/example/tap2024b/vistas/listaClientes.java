package com.example.tap2024b.vistas;

import com.example.tap2024b.models.ClienteDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        ImageView imv = new ImageView(getClass().getResource("/images_loteria/sigloteria.png").toString());
        Button btnAddCliente = new Button();
        btnAddCliente.setOnAction(actionEvent -> new FormCliente());
        btnAddCliente.setGraphic(imv);
        tlbMenu.getItems().add(btnAddCliente);
        tbvClientes = new TableView<>();
        vBox = new VBox(tlbMenu, tbvClientes);
        escena = new Scene(vBox, 500, 250);

    }
}
