/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entities;

import java.util.List;

/**
 *
 * @author azizbramli
 */
public class CartItem {
    private Produit product;
    private int quantity;
 private double discountedPrice;

    public CartItem(Produit product, int quantity, double discountedPrice) {
        this.product = product;
        this.quantity = quantity;
        this.discountedPrice = discountedPrice;
    }

    public CartItem(Produit product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Produit getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

