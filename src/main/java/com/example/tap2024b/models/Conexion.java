package com.example.tap2024b.models;

import java.sql.DriverManager;
import java.sql.Connection;

public class Conexion {
    static private String DB = "spotify";
    static private String user = "admin";
    static private String pass = "1507";
    static private String host = "localhost";
    static private String port = "3306";
    public static Connection connection;

    public static void crearConexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+DB,user,pass);
            System.out.println("conexion establecida :D");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}