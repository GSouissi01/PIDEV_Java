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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import static sun.rmi.transport.TransportConstants.Version;
import tn.edu.esprit.entites.PasswordHasher;
import tn.edu.esprit.entites.PasswordResetToken;
import tn.edu.esprit.entites.User;
import tn.edu.esprit.utils.Database;

/**
 *
 * @author ghada
 */
public class ServiceUser implements IUser<User> {

    Connection cnx = Database.getInstance().getCnx();

    public User findByEmail(String email) {
        User user = null;
        try {
            PreparedStatement statement = cnx.prepareStatement(
                    "SELECT * FROM user WHERE email = ?");
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setTel(rs.getInt("tel"));
                user.setNomSup(rs.getString("nom_sup"));
                user.setAdresseSup(rs.getString("adresse_sup"));
                user.setImagePath(rs.getString("imagePath"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

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

    public void ajouter(User u) {
        try {
            // Hash the password
            String hashedPassword = PasswordHasher.hashPassword(u.getPassword());
            
            String req = "INSERT INTO user (email, password, tel, nom, prenom, nom_sup, adresse_sup, imagePath) VALUES ('" + u.getEmail() + "', '" + hashedPassword + "','" + u.getTel() + "','" + u.getNom() + "','" + u.getPrenom() + "','" + u.getNomSup() + "','" + u.getAdresseSup() + "','" + u.getImagePath() + "')";
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

    public int checkexistance(String email) throws SQLException {
        Statement st = cnx.createStatement();
        String req = "select email from user where (email='" + email + "')";
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            return 1;
        }
        return 0;
    }

    public List<String> rechercherUtilisateurs(String texteRecherche) {
        List<String> suggestions = new ArrayList<>();
        try (PreparedStatement stmt = cnx.prepareStatement(
                        "SELECT nom, prenom FROM user WHERE nom LIKE ? OR prenom LIKE ?")) {
            stmt.setString(1, texteRecherche + "%");
            stmt.setString(2, texteRecherche + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                suggestions.add(nom + " " + prenom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suggestions;
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
            PreparedStatement ste = cnx.prepareStatement(req);
            ste.setInt(1, us.getId());
            ste.executeUpdate();
            System.out.println("user supprimé !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public User login(String email, String password) {
    try {
        String hashedPassword = PasswordHasher.hashPassword(password);
        String req = "SELECT * FROM user WHERE email = ? AND password = ?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setString(1, email);
        stmt.setString(2, hashedPassword);
        ResultSet rs = stmt.executeQuery();
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


    public void banUser(int userId) {
        String query = "UPDATE user SET is_banned = ? WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setBoolean(1, true);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            System.out.println("user Banned !");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void modifier(User u) {
        try {
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

    public void updateUserPassword(int userId, String newPassword) {

        PreparedStatement stmt = null;
        try {
            stmt = cnx.prepareStatement("UPDATE user SET password = ? WHERE id = ?");
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

    public User getUserByEmail(String email) {
    try {
        PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM user WHERE email = ?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("email");
            String password = rs.getString("password");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int tel = rs.getInt("tel");
            String nom_sup = rs.getString("nom_sup");
            String adresse_sup = rs.getString("adresse_sup");
            String imagePath = rs.getString("imagePath");
            return new User(id, username, password, nom, prenom, tel, nom_sup, adresse_sup, imagePath);
        } else {
            return null;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}


    public void insertPasswordResetToken(int userId, String token, long timestamp) {
        try {
            PreparedStatement stmt = cnx.prepareStatement("INSERT INTO password_reset_token(user_id, token, timestamp) VALUES (?, ?, ?)");
            stmt.setInt(1, userId);
            stmt.setString(2, token);
            stmt.setTimestamp(3, new Timestamp(timestamp));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // handle the exception as appropriate for your application
        }
    }

    public PasswordResetToken getPasswordResetToken(String token) {
        try {
            PreparedStatement statement = cnx.prepareStatement("SELECT * FROM password_reset_token WHERE token = ?");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            PasswordResetToken passwordResetToken = null;

            if (resultSet.next()) {
                passwordResetToken = new PasswordResetToken();
                passwordResetToken.setToken(resultSet.getString("token"));
                passwordResetToken.setUserId(resultSet.getInt("user_id"));
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                passwordResetToken.setTimestamp(localDateTime);

            }
            resultSet.close();
            statement.close();
            return passwordResetToken;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserById(Long id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {

            stmt = cnx.prepareStatement("SELECT * FROM user WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setNom(rs.getString("nom"));
                u.setEmail(rs.getString("email"));
                u.setPrenom(rs.getString("prenom"));
                u.setTel(rs.getInt("tel"));
                u.setNomSup(rs.getString("nom_sup"));
                u.setAdresseSup(rs.getString("adresse_sup"));
                u.setImagePath(rs.getString("imagePath"));
            }
        } catch (SQLException ex) {
            // handle exception
        } finally {
            // close resources
        }

        return user;
    }

    public void login() {
        try {
            String req = "SELECT email,password FROM user where email = ? and password = ? ";
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
        ObservableList<User> myList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT email, nom, prenom, tel, nom_sup, adresse_sup, imagePath FROM user WHERE role = 'USER'";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
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

    public void updatePassword(int userId, String newPassword) {
    try {
        // Hash the new password
        String hashedPassword = PasswordHasher.hashPassword(newPassword);
        
        String req = "UPDATE user SET password=? WHERE id=?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setString(1, hashedPassword);
        stmt.setInt(2, userId);
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    /*
    public void registerWithFacebook(String accessToken) {
        DefaultFacebookClient client = new DefaultFacebookClient(accessToken, Version.LATEST);
        User me = client.fetchObject("me", User.class, Parameter.with("fields", "email,name,picture"));

        // Create a new user object and populate it with the Facebook information
        User user = new User();
        user.setEmail(me.getEmail());
        user.setNom(me.getName());
        user.setImagePath(me.getPicture().getUrl());

        // Insert the new user into the database
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "password");
            statement = connection.prepareStatement("INSERT INTO user (email, nom, imagePath) VALUES (?, ?, ?)");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getNom());
            statement.setString(3, user.getImagePath());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
     */
}
