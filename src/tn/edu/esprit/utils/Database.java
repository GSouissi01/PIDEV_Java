/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ghada
 */
public class Database {
        private final  String URL="jdbc:mysql://localhost:3306/pidev";
    private final String LOGIN="root";
    private final String PWD="";
    Connection cnx;
    private static Database instance;
    public Database(){
        try {
            cnx=DriverManager.getConnection(URL, LOGIN, PWD);
            System.out.println("Connecte!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }       
    }
    public static Database getInstance() {
        if(instance == null)    
            instance = new Database();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
