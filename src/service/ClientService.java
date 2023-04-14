/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Client;
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
public class ClientService implements IClientService{
      Connection connexion;   
  public ClientService() {
        connexion = MyDB.getInstance().getConnection();
    }
  
  @Override
  public void ajouterClient(Client e) throws SQLException {
        String req = "INSERT INTO `client` (`nom`,`prenom`,`email`,`tel`) "
                + "VALUES (?,?,?,?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setString(1, e.getNom());
        ps.setString(2, e.getPrenom());
        ps.setString(3,  e.getEmail());
        ps.setString(4, e.getTel());
  
        ps.executeUpdate();
    }
  
  @Override
     public List<Client> AfficherClient() throws SQLException {
        List<Client> Clients = new ArrayList<>();
        String req = "select * from client ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);
        while (rst.next()) {
            Client e = new Client(rst.getInt("id")
                    , rst.getString("nom")
                    , rst.getString("prenom")
                    , rst.getString("email")
                    , rst.getString("tel")
            );
            Clients.add(e);
        }
        return Clients;
    }
     
     
             
         
   
  @Override
     public void SupprimerClient(Client e) throws SQLException {
        String req = "DELETE FROM client WHERE id =?";
        try {
            PreparedStatement ps = connexion.prepareStatement(req);
            ps.setInt(1, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
        }
    }
     
       @Override
      public void modifierClient(Client e) throws SQLException, NoSuchAlgorithmException {
        String req = "UPDATE client SET "
                + " nom='"+e.getNom()+"'"
                + ", prenom='"+e.getPrenom()+"'"
                + ", email='"+e.getEmail()+"'"                  
                + ", tel='"+ e.getTel()+"' where id  = "+e.getId()+"";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    } 
      


  
}
