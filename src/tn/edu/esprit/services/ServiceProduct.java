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
import tn.edu.esprit.utils.Database;

/**
 *
 * @author azizbramli
 */
public class ServiceProduct implements IProduct<Produit>{
    Connection cnx = Database.getInstance().getCnx();

    @Override
    public void ajouter(Produit p) {
        try {
            String req = "INSERT INTO produit (libelle, stock, prix, dateexpiration, prixachat, image_file) VALUES ('"+p.getLibelle()+"', '"+p.getStock()+"','"+p.getPrix()+"','"+formatDate(p.getDateexpiration()) +"','"+p.getPrixachat()+"','"+p.getImageFile()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("produit created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void register(Produit u) {/*
        try {
            String req = "INSERT INTO `produit1` (`email`, `password`,`nom`, `prenom`,`tel`, `nomSup`, `adresseSup`) VALUES (?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getNom());
            ps.setString(4, u.getPrenom());
            ps.setString(5, String.valueOf(u.getTel()));
            ps.setString(6, u.getNomSup());
            ps.setString(7, u.getAdresseSup());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
    }
    
    
    
    public void ajouter2(Produit p) {
        /*try {
            String req = "INSERT INTO `personne` (`nom`, `prenom`) VALUES (?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(2, p.getPrenom());
            ps.setString(1, p.getNom());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
    }
    
    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `produit` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("produit deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Produit p) {
       /* try {
            String req = "UPDATE `produit` SET `nom` = '" + p.getNom() + "', `prenom` = '" + p.getPrenom() + "' WHERE `personne`.`id` = " + p.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
    }

    @Override
    public List<Produit> getAll() {
        
        List<Produit> list = new ArrayList<>();
        try {
            String req = "Select * from personne";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                //Produit p = new Produit(rs.getInt(1), rs.getString("nom"), rs.getString(3));
                //list.add(p);
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
