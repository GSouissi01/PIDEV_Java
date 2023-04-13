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
    Integer id, numtel ;   
    String societe , nomrespo ;
    
 private List<publicite> publicites = new ArrayList<>();
    public fournisseur() {
    }

    public fournisseur(Integer id, String societe) {
        this.id = id;
        this.societe = societe;
    }

    public fournisseur(Integer id, Integer numtel, String societe, String nomrespo) {
        this.id = id;
        this.numtel = numtel;
        this.societe = societe;
        this.nomrespo = nomrespo;
    }

    public fournisseur(Integer numtel, String societe, String nomrespo) {
        this.numtel = numtel;
        this.societe = societe;
        this.nomrespo = nomrespo;
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

    @Override
    public String toString() {
        return "fournisseur{" + "id=" + id + ", numtel=" + numtel + ", societe=" + societe + ", nomrespo=" + nomrespo + '}';
    }
    

    
    
}
