/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.services.ServiceProduct;

/**
 * FXML Controller class
 *
 * @author azizbramli
 */
public class UpdateProductController implements Initializable {

    @FXML
    private TextField tfLibelle;
    @FXML
    private TextField tfStock;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfPrixAchat;
    @FXML
    private Button tfAdd;
    @FXML
    private DatePicker tfDateExp;
    @FXML
    private Button btnAddImage;
    @FXML
    private ImageView Image;
    @FXML
    private TextField URLImage;
    private void clearFields() {
    tfLibelle.clear();
    tfStock.clear();
    tfPrix.clear();
    tfPrixAchat.clear();
    tfDateExp.setValue(null);
    URLImage.clear();
}

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void AddImage(ActionEvent event) throws FileNotFoundException, IOException {
    Random rand = new Random();
    int x = rand.nextInt(1000);
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Upload File Path");
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
    File file = fileChooser.showOpenDialog(null);
    String DBPath = "" + x + ".jpg";

    if (file != null) {
        FileInputStream Fsource = new FileInputStream(file.getAbsolutePath());
        FileOutputStream Fdestination = new FileOutputStream(DBPath);
        BufferedInputStream bin = new BufferedInputStream(Fsource);
        BufferedOutputStream bou = new BufferedOutputStream(Fdestination);
        System.out.println(file.getAbsoluteFile());
        String path = file.getAbsolutePath();
        String res;
        int len;
        len=path.length();

        res = path.substring(47,len);
        System.out.println(res);
        Image img = new Image(file.toURI().toString());
        Image.setImage(img);
        URLImage.setText(res);
        int b = 0;
        while (b != -1) {
            b = bin.read();
            bou.write(b);
        }
        bin.close();
        bou.close();

    } else {
        System.out.println("error");
    }
}
    

    @FXML
    private void updateUser(ActionEvent event) {
       

    String libelle = tfLibelle.getText();
    String stockString = tfStock.getText();
    int stock = 0;
    if (!stockString.isEmpty()) {
        stock = Integer.parseInt(stockString);
    }
    String prixString = tfPrix.getText();
    float prix = 0;
    if (!prixString.isEmpty()) {
        prix = Float.parseFloat(prixString);
    }

    String prixAchatString = tfPrixAchat.getText();
    float prixAchat = 0;
    if (!prixAchatString.isEmpty()) {
        prixAchat = Float.parseFloat(prixAchatString);
    }

    Alert alert;
    if (libelle.isEmpty() || stockString.isEmpty() || prixString.isEmpty() || Objects.isNull(tfDateExp.getValue()) || prixAchatString.isEmpty() ) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs");
        alert.showAndWait();
    } else {
        try {
            // Change the format of the date returned by the DatePicker to match the format expected by the database
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(java.sql.Date.valueOf(tfDateExp.getValue()));

            // Check if the user has selected a row in the table view to update
           Produit p = new Produit(libelle, stock, prix, sdf.parse(dateStr), prixAchat, URLImage.getText());
           
                // If a row is selected, update the existing Produit object with the new input values
               
                p.setLibelle(libelle);
                p.setStock(stock);
                p.setPrix(prix);
                p.setDateexpiration(sdf.parse(dateStr));
                p.setPrixachat(prixAchat);
                p.setImageFile(URLImage.getText());
            

            // Call the ServiceProduct to add or update the Produit object in the database
            ServiceProduct sp = new ServiceProduct();
          
                sp.modifier(p); // update an existing product
            

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Opération effectuée avec succès");
            alert.showAndWait();

            // Reset the input fields and the selectedProduit variable
            clearFields();
       

        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir des nombres valides");
            alert.showAndWait();
        } catch (ParseException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une date valide");
            alert.showAndWait();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);

    }
    
}
    }
}
