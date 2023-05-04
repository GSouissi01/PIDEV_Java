/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 *
 * @author azizbramli
 */
public class Produit {
   private int id;
   private String libelle;
  private  int stock;
   private float prix;
  private  Date dateexpiration;
  private  float prixachat;
   private String imageFile;
    private Promotion promotion;
    
        public Produit(int id, String libelle, int stock, float prix, Date dateexpiration, float prixachat,int idpromo ,String imageFile) {
        this.id = id;
        this.libelle = libelle;
        this.stock = stock;
        this.prix = prix;
        this.dateexpiration = dateexpiration;
        this.prixachat = prixachat;
        this.promotion.idpromo=idpromo;
        this.imageFile = imageFile;
    }
  public Produit(String libelle, int stock, float prix, Date dateexpiration,Promotion promotion,float prixachat ,String imageFile) {
       
        this.libelle = libelle;
        this.stock = stock;
        this.prix = prix;
        this.dateexpiration = dateexpiration;
        this.prixachat = prixachat;
      this.promotion=promotion;
        this.imageFile = imageFile;
    }



    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }

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

    public Produit(int id) {
this.id=id;    }

    public void setId(int id) {
        this.id = id;
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
        return "Produit{" + "id=" + id + ", libelle=" + libelle + ", stock=" + stock + ", prix=" + prix + ", dateexpiration=" + dateexpiration + ", prixachat=" + prixachat + ", imageFile=" + imageFile + '}';
    }

  

    
    
}
