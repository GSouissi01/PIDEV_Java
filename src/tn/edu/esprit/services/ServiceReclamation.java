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
import java.util.ArrayList;
import java.util.List;
import tn.edu.esprit.entites.Reclamation;
import tn.edu.esprit.utils.Database;

/**
 *
 * @author SOUISSI
 */
public class ServiceReclamation {
    Connection cnx = Database.getInstance().getCnx();
        // Fonction pour ajouter une nouvelle réclamation dans la base de données
    public void ajouter(String user_email, String sujet, String description) throws SQLException {
        // Récupérer l'ID de l'utilisateur à partir de l'email
        int user_id = -1;
        String userQuery = "SELECT id FROM user WHERE email = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(userQuery)) {
            stmt.setString(1, user_email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user_id = rs.getInt("id");
            }
        }

        // Insérer la nouvelle réclamation dans la table
        String reclamationQuery = "INSERT INTO reclamation (user_email, user_id, sujet, description, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(reclamationQuery)) {
            stmt.setString(1, user_email);
            stmt.setInt(2, user_id);
            stmt.setString(3, sujet);
            stmt.setString(4, description);
            stmt.setString(5, "En attente");
            stmt.executeUpdate();
        }
    }
    
    public boolean utilisateurExiste(String email) throws SQLException 
    {
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM user WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        boolean utilisateurExiste = rs.next();
        rs.close();
        ps.close();
        return utilisateurExiste;
    }
    
    public List<Reclamation> getReclamations() {
        List<Reclamation> reclamations = new ArrayList<>();
        try {
            Statement statement = cnx.createStatement();
            String query = "SELECT id, user_email, user_id, sujet, description, status FROM reclamation";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(resultSet.getInt("id"));
                reclamation.setUser_email(resultSet.getString("user_email"));
                reclamation.setUser_id(resultSet.getInt("user_id"));
                reclamation.setSujet(resultSet.getString("sujet"));
                reclamation.setDescription(resultSet.getString("description"));
                reclamation.setStatus(resultSet.getString("status"));
                reclamations.add(reclamation);
            }
            statement.close();
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reclamations;
    }
    
   public void updateReclamation(Connection cnx,int id, String newStatus) throws SQLException {
    try {
        String sql = "UPDATE reclamation SET status = ? WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setString(1, newStatus);
        statement.setInt(2, id);
        statement.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

}


