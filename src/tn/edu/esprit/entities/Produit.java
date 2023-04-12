/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entities;

import java.util.Date;

/**
 *
 * @author azizbramli
 */
public class Produit {
    int id;
    String libelle;
    int stock;
    float prix;
    Date dateexpiration;
    float prixachat;
    String imageFile;

    public Produit(int id, String libelle, int stock, float prix, Date dateexpiration, float prixachat, String imageFile) {
        this.id = id;
        this.libelle = libelle;
        this.stock = stock;
        this.prix = prix;
        this.dateexpiration = dateexpiration;
        this.prixachat = prixachat;
        this.imageFile = imageFile;
    }

    public Produit(String libelle, int stock, float prix, Date dateexpiration, float prixachat, String imageFile) {
        this.libelle = libelle;
        this.stock = stock;
        this.prix = prix;
        this.dateexpiration = dateexpiration;
        this.prixachat = prixachat;
        this.imageFile = imageFile;
    }

    public Produit() {
     
    }

    public int getId() {
        return id;
    }
    
    public String getLibelle() {
        return libelle;
    }

    public int getStock() {
        return stock;
    }

    public float getPrix() {
        return prix;
    }

    public Date getDateexpiration() {
        return dateexpiration;
    }

    public float getPrixachat() {
        return prixachat;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setDateexpiration(Date dateexpiration) {
        this.dateexpiration = dateexpiration;
    }

    public void setPrixachat(float prixachat) {
        this.prixachat = prixachat;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String toString() {
        return "Produit{" + "libelle=" + libelle + ", stock=" + stock + ", prix=" + prix + ", dateexpiration=" + dateexpiration + ", prixachat=" + prixachat + ", imageFile=" + imageFile + '}';
    }
    
    
    
    
    
}
