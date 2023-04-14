/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author wissa
 */
public class Contrat {
   int id ;
    String datesignature, dateexpiration,montant ,imagecontrat;
    int idCategorie;
    public Contrat() {
    }

    public Contrat(int id, String datesignature, String dateexpiration, String montant, String imagecontrat) {
        this.id = id;
        this.datesignature = datesignature;
        this.dateexpiration = dateexpiration;
        this.montant = montant;
        this.imagecontrat = imagecontrat;
    }

    public Contrat(int id, String datesignature, String dateexpiration, String montant, String imagecontrat,int idCategorie) {
        this.id = id;
         this.datesignature = datesignature;
        this.dateexpiration = dateexpiration;
        this.montant = montant;
        this.imagecontrat = imagecontrat;
        this.idCategorie = idCategorie;
    }

  

   



    public Contrat(int id) {
        this.id = id;
    }

    public Contrat(String datesignature, String dateexpiration, String montant, String imagecontrat) {
        this.dateexpiration=dateexpiration;
        this.datesignature=datesignature;
        this.montant=montant;
        this.imagecontrat=imagecontrat;
    }
    

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatesignature() {
        return datesignature;
    }

    public void setDatesignature(String datesignature) {
        this.datesignature = datesignature;
    }

    public String getDateexpiration() {
        return dateexpiration;
    }

    public void setDateexpiration(String dateexpiration) {
        this.dateexpiration = dateexpiration;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getImagecontrat() {
        return imagecontrat;
    }

    public void setImagecontrat(String imagecontrat) {
        this.imagecontrat = imagecontrat;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", datesignature=" + datesignature + ", dateexpiration=" + dateexpiration + ", montant=" + montant + ", imagecontrat=" + imagecontrat + ", idCategorie=" + idCategorie + '}';
    }
 
}
