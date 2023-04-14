/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;


import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.utils.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.edu.esprit.entities.Promotion;
import tn.edu.esprit.utils.Database;

/**
 *
 * @author azizbramli
 */
public class ServiceProduct implements IProduct<Produit>{
    Connection cnx = Database.getInstance().getCnx();
      private static ServiceProduct instance;
  
    private Statement st;
    private ResultSet rs;
    public ServiceProduct(){
     Database cs=Database.getInstance();
        try {
            st=cs.getCnx().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public static ServiceProduct getInstance(){
        if(instance==null) 
            instance=new ServiceProduct();
        return instance;
    }
    

    @Override
    public void ajouter(Produit p) {
        try {
            String req = "INSERT INTO produit (libelle, stock, prix, dateexpiration, promotion_id,prixachat ,image_file) VALUES ('"+p.getLibelle()+"', '"+p.getStock()+"','"+p.getPrix()+"','"+formatDate(p.getDateexpiration()) +"', '" + p.getPromotion().getIdpromo() + "','"+p.getPrixachat()+ "', '" +p.getImageFile()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("produit created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
  
    
   /* @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `produit` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("produit deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/
    
    
       @Override
    public Produit displayById(int id) {
        String req = "Select * from produit where id =" + id;
        
                  Produit p = new Produit();
        try {
            
             rs=st.executeQuery(req);
           // while(rs.next()){
            rs.next();
                p.setId(rs.getInt(1));
               p.setLibelle(rs.getString("libelle"));
               p.setStock(rs.getInt("stock"));
                p.setPrix(rs.getInt("prix"));
               p.setDateexpiration(rs.getDate("dateexpiration"));
             
               p.setPrixachat(rs.getInt("prixachat"));
               p.setImageFile(rs.getString("image_file"));
              
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
     return p; 
    }
    

         @Override
    public void supprimer(int id) {
        String req="delete from produit where id="+id;
        Produit p=displayById(id);
        
          if(p!=null)
              try {
           
            st.executeUpdate(req);
             
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
        }else System.out.println("n'existe pas");
    }
        
        
@Override
public void modifier(Produit p) {
    try {
        String req = "UPDATE `produit` SET `libelle` = '" + p.getLibelle() + "', `stock` = " + p.getStock() + ", `prix` = " + p.getPrix() + ", `dateexpiration` = '" + new SimpleDateFormat("yyyy-MM-dd").format(p.getDateexpiration()) + "', `prixAchat` = " + p.getPrixachat() + ", `image_file` = '" + p.getImageFile() + "' WHERE `produit`.`id` = " + p.getId();
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Produit updated !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}



   @Override
public List<Produit> getAll() {
    
    List<Produit> list = new ArrayList<>();
    try {
        String req = "SELECT * FROM produit LEFT JOIN promotion ON produit.promotion_id = promotion.idpromo";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while(rs.next()){
            Produit p = new Produit();
            Promotion promo = new Promotion();
            
            p.setId(rs.getInt("id"));
            p.setLibelle(rs.getString("libelle"));
            p.setStock(rs.getInt("stock"));
            p.setPrix(rs.getInt("prix"));
            p.setDateexpiration(rs.getDate("dateexpiration"));
            promo.setPourcentage(rs.getInt("pourcentage"));
            p.setPromotion(promo);
            p.setPrixachat(rs.getInt("prixachat"));
            p.setImageFile(rs.getString("image_file"));
          
            list.add(p);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    
    return list;
}

    

          
            
    
    
    
    
    
    
    

    private String formatDate(Date dateexpiration) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dateexpiration);
    }

}
