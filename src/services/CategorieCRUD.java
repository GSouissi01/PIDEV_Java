package services;

import entities.Categorie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyConnection;

public class CategorieCRUD {

    Connection conn = MyConnection.getInstance().getConn();

    public void ajouterCategorie(Categorie c) {
        try {
           

            String req = "INSERT INTO `categorie`(`depense_id`, `nom`, `description`, `code`, `image`) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, c.getDepenseId());
            ps.setString(2, c.getNom());
            ps.setString(3, c.getDescription());
            ps.setInt(4, c.getCode());
            ps.setString(5, c.getImage());
            ps.executeUpdate();
            System.out.println("Catégorie ajoutée avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout de la catégorie : " + ex.getMessage());
        }
    }

    public boolean modifierCategorie(Categorie c) {
        try {
            String req = "UPDATE `categorie` SET `nom`=?,`description`=?,`code`=?,`image`=? WHERE `id`=?";
            PreparedStatement ps = conn.prepareStatement(req);
          
            ps.setString(1, c.getNom());
            ps.setString(2, c.getDescription());
            ps.setInt(3, c.getCode());
            ps.setString(4, c.getImage());
            ps.setInt(5, c.getId());
            int nbUpdatedRows = ps.executeUpdate();
            System.out.println("Catégorie modifiée avec succès !");
            return nbUpdatedRows > 0;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification de la catégorie : " + ex.getMessage());
            return false;
        }
    }

    public void supprimerCategorie(int id) {
        try {
            String req = "DELETE FROM `categorie` WHERE `id`=?";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Catégorie supprimée avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de la catégorie : " + ex.getMessage());
        }
    }

    public List<Categorie> afficherCategories() {
        List<Categorie> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `categorie`";
            Statement st = conn.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Categorie c = new Categorie();
                c.setId(RS.getInt("id"));
                c.setNom(RS.getString("nom"));
                c.setDescription(RS.getString("description"));
                c.setCode(RS.getInt("code"));
                c.setImage(RS.getString("image"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'affichage des catégories : " + ex.getMessage());
        }
        return list;
    }
}
