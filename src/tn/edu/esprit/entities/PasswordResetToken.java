/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entities;

import java.time.LocalDateTime;

/**
 *
 * @author SOUISSI
 */
public class PasswordResetToken {
    private int id;
    private int userId;
    private String token;
    private LocalDateTime timestamp;

    public PasswordResetToken(int id, int userId, String token, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.timestamp = timestamp;
    }

    public PasswordResetToken() {//To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}