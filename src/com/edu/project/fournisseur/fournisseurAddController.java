/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.fournisseur;

import static DBCnx.MyConnection.MyConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author islem
 */
public class fournisseurAddController implements Initializable {

    @FXML
    private Button btnajouter;

    fournisseurController obj = new fournisseurController();
    private TextField prixtf;
    private TextField nomtf;
    private DatePicker datetf;
    private TextField nbrplacestf;
    private TextField ratingtf;
    private TextField emailtf;
    private TextField detailstf;
    private TextField subjecttf;
    @FXML
    private TextField societetf;
    @FXML
    private TextField teltf;
    @FXML
    private TextField respotf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private boolean validateFields() {
        if (societetf.getText().isEmpty()) {
            showAlert("Error", "societe field is required.");
            return false;
        }

        if (teltf.getText().isEmpty()) {
            showAlert("Error", "Tel field is required.");
            return false;
        } else if (!teltf.getText().matches("[0-9]{8}")) {
            showAlert("Error", "Please enter a valid phone number with 8 digits.");
            return false;
        }

        if (respotf.getText().isEmpty()) {
            showAlert("Error", "respo field is required.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void OnCreate(ActionEvent event) {
        if (event.getSource() == btnajouter) {
            if (validateFields()) {
                insert();
                obj.showfournisseurs();
            }
        }

    }

    private void executeQuery(String query) {
        Connection conn = MyConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    private void insert() {
        String societe = societetf.getText();
        String numtel = teltf.getText();
        String nomrespo = respotf.getText();
        validateFields();
        String query = "INSERT INTO `fournisseur`(`societe`, `numtel`, `nomrespo`) VALUES('" + societe + "','" + numtel + "','" + nomrespo + "')";
        executeQuery(query);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("fournisseur ");
        alert.setHeaderText(null);
        alert.setContentText("added  succesfuly");
        alert.showAndWait();
    }

}
