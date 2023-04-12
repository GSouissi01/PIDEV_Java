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
import tn.edu.esprit.entities.User;
import tn.edu.esprit.utils.Database;
/**
 *
 * @author ghada
 */
public class ServiceUser implements IUser<User>{
    Connection cnx = Database.getInstance().getCnx();

    @Override
    public void ajouter(User u) {
        try {
            String req = "INSERT INTO user (email, password,tel, nom,prenom, nom_sup, adresse_sup) VALUES ('"+u.getEmail()+"', '"+u.getPassword()+"','"+u.getTel()+"','"+u.getNom()+"','"+u.getPrenom()+"','"+u.getNomSup()+"','"+u.getAdresseSup()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("user created !");
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
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setTel(rs.getInt("tel"));
            u.setNomSup(rs.getString("nom_sup"));
            u.setAdresseSup(rs.getString("adresse_sup"));
            return u;
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return null;
}
     public void modifierUtilisateur(User user) {
        try {
            String requete2="update user set email=?,nom=?,prenom=?,tel=?,nom_sup=?, adresse_sup=? where id=?";
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setInt(1, user.getCIN());
            pst.setString(2, user.getUserName());
            pst.setInt(3, user.getNumero());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getAdresse());
            pst.setInt(6, user.getId());
            pst.executeUpdate();
           
            System.out.println("Utlisateur est modifié");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
    }}
    
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
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `user` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("user deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(User p) {
       /* try {
            String req = "UPDATE `user` SET `nom` = '" + p.getNom() + "', `prenom` = '" + p.getPrenom() + "' WHERE `personne`.`id` = " + p.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
    }

    @Override
    public List<User> getAll() {
        
        List<User> list = new ArrayList<>();
        try {
            String req = "Select * from personne";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                //User p = new User(rs.getInt(1), rs.getString("nom"), rs.getString(3));
                //list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        
        return list;
    }

}
