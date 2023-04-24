/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author Mohamed Fenina
 */


import Interfaces.InterfaceDepense;
import entities.Depense;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyConnection;

public class DepenseCRUD implements InterfaceDepense{
    Statement ste;
    Connection conn = MyConnection.getInstance().getConn();

    @Override
    public void ajouterDepense(Depense d) {
        try {
            String req = "INSERT INTO `depense`(`prix`, `date`, `description`, `total_par_mois`, `etat`) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setFloat(1, d.getPrix());
            ps.setString(2, d.getDate());
            ps.setString(3, d.getdescription());
            ps.setFloat(4, d.getTotal_par_mois());
            ps.setString(5, d.getEtat());
            ps.executeUpdate();
            System.out.println("Depense ajoutée avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout de la dépense : " + ex.getMessage());
        }
    }

@Override
public boolean modifierDepense(Depense d) {
    try {
        String req = "UPDATE `depense` SET `prix`=?,`date`=?,`description`=?,`total_par_mois`=?,`etat`=? WHERE `id`=?";
        PreparedStatement ps = conn.prepareStatement(req);
        ps.setFloat(1, d.getPrix());
        ps.setString(2, d.getDate());
        ps.setString(3, d.getdescription());
        ps.setFloat(4, d.getTotal_par_mois());
        ps.setString(5, d.getEtat());
        ps.setInt(6, d.getId());
        int nbUpdatedRows = ps.executeUpdate();
        System.out.println("Dépense modifiée avec succès !");
        return nbUpdatedRows > 0;
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la modification de la dépense : " + ex.getMessage());
        return false;
    }
}


    @Override
    public void supprimerDepense(int id) {
        try {
            String req = "DELETE FROM `depense` WHERE `id`=?";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Dépense supprimée avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de la dépense : " + ex.getMessage());
        }
    }
    
      public void supprimerCategories(int depenseId) {
        String sql = "DELETE FROM categorie WHERE depense_id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, depenseId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression des catégories : " + ex.getMessage());
        }
    }

    @Override
    public List<Depense> afficherDepenses() {
        List<Depense> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `depense`";
            Statement st = conn.createStatement();
            ResultSet RS = st.executeQuery(req);
            while(RS.next()){
                System.out.println(RS);
                Depense d = new Depense();
                d.setId(RS.getInt(1));
                d.setPrix(RS.getFloat(2));
                d.setDate(RS.getString(3));
                d.setEtat(RS.getString(4));
                d.setTotal_par_mois(RS.getFloat(6));
                d.setdescription(RS.getString(5));
                list.add(d);
                
            }
            System.out.println(list);
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'affichage des dépenses : " + ex.getMessage());
        }
        return list;
    }

    @Override
    public List<Depense> afficherDepensesParMois(String mois) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float calculerTotalDepensesParEtat(String etat) {
        float total = 0;
        try {
            String req = "SELECT SUM(`prix`) FROM `depense` WHERE `etat`=?";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setString(1, etat);
            ResultSet RS = ps.executeQuery();
            while(RS.next()){
                total = RS.getFloat(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors du calcul du total des dépenses : " + ex.getMessage());
        }
        return total;
    }

  
}
