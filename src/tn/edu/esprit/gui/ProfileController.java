/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Client;
import tn.edu.esprit.entities.Server;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author SOUISSI
 */
public class ProfileController implements Initializable {

    @FXML
    private TextField NomAcc;
    @FXML
    private TextField AdresseAcc;
    @FXML
    private TextField PrenomAcc;
    @FXML
    private TextField TelAcc;
    @FXML
    private TextField EmailAcc;
    @FXML
    private TextField SupAcc;
    @FXML
    private ImageView profilePic;
    @FXML
    private Button btnSupprimer;
    @FXML
    private ComboBox<String> rechercheComboBox;
    @FXML
    private Button menu_btn;
    @FXML
    private Button product;
    @FXML
    private Button user1;
    @FXML
    private Button messagerie;
    @FXML
    private Button close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
              close.setStyle("-fx-background-image: url('file:///C:/Users/azizb/Downloads/close.png');-fx-background-size: 100% 100%;");

    close.setOnAction(event -> {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
});
        
        ServiceUser serviceUser = new ServiceUser();
        rechercheComboBox.setEditable(true);
        rechercheComboBox.setButtonCell(new ListCell<>());
        rechercheComboBox.setOnKeyReleased(event -> {
            String recherche = rechercheComboBox.getEditor().getText().trim();
            List<String> suggestions = serviceUser.rechercherUtilisateurs(recherche);
            rechercheComboBox.getItems().clear();
            ObservableList<String> items = FXCollections.observableArrayList();
            items.addAll(suggestions);
            rechercheComboBox.setItems(items);

            rechercheComboBox.show();
        });

    }

    public void setTextNom(String message) {
        this.NomAcc.setText(message);
    }

    public void setTextPrenom(String message) {
        this.PrenomAcc.setText(message);
    }

    public void setTextEmail(String message) {
        this.EmailAcc.setText(message);
    }

    public void setTextTel(String message) {
        this.TelAcc.setText(message);
    }

    public void setTextSup(String message) {
        this.SupAcc.setText(message);
    }

    public void setTextAdresse(String message) {
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
            double radius = 50.0;
                Circle clip = new Circle(radius, radius, radius);
                clip.setFill(Color.WHITE);
                profilePic.setClip(clip);
        }
    }

    @FXML
    public void handleLogOutBtn(ActionEvent event) {
        try {
            UserController uc = new UserController();
            uc.logout();

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

    @FXML
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
        User updatedUser = new User(currentUser.getId(), email, currentUser.getPassword(), nom, prenom, tel, nomSup, adresseSup);

        // Update the user in the database
        su.modifier(updatedUser);

        // Show a confirmation message to the user
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ComptaMerveille :: Success Message");
        alert.setHeaderText(null);
        alert.setContentText("Utilisateur modifié");
        alert.showAndWait();
    }

    @FXML
    void supprimerCompte(ActionEvent event) {

        // Demander confirmation à l'utilisateur avant de supprimer le compte
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer votre compte ?");
        alert.setContentText("Cette action est irréversible. Veuillez confirmer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // Supprimer le compte utilisateur
            ServiceUser su = new ServiceUser();
            su.supprimer(user);

            // Afficher un message de confirmation
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
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

    @FXML
    void sendMsg(ActionEvent event) {

        // Demander confirmation à l'utilisateur avant de supprimer le compte
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notez-bien");
            alert.setHeaderText(null);
            alert.setContentText("Nous souhaitons porter à votre connaissance que vos messages ne seront présents que jusqu'à la fin de votre session. Aucun message ne sera enregistré ");
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Client.fxml"));
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

    void sendMsg2(ActionEvent event) {

        try {

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Client.fxml"));
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

 

    @FXML
    private void product(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduct.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) product.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void home(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeprod.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) menu_btn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }


    @FXML
    private void user1(ActionEvent event) {
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) user1.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void messagerie(ActionEvent event) {
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Client.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) menu_btn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

}
