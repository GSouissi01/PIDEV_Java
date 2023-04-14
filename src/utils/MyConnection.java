/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author belkn
 */
public class MyConnection {
private Connection conn;
static MyConnection instance;
String url = "jdbc:mysql://localhost:3306/pidev";
String user="root";
String pwd="";
    private MyConnection() {
        
    try {
        conn=DriverManager.getConnection(url, user, pwd);
        
        System.out.println("Connection établie!!");
    } catch (SQLException ex) {
        System.out.println("Probleme de connection");    
    }
    } 
        
     
    public static MyConnection getInstance(){
        if(instance==null){
            instance=new MyConnection();
        }
        return instance;
        }

    public Connection getConn() {
        return conn;
    }
    
    
    }
    
    
    
    
    

