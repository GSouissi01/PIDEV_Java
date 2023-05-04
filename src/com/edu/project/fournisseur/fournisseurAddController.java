/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.fournisseur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author azizbramli
 */
public class fournisseurAddController implements Initializable {

    @FXML
    private Button btnajouter;
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
        // TODO
    }    

    @FXML
    private void OnCreate(ActionEvent event) {
    }
    
}
