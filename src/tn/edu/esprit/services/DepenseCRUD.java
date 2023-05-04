/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  tn.edu.esprit.services;

/**
 *
 * @author Mohamed Fenina
 */




import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.edu.esprit.entities.Depense;
import tn.edu.esprit.utils.MyConnection;


public class DepenseCRUD {
    Statement ste;
    Connection conn = MyConnection.getInstance().getCnx();

 



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

 
    public List<Depense> afficherDepensesParMois(String mois) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
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
