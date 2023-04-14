/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author SOUISSI
 */
public class BackEndController implements Initializable {

    @FXML
    private FlowPane pane;

    /**
     * Initializes the controller class.
     */
    @Override
public void initialize(URL location, ResourceBundle resources) {
    
    Font cardTitleFont = new Font("System Bold", 18.0);
Font cardSubtitleFont = new Font("System", 14.0);
Font cardBodyFont = new Font("System", 12.0);

pane.setHgap(10);
pane.setVgap(10);
pane.setAlignment(Pos.CENTER);

int count = 0;
HBox cardContainer = new HBox();
cardContainer.setSpacing(10.0);
cardContainer.setAlignment(Pos.CENTER);
pane.getChildren().add(cardContainer);

ServiceUser su = new ServiceUser();
        ObservableList<User> listuser = FXCollections.observableArrayList();
        listuser = su.afficherusers();
        
        for (User us : listuser) {
    if (count % 2 == 0 && count > 0) {
        cardContainer = new HBox();
        cardContainer.setSpacing(10.0);
        cardContainer.setAlignment(Pos.CENTER);
        pane.getChildren().add(cardContainer);
    }
    StackPane card = new StackPane();
    card.setAlignment(Pos.CENTER);
    card.setPrefSize(250, 250);
    card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);");

    ImageView userImage = new ImageView();
    userImage.setFitHeight(150.0);
    userImage.setFitWidth(150.0);
    userImage.setClip(new Circle(75, 75, 75));
    userImage.setPreserveRatio(true);

    
    String imagePath = us.getImagePath();
    Image image = null;
    try {
        image = new Image(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }

    userImage.setImage(image);

    Text userName = new Text("Nom: " + us.getNom());
    userName.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
    userName.setFill(javafx.scene.paint.Color.BLACK);
    userName.setFont(cardSubtitleFont);

    Text userEmail = new Text("Email: " + us.getEmail());
    userEmail.setFill(javafx.scene.paint.Color.RED);
    userEmail.setFont(cardSubtitleFont);

    Text userPrenom = new Text("Prénom: " + us.getPrenom());
    userPrenom.setFill(javafx.scene.paint.Color.BLACK);
    userPrenom.setWrappingWidth(175.0);
    Button deleteButton = new Button("Delete");
    deleteButton.setOnAction(event -> {
        ServiceUser serviceUser = new ServiceUser();
        serviceUser.supprimer(us);
        pane.getChildren().clear();
        initialize(location, resources);
    });

    VBox cardContent = new VBox();
    cardContent.setSpacing(5.0);
    cardContent.getChildren().addAll(userImage, userName, userEmail, userPrenom, deleteButton);
    card.getChildren().add(cardContent);

    cardContainer.getChildren().add(card);
    count++;

}
}

    
}
    
