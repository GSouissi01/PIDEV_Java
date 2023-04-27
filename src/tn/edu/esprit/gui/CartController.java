/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.edu.esprit.entities.CartItem;

/**
 * FXML Controller class
 *
 * @author azizbramli
 */
public class CartController {
    private GridPane productGrid;

    private Label totalLabel;

    @FXML
    private Label totalPriceLabel;

    private ObservableList<CartItem> cartItems;

    private double totalPrice;
    @FXML
    private GridPane cartGridPane;
    @FXML
    private VBox cartBox;
    @FXML
    private Button clearCartButton;

    public CartController(ObservableList<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.totalPrice = calculateTotalPrice();
    }
    public CartController() {
        // Empty constructor
    }

    public void initialize() {
     
    }

    private void updateCartView() {
        productGrid.getChildren().clear();

        // Add column headers
        productGrid.add(new Label("Product"), 0, 0);
        productGrid.add(new Label("Price"), 1, 0);
        productGrid.add(new Label("Quantity"), 2, 0);
        productGrid.add(new Label(""), 3, 0);

        int row = 1;
        for (CartItem cartItem : cartItems) {
            // Add product name and price
            productGrid.add(new Label(cartItem.getProduct().getLibelle()), 0, row);
            productGrid.add(new Label(String.format("%.2f €", cartItem.getProduct().getPrix())), 1, row);

            // Add quantity and buttons
            HBox quantityBox = new HBox();
            Label quantityLabel = new Label(String.valueOf(cartItem.getQuantity()));
            Button removeButton = new Button("-");
            removeButton.setOnAction(event -> {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    quantityLabel.setText(String.valueOf(cartItem.getQuantity()));
                    updateTotalPrice();
                    updateCartView();
                } else {
                    cartItems.remove(cartItem);
                    updateTotalPrice();
                    updateCartView();
                }
            });
            Button addButton = new Button("+");
            addButton.setOnAction(event -> {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                quantityLabel.setText(String.valueOf(cartItem.getQuantity()));
                updateTotalPrice();
                updateCartView();
            });
            quantityBox.getChildren().addAll(removeButton, quantityLabel, addButton);
            productGrid.add(quantityBox, 2, row);

            // Add remove button
            Button removeItemButton = new Button("Remove from cart");
            removeItemButton.setOnAction(event -> {
                cartItems.remove(cartItem);
                updateTotalPrice();
                updateCartView();
            });
            productGrid.add(removeItemButton, 3, row);

            row++;
        }

        // Add total price labels
        totalLabel.setText("Total:");
        totalPriceLabel.setText(String.format("%.2f €", totalPrice));
    }

    private double calculateTotalPrice() {
        return cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrix() * cartItem.getQuantity())
                .sum();
    }

    private void updateTotalPrice() {
        totalPrice = calculateTotalPrice();
        totalPriceLabel.setText(String.format("%.2f €", totalPrice));
    }
}

