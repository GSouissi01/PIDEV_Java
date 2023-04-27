/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pc
 */
public class fournisseur {
    Integer id, numtel , rating ;   
    String societe , nomrespo ;
    
 private List<publicite> publicites = new ArrayList<>();

    public fournisseur(Integer id, Integer numtel, Integer rating, String societe, String nomrespo) {
        this.id = id;
        this.numtel = numtel;
        this.rating = rating;
        this.societe = societe;
        this.nomrespo = nomrespo;
    }

    public fournisseur(Integer id, String societe) {
        this.id = id;
        this.societe = societe;
    }

    public fournisseur(Integer numtel, Integer rating, String societe, String nomrespo) {
        this.numtel = numtel;
        this.rating = rating;
        this.societe = societe;
        this.nomrespo = nomrespo;
    }

    public fournisseur() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumtel() {
        return numtel;
    }

    public void setNumtel(Integer numtel) {
        this.numtel = numtel;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public String getNomrespo() {
        return nomrespo;
    }

    public void setNomrespo(String nomrespo) {
        this.nomrespo = nomrespo;
    }

    public List<publicite> getPublicites() {
        return publicites;
    }

    public void setPublicites(List<publicite> publicites) {
        this.publicites = publicites;
    }

    @Override
    public String toString() {
        return "fournisseur{" + "id=" + id + ", numtel=" + numtel + ", rating=" + rating + ", societe=" + societe + ", nomrespo=" + nomrespo + ", publicites=" + publicites + '}';
    }
    
    

    
    
}
