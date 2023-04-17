/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import tn.edu.esprit.entities.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import tn.edu.esprit.services.ServiceUser;
import com.sun.org.apache.xerces.internal.util.FeatureState;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import tn.edu.esprit.utils.Database;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class Register1Controller implements Initializable {

    @FXML
    private BorderPane register_form;
    @FXML
    private Button tfLogin_btn;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfAdresse;
    @FXML
    private TextField tfSup;
    @FXML
    private TextField tfTel;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfEmail;
    @FXML
    private Button tfCreateAcc_btn;

    
    private Connection connect; 
    private FXMLLoader loginLoader;
     
    private String imagePath;
    @FXML
    private PasswordField tfConfirmPassword;
    
    private Image profileImage;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Label URLImage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private User getUserFromForm() 
    {
        // get the user data from the form
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();
        String telString = tfTel.getText();
        int tel = 0;
        if (!telString.isEmpty()) {
            tel = Integer.parseInt(telString);
        }
        String nomSup = tfSup.getText();
        String adresseSup = tfAdresse.getText();

        // create a new user object with the form data
        User user = new User(email, password, nom, prenom, tel, nomSup, adresseSup, imagePath);

        return user;
    }
    
    @FXML
private void uploadImage(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose Image");
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
        try {
            // Copy the image to a new file with a unique name
            Random rand = new Random();
            int x = rand.nextInt(1000);
            String imageExtension = file.getName().substring(file.getName().lastIndexOf("."));
            String newImagePath = "image_" + x + imageExtension;
            Files.copy(file.toPath(), new File(newImagePath).toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Set the new image path and display the image
            imagePath = newImagePath;
            Image image = new Image(new File(newImagePath).toURI().toString());
            profileImageView.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("Error: No file chosen.");
    }
}
    
    
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        if (loginLoader == null) {
            loginLoader = new FXMLLoader(getClass().getResource("Login1.fxml"));
            Parent root = loginLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } else {
            Parent root = loginLoader.getRoot();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
    }
    
   
    @FXML
    private void saveUser(ActionEvent event) throws IOException {
    
    ServiceUser su=new ServiceUser();
    PasswordField passwordField = new PasswordField();
    String nom=tfNom.getText();
    String prenom=tfPrenom.getText();
    String email=tfEmail.getText();
    String password=tfPassword.getText();
    String telString=tfTel.getText();
    if (!telString.isEmpty()) {
        int tel = Integer.parseInt(telString);
    }
    String nomSup=tfSup.getText();
    String adresseSup=tfAdresse.getText();
    //String imagePath = null;
    
    try{
        Alert alert; 
        if(nom.isEmpty() || prenom.isEmpty() ||email.isEmpty() || password.isEmpty() || telString.isEmpty() || nomSup.isEmpty() || adresseSup.isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        }
        else{// Vérification de l'email
            if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un email valide");
                alert.showAndWait();
                return;
            }
            // Vérification du numéro de téléphone
            if (!telString.matches("\\d{8}")) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un numéro de téléphone valide (8 chiffres)");
                alert.showAndWait();
                return;
            }
            // Vérification du nom et prénom
            if (!nom.matches("[A-Z][a-z]*") || !prenom.matches("[A-Z][a-z]*")) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un nom et un prénom commençant par une majuscule");
                alert.showAndWait();
                return;
            }
            if(password.length() <8){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Password");
                alert.showAndWait();
            }
            else{
                int tel = Integer.parseInt(telString);
                
                User u = new User(email, password, nom, prenom, tel, nomSup, adresseSup, imagePath);
                su.ajouter(u);

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Ajout avec succés");
                alert.showAndWait();

                // Update UserAcc1Controller with user data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                try {
                    Parent root = loader.load();
                    ProfileController acc = loader.getController();
                    acc.setTextNom(u.getNom());
                    acc.setTextPrenom(u.getPrenom());
                    acc.setTextEmail(u.getEmail());
                    acc.setTextTel("" + u.getTel());
                    acc.setTextSup(u.getNomSup());
                    acc.setTextAdresse(u.getAdresseSup());
                    acc.initData(u);

                    tfNom.getScene().setRoot(root);
                } catch (IOException ex) {
                    System.out.println("Error :" + ex.getMessage());
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
    private void chooseImage(javafx.scene.input.MouseEvent event) {
    }

    @FXML
    private void chooseImage(ActionEvent event) {
    }
    
  }
