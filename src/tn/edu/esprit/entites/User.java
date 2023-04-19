/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entites;

/**
 *
 * @author ghada
 */
public class User {
    private int id; 
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private int tel; 
    private String nomSup;
    private String adresseSup;
    String imagePath; 
    private String role;
    private boolean isBanned; 

    public boolean isIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public User(String email, String nom, String prenom) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
    }

    public User(int id, String nom, String prenom, String email, int tel, String password, String imagePath) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.password = password;
        this.imagePath = imagePath;
        this.isBanned = false; // Set isBanned to false by default
    }

    public User() {
    }

    public User(String email, String password, String nom, String prenom, int tel, String nomSup, String adresseSup, String imagePath) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.nomSup = nomSup;
        this.adresseSup = adresseSup;
        this.imagePath = imagePath;
    }



    public User(String email, String nom, String prenom, int tel, String nomSup, String adresseSup) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.nomSup = nomSup;
        this.adresseSup = adresseSup;
    }

    public User(int id, String email, String password, String nom, String prenom, int tel, String nomSup, String adresseSup) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.nomSup = nomSup;
        this.adresseSup = adresseSup;
    }

    public User(String email, String password, String nom, String prenom, int tel, String nomSup, String adresseSup) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.nomSup = nomSup;
        this.adresseSup = adresseSup;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public void setProfilePic(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getNomSup() {
        return nomSup;
    }

    public void setNomSup(String nomSup) {
        this.nomSup = nomSup;
    }

    public String getAdresseSup() {
        return adresseSup;
    }

    public void setAdresseSup(String adresseSup) {
        this.adresseSup = adresseSup;
    }

    @Override
    public String toString() {
        return "User{" + "email=" + email + ", password=" + password + ", nom=" + nom + ", prenom=" + prenom + ", tel=" + tel + ", nomSup=" + nomSup + ", adresseSup=" + adresseSup + '}';
    }
    
    
    
}
