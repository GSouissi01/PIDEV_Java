/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entites;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.utils.Database;

/**
 *
 * @author SOUISSI
 */
public class PasswordResetToken {

    private static final int EXPIRATION_TIME_IN_MINUTES = 60 * 24; // 24 hours

    private Long id;

    private String token;

    private User user;

    private LocalDateTime expiryDate;

    // Constructors, getters, and setters omitted for brevity

   
    
    public PasswordResetToken(User user, String token) {
        this.user = user;
        this.token = token;
        Date expiryDate = calculateExpiryDate(EXPIRATION_TIME_IN_MINUTES);
        LocalDateTime localDateTime = expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.expiryDate = localDateTime;
    }

    public PasswordResetToken() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }

    // Other utility methods omitted for brevity

public void setId(long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getUserId() {
        return user.getId();
    }
public String getUserId(String resetToken) {
        Connection cnx = Database.getInstance().getCnx();

    PreparedStatement stmt = null;
    ResultSet rs = null;
    String userId = null;
    try {
        
        stmt = cnx.prepareStatement("SELECT user_id FROM password_reset_tokens WHERE token = ?");
        stmt.setString(1, resetToken);
        rs = stmt.executeQuery();
        if (rs.next()) {
            userId = rs.getString("user_id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
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
    return userId;
}
}