/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Client;
import entity.Credit;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tools.MyDB;

/**
 *
 * @author Administrateur
 */
public class CreditService implements ICreditService{
          Connection connexion;   
  public CreditService() {
        connexion = MyDB.getInstance().getConnection();
    }
  
    
  @Override
  public void ajouterCredit(Credit e) throws SQLException {
        String req = "INSERT INTO `credit` (`client_id`,`valeur`,`date`) "
                + "VALUES (?,?,?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setInt(1, e.getClient_id());
        ps.setFloat(2, e.getValeur());
        ps.setDate(3,(java.sql.Date) (Date)  e.getDate());

  
        ps.executeUpdate();
    }
@Override
     public List<Credit> AfficherCredit() throws SQLException {
        List<Credit> Credits = new ArrayList<>();
        String req = "select * from credit ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);
        while (rst.next()) {
            Credit e = new Credit(rst.getInt("id")
                    , rst.getInt("client_id")
                    , rst.getFloat("valeur")
                    , rst.getDate("date")
                  
            );
            Credits.add(e);
        }
        return Credits;
    }
          public List<Credit> AfficherCreditByClients() throws SQLException {
        List<Credit> Credits = new ArrayList<>();
        String req = "select * from credit order by client_id ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);
        while (rst.next()) {
            Credit e = new Credit(rst.getInt("id")
                    , rst.getInt("client_id")
                    , rst.getFloat("valeur")
                    , rst.getDate("date")
                  
            );
            Credits.add(e);
        }
        return Credits;
    }
     
      public List<Credit> AfficherCreditParValeur() throws SQLException {
        List<Credit> Credits = new ArrayList<>();
        String req = "select * from credit order by valeur ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);
        while (rst.next()) {
            Credit e = new Credit(rst.getInt("id")
                    , rst.getInt("client_id")
                    , rst.getFloat("valeur")
                    , rst.getDate("date")
                  
            );
            Credits.add(e);
        }
        return Credits;
    }   
             
         
   
@Override
     public void SupprimerCredit(Credit e) throws SQLException {
        String req = "DELETE FROM credit WHERE id =?";
        try {
            PreparedStatement ps = connexion.prepareStatement(req);
            ps.setInt(1, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
        }
    }
     @Override
      public void modifierCredit(Credit e) throws SQLException, NoSuchAlgorithmException {
        String req = "UPDATE credit SET "
                + " client_id='"+e.getClient_id()+"'"
                + ", valeur='"+e.getValeur()+"'"
                           
                + ", date='"+ (java.sql.Date) (Date) e.getDate()+"' where id  = "+e.getId()+"";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    } 
      

      
      
      
}
