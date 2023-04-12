/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.services.ServiceProduct;

/**
 * FXML Controller class
 *
 * @author azizbramli
 */
public class AddProductController implements Initializable {

    @FXML
    private TextField tfLibelle;
    @FXML
    private TextField tfStock;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfPrixAchat;
    @FXML
    private TextField tfImage;
    @FXML
    private Button tfAdd;
    @FXML
    private DatePicker tfDateExp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    @FXML
    private void saveUser(ActionEvent event)throws IOException {
        
        
        String libelle=tfLibelle.getText(); 
        String stockString=tfStock.getText();
        if (!stockString.isEmpty()) {
            int stock = Integer.parseInt(stockString);
        }
        String prixString=tfPrix.getText();
        if (!prixString.isEmpty()) {
            float prix = Float.parseFloat(prixString);
        }
        LocalDate selectedDate = tfDateExp.getValue();
        Date dateexpiration = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String prixAchatString=tfPrixAchat.getText();
        if (!prixAchatString.isEmpty()) {
            float prixachat = Float.parseFloat(prixAchatString);
        }
        String imageFile=tfImage.getText();
        
        try{
            Alert alert; 
            if(libelle.isEmpty() || stockString.isEmpty() ||prixString.isEmpty() || Objects.isNull(dateexpiration) || prixAchatString.isEmpty() || imageFile.isEmpty() ){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
            }
            else{
                    int stock = Integer.parseInt(stockString);
                    float prix = Float.parseFloat(prixString);
                    dateexpiration = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    float prixachat = Float.parseFloat(prixAchatString);
                    Produit p = new Produit(libelle, stock, prix, dateexpiration, prixachat, imageFile);
                    ServiceProduct sp =new ServiceProduct();
                    sp.ajouter(p);
                    
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Ajout avec succés");
                    alert.showAndWait();
                }
        }catch(Exception e){
           e.printStackTrace();
        }
    }
    
    
}
