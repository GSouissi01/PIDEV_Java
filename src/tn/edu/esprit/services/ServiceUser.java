/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.utils.Database;
/**
 *
 * @author ghada
 */
public class ServiceUser implements IUser<User>{
    Connection cnx = Database.getInstance().getCnx();
    
    public void setProfilePic(User user) {
    try {
        String email = user.getEmail();
        String imagePath = user.getImagePath();
        String req = "UPDATE user SET imagePath = ? WHERE email = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, imagePath);
        ps.setString(2, email);
        ps.executeUpdate();
        System.out.println("Profile picture updated!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    public Image getProfilePic(String imagePath) {
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(imagePath);
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return image;
    }
    @Override
    public void ajouter(User u) {
        try {
            String req = "INSERT INTO user (email, password,tel, nom,prenom, nom_sup, adresse_sup,imagePath) VALUES ('"+u.getEmail()+"', '"+u.getPassword()+"','"+u.getTel()+"','"+u.getNom()+"','"+u.getPrenom()+"','"+u.getNomSup()+"','"+u.getAdresseSup()+"','"+u.getImagePath()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("user created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void supprimerBack(User user) {
    try {
        String query = "DELETE FROM user WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setInt(1, user.getId());
        ps.executeUpdate();
        System.out.println("Utilisateur supprimé avec succès !");
    } catch (SQLException ex) {
        System.out.println("Une erreur s'est produite lors de la suppression de l'utilisateur: " + ex.getMessage());
    }
}
    public void supprimer(User u) {
    try {
        String req = "DELETE FROM user WHERE id=" + u.getId();
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("user deleted !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    
    public void supprimeruser(User us) {
        try {
            String req = "DELETE FROM user WHERE id = ?";
            PreparedStatement ste=cnx.prepareStatement(req);
            ste.setInt(1, us.getId());
            ste.executeUpdate();
            System.out.println("user supprimé !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public User login(String email, String password) {
    try {
        String req = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        if (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setTel(rs.getInt("tel"));
            u.setNomSup(rs.getString("nom_sup"));
            u.setAdresseSup(rs.getString("adresse_sup"));
            u.setImagePath(rs.getString("imagePath"));
            u.setRole(rs.getString("role")); // Ajout de la colonne "role"
            return u;
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return null;
}
    @Override
    public void modifier(User u) 
    {
        try 
        {
            String req = "UPDATE user SET email=?, password=?, tel=?, nom=?, prenom=?, nom_sup=?, adresse_sup=? WHERE email=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, u.getEmail());
            st.setString(2, u.getPassword());
            st.setInt(3, u.getTel());
            st.setString(4, u.getNom());
            st.setString(5, u.getPrenom());
            st.setString(6, u.getNomSup());
            st.setString(7, u.getAdresseSup());
            st.setString(8, u.getEmail());
            st.executeUpdate();
            System.out.println("user updated !");
        } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
  public int getUserIdByEmail(String email) {
    int userId = -1; // default value if user is not found
    try {
        String query = "SELECT id FROM user WHERE email = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            userId = rs.getInt("id");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return userId;
}  
    public void register(User u) {
        try {
            String req = "INSERT INTO `user1` (`email`, `password`,`nom`, `prenom`,`tel`, `nomSup`, `adresseSup`) VALUES (?,?)";
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
        }
    }
    
    public User findByEmail(String email) {
    try {
        String req = "SELECT * FROM user WHERE email=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String password = rs.getString("password");
            int tel = rs.getInt("tel");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            String nomSup = rs.getString("nom_sup");
            String adresseSup = rs.getString("adresse_sup");
            User u = new User(email, password, nom, prenom, tel, nomSup, adresseSup);
            return u;
        } else {
            return null;
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        return null;
    }
}
    
    public void login() {
        try{
            String req="SELECT email,password FROM user where email = ? and password = ? ";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Authorized Access !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void supprimerUser(int id) {
        try {
            String req = "DELETE FROM `user` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("user deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
public ObservableList<User> afficherusers() {
       ObservableList<User> myList= FXCollections.observableArrayList();
        
    
        try 
        {
            String sql = "SELECT email, nom, prenom, tel, nom_sup, adresse_sup, imagePath FROM user WHERE role = 'User'";
            Statement ste=cnx.createStatement();
            ResultSet rs= ste.executeQuery(sql);
            while(rs.next()){
                User u = new User();
                u.setNom(rs.getString("nom")); 
                u.setEmail(rs.getString("email"));
                u.setPrenom(rs.getString("prenom"));
                u.setTel(rs.getInt("tel"));
                u.setNomSup(rs.getString("nom_sup"));
                u.setAdresseSup(rs.getString("adresse_sup"));
                u.setImagePath(rs.getString("imagePath"));
                myList.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

}
