/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.edu.esprit.entites.User;
import tn.edu.esprit.services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author SOUISSI
 */
public class ProfileController implements Initializable {

    private TextField NomAcc;
    private TextField AdresseAcc;
    private TextField PrenomAcc;
    private TextField TelAcc;
    private TextField EmailAcc;
    private TextField SupAcc;
    @FXML
    private ImageView profilePic;
    private Button btnSupprimer;
    @FXML
    private ColumnConstraints gridPane1;

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
    
    private User user;
    public void initData(User user) {
    this.user = user;

    ServiceUser su = new ServiceUser();

    // Get the user's information
    String nom = user.getNom();
    String prenom = user.getPrenom();
    String email = user.getEmail();
    if (email != null && !email.isEmpty()) {
        EmailAcc.setText(email);
    } else {
        EmailAcc.setText("No email found");
    }

    int tel = user.getTel();
    String nomSup = user.getNomSup();
    String adresseSup = user.getAdresseSup();
    String imagePath = user.getImagePath();

    // Set the values of the text fields
    NomAcc.setText(nom);
    PrenomAcc.setText(prenom);
    EmailAcc.setText(email);
    TelAcc.setText(Integer.toString(tel));
    SupAcc.setText(nomSup);
    AdresseAcc.setText(adresseSup);

        // Set the profile picture
        Image profilePicture = su.getProfilePic(imagePath);
        if (profilePicture != null) {
            this.profilePic.setImage(profilePicture);
        }
}

    @FXML
    public void handleLogOutBtn(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register1.fxml"));
            Parent root = loader.load();

            // Create a new scene and display it
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    
   void ModifierUser(ActionEvent event) {
    ServiceUser su = new ServiceUser();
        
    String email = EmailAcc.getText();
    String nom = NomAcc.getText();
    String prenom = PrenomAcc.getText();
    Integer tel = Integer.parseInt(TelAcc.getText());
    String nomSup = SupAcc.getText();
    String adresseSup = AdresseAcc.getText();

    // Get the current user from the database
    User currentUser = su.findByEmail(email);

    // Create a new user object with the updated data
    User updatedUser = new User(currentUser.getId(),email,currentUser.getPassword(),nom,prenom,tel,nomSup,adresseSup);

    // Update the user in the database
    su.modifier(updatedUser);

    // Show a confirmation message to the user
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("ComptaMerveille :: Success Message");
    alert.setHeaderText(null);
    alert.setContentText("Utilisateur modifié");
    alert.showAndWait();
}
   void supprimerCompte(ActionEvent event)
    {
        
        // Demander confirmation à l'utilisateur avant de supprimer le compte
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer votre compte ?");
        alert.setContentText("Cette action est irréversible. Veuillez confirmer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // Supprimer le compte utilisateur
            ServiceUser su = new ServiceUser();
            su.supprimer(user);

            // Afficher un message de confirmation
            Alert alert2 = new Alert(Alert. AlertType.INFORMATION);
            alert2.setTitle("Compte supprimé");
            alert2.setHeaderText(null);
            alert2.setContentText("Votre compte a été supprimé avec succès.");
            alert2.showAndWait();

            // Fermer la fenêtre actuelle
            Stage stage = (Stage) btnSupprimer.getScene().getWindow();
            stage.close();
        } else {
            // L'utilisateur a annulé l'action de suppression
            System.out.println("Suppression annulée par l'utilisateur.");
        }
    }
   


  
}
    

