/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entities;

/**
 *
 * @author SOUISSI
 */
public class Reclamation {
    private int id;
    private String user_email;
    private int user_id;
    private String sujet;
    private String description;
    private String status;

    public Reclamation(int id, String user_email, int user_id, String sujet, String description, String status) {
        this.id = id;
        this.user_email = user_email;
        this.user_id = user_id;
        this.sujet = sujet;
        this.description = description;
        this.status = status;
    }

    public Reclamation() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

