/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author azizbramli
 */
public class DashController implements Initializable {

    @FXML
    private Button contrat;
    @FXML
    private Button typecontrat;
    @FXML
    private Button promotion;
    @FXML
    private Button reclamation;
    @FXML
    private Button product;
    @FXML
    private Button fournisseur;
    @FXML
    private Button publicite;
    @FXML
    private MediaView mediaview;

    /**
     * Initializes the controller class.
     */
    
    private MediaPlayer mediaPlayer;
    @FXML
    private Button close;
    @FXML
    private Button user;
    @FXML
    private Button home;
    @FXML
    private Button depense;
    @FXML
    private Button categorie;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             close.setStyle("-fx-background-image: url('file:///C:/Users/azizb/Downloads/close.png');-fx-background-size: 100% 100%;");

    close.setOnAction(event -> {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
});

        
         mediaview.setFitWidth(550);
mediaview.setFitHeight(400);


    

    try {
        
        String videoname = "Savourez les fruits et légumes de chez nous"; // Replace with the name of your video file (without the extension)
        String path = "file:///C:/Users/azizb/Downloads/Savourez%20les%20fruits%20et%20l%C3%A9gumes%20de%20chez%20nous.mp4";

        Media media = new Media(path);
        mediaPlayer = new MediaPlayer(media);
        mediaview.setMediaPlayer(mediaPlayer);
       
         mediaPlayer.play();
      
    } catch (Exception ex) {
        ex.printStackTrace();
    }
        // TODO
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

  @FXML
    private void product(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
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
    private void promotion(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPromotion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) promotion.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void reclamation(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BackReclam.fxml"));
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
    private void contrat(ActionEvent event) {
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("contrat.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) contrat.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void typecontrat(ActionEvent event) {
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("typecontrat.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) typecontrat.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

     @FXML
    private void fournisseur(ActionEvent event) {
             try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fournisseur.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) fournisseur.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void publicite(ActionEvent event) {
             try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("publicite.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) publicite.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void user(ActionEvent event) {
              try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BackEnd.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) user.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void home(ActionEvent event) {
              try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dash.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) home.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void depense(ActionEvent event) {
              try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("depense.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) depense.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void categorie(ActionEvent event) {
              try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cat.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) categorie.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
