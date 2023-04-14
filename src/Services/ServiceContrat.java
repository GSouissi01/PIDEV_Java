/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Contrat;
import Entities.Categorie;
import Utils.MyDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Safe
 */
public class ServiceContrat implements IServices<Contrat> {
      Connection cnx;
    @Override
    public void add(Contrat t) {
         try {
             LocalDateTime date = LocalDateTime.now();
        String qry ="INSERT INTO `pidev`( `datesignature`, `dateexpiration`,`montant`,`imagecontrat`) VALUES ('"+t.getDatesignature()+"','"+t.getDateexpiration()+"','"+t.getMontant()+"','"+t.getImagecontrat()+"')";

        cnx = MyDB.getInstance().getCnx();
      
            Statement stm =cnx.createStatement();
            
            stm.executeUpdate(qry);
            
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Contrat> afficher() {
        List<Contrat> Contrats = new ArrayList();
        try {
            String qry ="SELECT * FROM `contrat`";
            cnx = MyDB.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while(rs.next()){
                Contrat m =new Contrat();
                m.setId(rs.getInt(1));
                m.setDateexpiration(rs.getString(2));
                m.setDatesignature(rs.getString(3));
                m.setMontant(rs.getString(4));
                m.setImagecontrat(rs.getString("image"));
                
                Contrats.add(m);
            }
            return Contrats;
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Contrats;    }
int idc;
        public List<Contrat> getContratByCategoryName(String name) {
        List<Contrat> contrats = new ArrayList();
        
          try {
            String qry ="SELECT id FROM `type_contrat` WHERE name='"+name+"'";
            cnx = MyDB.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while(rs.next()){
               
                idc=rs.getInt(1);
                System.out.println(idc);
            }  
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            String qry ="SELECT * FROM `contrat` WHERE contratCategory_id='"+idc+"'";
            cnx = MyDB.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while(rs.next()){
                Contrat m =new Contrat();
                m.setId(rs.getInt(1));
                m.setDateexpiration(rs.getString(2));
                m.setDatesignature(rs.getString(3));
                m.setMontant(rs.getString(4));
                m.setIdCategorie(rs.getInt("contratCategory_id"));
                m.setImagecontrat(rs.getString("image"));
                contrats.add(m);
            }
            return contrats;
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return contrats;    }

    @Override
    public void modifier(Contrat t) {
try {
       String qry = "UPDATE `contrat` SET `datesignature`='" + t.getDatesignature()+ "', `dateexpiration`='" + t.getDateexpiration()+ "', `montant`='" + t.getMontant()+ "', `imagecontrat`='" + t.getImagecontrat()+"'";

      cnx = MyDB.getInstance().getCnx();
      
            Statement stm =cnx.createStatement();
            
            stm.executeUpdate(qry);
            System.out.println("Success!");
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }      }

    @Override
    public void supprimer(Contrat t) {
                  try {
            
            String qry="DELETE from `contrat`  WHERE `id`='" + t.getId()+ "'";
            cnx = MyDB.getInstance().getCnx();
      
            Statement stm =cnx.createStatement();
            
            stm.executeUpdate(qry);
              System.out.println("Row deleted successfully!");
        }   catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }    }

}
