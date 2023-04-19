/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import tn.edu.esprit.utils.Database;
/**
 *
 * @author SOUISSI
 */
public class UserService {
    Connection cnx = Database.getInstance().getCnx();
 private String secretKey = "your_secret_key_here"; // change this to a secure key

public String generateToken(String email) {
    return Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS512, secretKey).compact();
}

public Claims decodeToken(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
}

public boolean authenticate(String email, String password) {
    try {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String hashedPassword = rs.getString("password");
            return BCrypt.checkpw(password, hashedPassword);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public User createUser(String email, String password, String nom, String prenom, String tel, String nom_sup, String adresse, String imagePath) {
    try {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (email, password, nom, prenom, tel, nom_sup, adresse, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, email);
        stmt.setString(2, hashedPassword);
        stmt.setString(3, nom);
        stmt.setString(4, prenom);
        stmt.setString(5, tel);
        stmt.setString(6, nom_sup);
        stmt.setString(7, adresse);
        stmt.setString(8, imagePath);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            return new User(id, email, nom, prenom, tel, nom_sup, adresse, imagePath);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}   
}
