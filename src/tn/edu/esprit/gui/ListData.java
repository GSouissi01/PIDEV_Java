/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;


import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.entities.Promotion;
import tn.edu.esprit.services.ServiceProduct;
import tn.edu.esprit.services.ServicePromotion;
import tn.edu.esprit.utils.Database;



/**
 *
 * @author wiemhjiri
 */
public class ListData {
    
     /**
     * The data as an observable list of Persons.
     */
    
    private ObservableList<Produit> produit=FXCollections.observableArrayList();
    private ObservableList<Promotion> promotion=FXCollections.observableArrayList();

  public ListData() {
    ServiceProduct pdao=ServiceProduct.getInstance();
    ServicePromotion prdao=ServicePromotion.getInstance();
     ArrayList<Promotion> promotions = (ArrayList<Promotion>) prdao.getAll();
    ArrayList<Produit> produits = (ArrayList<Produit>) pdao.getAll(); // get the ArrayList of produits
    produit = FXCollections.observableArrayList(produits); // create an ObservableList and add all the elements of the ArrayList to it
    System.out.println(produit);

    promotion = FXCollections.observableArrayList(promotions); 
    System.out.println(promotion);
  }

    
    
    public ObservableList<Produit> getProduit(){
        return produit;
    }
     public ObservableList<Promotion> getPromotion(){
        return promotion;
    }
   
}
