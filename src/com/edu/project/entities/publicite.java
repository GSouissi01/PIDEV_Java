/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.entities;

/**
 *
 * @author pc
 */
public class publicite {
    Integer id,fournisseur_id ; 
    String image,produit ; 

    public publicite() {
    }

    public publicite(Integer id, Integer idfournisseur, String image, String produit) {
        this.id = id;
        this.fournisseur_id = idfournisseur;
        this.image = image;
        this.produit = produit;
    }

    public publicite(Integer idfournisseur, String image, String produit) {
        this.fournisseur_id = idfournisseur;
        this.image = image;
        this.produit = produit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdfournisseur() {
        return fournisseur_id;
    }

    public void setIdfournisseur(Integer idfournisseur) {
        this.fournisseur_id = idfournisseur;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "publicite{" + "id=" + id + ", idfournisseur=" + fournisseur_id + ", image=" + image + ", produit=" + produit + '}';
    }
    
}
