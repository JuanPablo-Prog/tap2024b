package com.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ClienteDAO {
    private int id_cliente;
    private String nombreCliente;
    private String telCte;
    private String emailCte;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelCte() {
        return telCte;
    }

    public void setTelCte(String telCte) {
        this.telCte = telCte;
    }

    public String getEmailCte() {
        return emailCte;
    }

    public void setEmailCte(String emailCte) {
        this.emailCte = emailCte;
    }


    public void INSERT() {
        String query = "INSERT INTO tblCliente(nombreCliente,telCte,emailCte)" +
                " VALUES('" + this.nombreCliente + "','" + this.telCte + "','" + this.emailCte + "')";

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void UPDATE() {

        String query = "UPDATE tblCliente SET nombreCliente = '" + this.nombreCliente + "'," + "telCte = '" + this.telCte + "', emailCte = '" + this.emailCte + "' WHERE id_cliente = " + this.id_cliente;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM tblCliente WHERE id_cliente =" + this.id_cliente;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ObservableList<ClienteDAO> SELECTALL() {
        ClienteDAO objCte;
        String query = "SELECT * FROM tblCliente";
        ObservableList<ClienteDAO> listaC = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objCte = new ClienteDAO();
                objCte.id_cliente = res.getInt(0);
                objCte.nombreCliente = res.getString(1);
                objCte.telCte = res.getString(2);
                objCte.emailCte = res.getString(3);
                listaC.add(objCte);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaC;
    }

}