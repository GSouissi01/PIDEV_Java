/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 * FXML Controller class
 *
 * @author azizbramli
 */
public class homeprodController implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private AnchorPane side_menu;
    @FXML
    private Button menu_btn;
    @FXML
    private Label username;
    @FXML
    private Button product;
    @FXML
    private Button promotion;
    
   private final Image[] backgroundImages = new Image[]{
        new Image(new File("C:\\Users\\azizb\\Downloads\\background.jpg").toURI().toString()),
       new Image(new File("C:\\Users\\azizb\\Downloads\\aziz.png").toURI().toString()),
              new Image(new File("C:\\Users\\azizb\\Downloads\\s.png").toURI().toString()),

    new Image(new File("C:\\Users\\azizb\\Downloads\\f.png").toURI().toString()),
   new Image(new File("C:\\Users\\azizb\\Downloads\\Fi.png").toURI().toString()),
  new Image(new File("C:\\Users\\azizb\\Downloads\\promo.png").toURI().toString())
};

private int currentImageIndex = 0;
    @FXML
    private ImageView background;
    @FXML
    private Button close;

private void animateBackground() {
    javafx.util.Duration duration = new javafx.util.Duration(2000);
    Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
        currentImageIndex = (currentImageIndex + 1) % backgroundImages.length;
        background.setImage(backgroundImages[currentImageIndex]);
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
}




  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
      close.setStyle("-fx-background-image: url('file:///C:/Users/azizb/Downloads/close.png');-fx-background-size: 100% 100%;");

    close.setOnAction(event -> {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
});

        
    animateBackground();
        // Charger le fichier audio dans un Media
        Media media = new Media(new File("C:\\Users\\azizb\\Downloads\\sound.mp3").toURI().toString());

        // Ajouter un événement de souris pour jouer le son lors du survol du bouton "menu_btn"
        menu_btn.setOnMouseEntered(e -> {
            // Créer un nouveau MediaPlayer pour chaque événement de souris
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        });
        
        // Ajouter un événement de souris pour jouer le son lors du survol du bouton "promotion"
        promotion.setOnMouseEntered(e -> {
            // Créer un nouveau MediaPlayer pour chaque événement de souris
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        });
        
        // Ajouter un événement de souris pour jouer le son lors du survol du bouton "product"
        product.setOnMouseEntered(e -> {
            // Créer un nouveau MediaPlayer pour chaque événement de souris
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        });
        
     
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
    private void promotion(ActionEvent event) {
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPromotion.fxml"));
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
