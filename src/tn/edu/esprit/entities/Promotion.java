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
public class Promotion {
    int idpromo;
    String description;
    int pourcentage;
    Date datedebut;
    Date datefin;
    String status;

    public Promotion() {
    }

    public Promotion(String description, int pourcentage, Date datedebut, Date datefin, String status) {
        this.description = description;
        this.pourcentage = pourcentage;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.status = status;
    }

    public Promotion(int idpromo, String description, int pourcentage, Date datedebut, Date datefin, String status) {
        this.idpromo = idpromo;
        this.description = description;
        this.pourcentage = pourcentage;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.status = status;
    }

    public int getIdpromo() {
        return idpromo;
    }

    public String getDescription() {
        return description;
    }

    public int getPourcentage() {
        return pourcentage;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public String getStatus() {
        return status;
    }

    public void setIdpromo(int idpromo) {
        this.idpromo = idpromo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPourcentage(int pourcentage) {
        this.pourcentage = pourcentage;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return description ;
    }

  


    
    
}
