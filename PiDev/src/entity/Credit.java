/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Administrateur
 */
public class Credit {
    private int id; 
    
        private int client_id ;
    
    private float valeur ;
     public Date date;

    public Credit() {
    }

    public Credit(int id, int client_id, float valeur, Date date) {
        this.id = id;
        this.client_id = client_id;
        this.valeur = valeur;
        this.date = date;
    }

    public Credit(int client_id, float valeur, Date date) {
        this.client_id = client_id;
        this.valeur = valeur;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public float getValeur() {
        return valeur;
    }

    public void setValeur(float valeur) {
        this.valeur = valeur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Credit{" + "id=" + id + ", client_id=" + client_id + ", valeur=" + valeur + ", date=" + date + '}';
    }
     
    
    
    
}
