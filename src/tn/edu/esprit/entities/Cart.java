/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author azizbramli
 */
public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();

    public void addProduct(Produit product) {
        CartItem cartItem = findCartItem(product);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItems.add(new CartItem(product, 1));
        }
    }

    public void removeProduct(Produit product) {
        CartItem cartItem = findCartItem(product);
        if (cartItem != null) {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
            } else {
                cartItems.remove(cartItem);
            }
        }
    }

    private CartItem findCartItem(Produit product) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(product)) {
                return cartItem;
            }
        }
        return null;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrix() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}