/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Mohamed Fenina
 */
public class Depense {
     private int id;
    private float prix;
    private String date;
    private String description;
    private float total_par_mois;
    private String etat;
    public Depense() {
    }

    public Depense(int id, float prix, String date, String description, float total_par_mois, String etat) {
        this.id = id;
        this.prix = prix;
        this.date = date;
        this.description = description;
        this.total_par_mois = total_par_mois;
        this.etat = etat;
    }
    public Depense(float prix, String date, String description, float total_par_mois, String etat) {
        this.prix = prix;
        this.date = date;
        this.description = description;
        this.total_par_mois = total_par_mois;
        this.etat = etat;

    }

 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public float getTotal_par_mois() {
        return total_par_mois;
    }

    public void setTotal_par_mois(float total_par_mois) {
        this.total_par_mois = total_par_mois;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
return description;    }
}
