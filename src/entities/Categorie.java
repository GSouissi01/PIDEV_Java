/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 */
public class Categorie {
    private int id;
    private int depenseId;
    private String nom;
    private String description;
    private int code;
    private String image;

    // Constructeur par défaut
    public Categorie() {
    }

    // Constructeur avec tous les champs
    public Categorie(int id, int depenseId, String nom, String description, int code, String image) {
        this.id = id;
        this.depenseId = depenseId;
        this.nom = nom;
        this.description = description;
        this.code = code;
        this.image = image;
    }
    public Categorie( String nom, String description, int code, String image, int depenseId) {
        this.depenseId = depenseId;
        this.nom = nom;
        this.description = description;
        this.code = code;
        this.image = image;
    }


    // Getters et setters pour chaque champ
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepenseId() {
        return depenseId;
    }

    public void setDepenseId(int depenseId) {
        this.depenseId = depenseId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

