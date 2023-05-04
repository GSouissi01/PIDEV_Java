/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.entities.Promotion;
import tn.edu.esprit.utils.Database;

/**
 *
 * @author azizbramli
 */
public class ServicePromotion implements ipromotion<Promotion> {
    
      Connection cnx = Database.getInstance().getCnx();
      private static ServicePromotion instance;
          private Statement st;
           private ResultSet rs;

    public ServicePromotion() {
        
        
        Database cs=Database.getInstance();
        try {
            st=cs.getCnx().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
           
           

           public static ServicePromotion getInstance(){
        if(instance==null) 
            instance=new ServicePromotion();
        return instance;
    }   
    public List<Promotion> getAll() {
        
        List<Promotion> list = new ArrayList<>();
        try {
            String req = "Select * from promotion";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                  Promotion p = new Promotion();
                p.setIdpromo(rs.getInt(1));
                
                   
            p.setDescription(rs.getString("description"));
            p.setPourcentage(rs.getInt("pourcentage"));
            p.setDatedebut(rs.getDate("datedebut"));
            p.setDatefin(rs.getDate("datefin"));
            p.setStatus(rs.getString("status"));
             p.setTitre(rs.getString("titre"));
               
               list.add(p);
             
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        
        return list;
    }

    @Override
    public void insert(Promotion o) {
        try {
            String req = "INSERT INTO promotion (description, pourcentage, datedebut, datefin, status,titre) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, o.getDescription());
            ps.setInt(2, o.getPourcentage());
            ps.setDate(3, new java.sql.Date(o.getDatedebut().getTime()));
            ps.setDate(4, new java.sql.Date(o.getDatefin().getTime()));
            ps.setString(5, o.getStatus());
            ps.setString(6, o.getTitre());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM promotion WHERE idpromo=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Promotion> displayAll() {
        List<Promotion> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM promotion";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Promotion p = new Promotion();
                    p.setIdpromo(rs.getInt("idpromo"));
            p.setDescription(rs.getString("description"));
            p.setPourcentage(rs.getInt("pourcentage"));
            p.setDatedebut(rs.getDate("datedebut"));
            p.setDatefin(rs.getDate("datefin"));
            p.setStatus(rs.getString("status"));
             p.setTitre(rs.getString("titre"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }


    @Override
    public Promotion displayById(int id) {
              String req="SELECT * FROM `promotion` WHERE idpromo ="+id;
           Promotion p=new Promotion();
        try {
            rs=st.executeQuery(req);
           // while(rs.next()){
            rs.next();
                p.setIdpromo(rs.getInt(1));
           
            p.setDescription(rs.getString("description"));
            p.setPourcentage(rs.getInt("pourcentage"));
            p.setDatedebut(rs.getDate("datedebut"));
            p.setDatefin(rs.getDate("datefin"));
            p.setStatus(rs.getString("status"));
             p.setTitre(rs.getString("titre"));
            //}  
        } catch (SQLException ex) {
            Logger.getLogger(ServicePromotion.class.getName()).log(Level.SEVERE, null, ex);
        }
    return p; //To change body of generated methods, choose Tools | Templates.
    }

  @Override
public Promotion getOneByName(String nom) {
    String req = "SELECT * FROM promotion WHERE titre=?";
    Promotion p = new Promotion();
    try {
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            p.setIdpromo(rs.getInt("idpromo"));
            p.setDescription(rs.getString("description"));
            p.setPourcentage(rs.getInt("pourcentage"));
            p.setDatedebut(rs.getDate("datedebut"));
            p.setDatefin(rs.getDate("datefin"));
            p.setStatus(rs.getString("status"));
             p.setTitre(rs.getString("titre"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(ServicePromotion.class.getName()).log(Level.SEVERE, null, ex);
    }
    return p;
}

         @Override
    public void supprimer(int id) {
        String req="delete from promotion where idpromo="+id;
        Promotion p=displayById(id);
        
          if(p!=null)
              try {
           
            st.executeUpdate(req);
             
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
        }else System.out.println("n'existe pas");
    }
       
    
@Override
public void update(Promotion p) {
  try {
    String req = "UPDATE `promotion` SET " +
                 "`description` = ?, " +
                 "`pourcentage` = ?, " +
                 "`datedebut` = ?, " +
                 "`datefin` = ?, " +
                 "`status` = ?, " +
                 "`titre` = ? " +
                 "WHERE idpromo = ?";

    PreparedStatement ps = cnx.prepareStatement(req);
    ps.setString(1, p.getDescription());
    ps.setInt(2, p.getPourcentage());
    ps.setDate(3, new java.sql.Date(p.getDatedebut().getTime()));
    ps.setDate(4, new java.sql.Date(p.getDatefin().getTime()));
    ps.setString(5, p.getStatus());
    ps.setString(6, p.getTitre());
    ps.setInt(7, p.getIdpromo());

    ps.executeUpdate();
    System.out.println("Promotion updated!");
  } catch (SQLException ex) {
    System.out.println(ex.getMessage());
  }
}

}
