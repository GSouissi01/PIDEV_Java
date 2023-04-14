/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Categorie;
import Utils.MyDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Safe
 */
public class ServiceCategorie implements IServices<Categorie>{
      Connection cnx;
    @Override
    public void add(Categorie t) {
         try {
        String qry ="INSERT INTO `type_contrat`(`nom`) VALUES ('"+t.getName()+"')";
        cnx = MyDB.getInstance().getCnx();
      
            Statement stm =cnx.createStatement();
            
            stm.executeUpdate(qry);
            
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Categorie> afficher() {
        List<Categorie> categories = new ArrayList();
        try {
            String qry ="SELECT * FROM `type_contrat`";
            cnx = MyDB.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while(rs.next()){
                Categorie m =new Categorie();
                m.setId(rs.getInt(1));
                m.setName(rs.getString(3));
                categories.add(m);
            }
            return categories;
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;    }
     int id;
        public int getIdByNom(String nom) {
           
        try {
            String qry ="SELECT id FROM `type_contrat` WHERE nom='"+nom+"' ";
            cnx = MyDB.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while(rs.next()){
                
                id=(rs.getInt(1));
               
            }
            return id;
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;    }
    
        public List<String> getAllNames() {
        List<String> categories = new ArrayList();
        try {
            String qry ="SELECT * FROM `type_contrat`";
            cnx = MyDB.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while(rs.next()){
                categories.add(rs.getString(3));
            }
            return categories;
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;    }

    @Override
    public void modifier(Categorie t) {
     try {
       String qry = "UPDATE `type_contrat` SET `nom`='" + t.getName()+"' WHERE `id`='" + t.getId()+ "'";

      cnx = MyDB.getInstance().getCnx();
      
            Statement stm =cnx.createStatement();
            
            stm.executeUpdate(qry);
            System.out.println("Success!");
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }    }

    @Override
    public void supprimer(Categorie t) {
                  try {
            
            String qry="DELETE from `type_contrat`  WHERE `id`='" + t.getId()+ "'";
            cnx = MyDB.getInstance().getCnx();
      
            Statement stm =cnx.createStatement();
            
            stm.executeUpdate(qry);
              System.out.println("Row deleted successfully!");
        }   catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
    }
}
