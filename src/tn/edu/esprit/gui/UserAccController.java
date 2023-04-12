/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class UserAccController implements Initializable {

    @FXML
    private TextField NomAcc;
    @FXML
    private TextField PrenomAcc;
    @FXML
    private TextField EmailAcc;
    @FXML
    private TextField TelAcc;
    @FXML
    private TextField SupAcc;
    @FXML
    private TextField AdresseAcc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    public void setTextNom(String message){
        this.NomAcc.setText(message);
    }
    public void setTextPrenom(String message){
        this.PrenomAcc.setText(message);
    }
    public void setTextEmail(String message){
        this.EmailAcc.setText(message);
    }
    public void setTextTel(String message){
        this.TelAcc.setText(message);
    }
    public void setTextSup(String message){
        this.SupAcc.setText(message);
    }
    public void setTextAdresse(String message){
        this.AdresseAcc.setText(message);   
    }
    
}
