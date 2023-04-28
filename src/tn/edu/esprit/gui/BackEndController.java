/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.edu.esprit.entites.User;
import tn.edu.esprit.services.ServiceUser;
import static tn.edu.esprit.services.TwilioService.sendSms;

/**
 * FXML Controller class
 *
 * @author SOUISSI
 */
public class BackEndController implements Initializable {

    @FXML
    private GridPane gridPane;

    /**
     * Initializes the controller class.
     */
    @Override
public void initialize(URL location, ResourceBundle resources)
{
    
    VBox container = new VBox();
    container.setSpacing(10);
    container.setAlignment(Pos.CENTER);
    container.setPadding(new Insets(20, 0, 0, 0)); // Add 20 pixels of padding to the top
    gridPane.getChildren().add(container);

    ServiceUser su = new ServiceUser();
    ObservableList<User> listuser = su.afficherusers();

    TilePane cardContainer = new TilePane();
    cardContainer.setPrefColumns(2);
    cardContainer.setTileAlignment(Pos.CENTER);
    cardContainer.setHgap(10);
    cardContainer.setVgap(10);
    cardContainer.setPadding(new Insets(20, 0, 0, 0)); 
    cardContainer.setPrefWidth(600);

    for (User us : listuser) {
        StackPane card = new StackPane();
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(250, 250);
        card.setStyle("-fx-background-color: #1a569f; -fx-background-radius: 50px; -fx-border-color: #white; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);");
        
        ImageView userImage = new ImageView();
        userImage.setFitHeight(150.0);
        userImage.setFitWidth(150.0);
        userImage.setClip(new Circle(75, 75, 75));
        //userImage.setPreserveRatio(true);

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
        userName.setFill(javafx.scene.paint.Color.WHITE);
            Font cardSubtitleFont = null;
        userName.setFont(cardSubtitleFont);

        Text userEmail = new Text("Email: " + us.getEmail());
        userEmail.setFill(javafx.scene.paint.Color.WHITE);
        userEmail.setFont(cardSubtitleFont);

        Text userPrenom = new Text("Prénom: " + us.getPrenom());
        userPrenom.setFill(javafx.scene.paint.Color.WHITE);
        userPrenom.setWrappingWidth(175.0);
        int idUser=su.getUserIdByEmail(us.getEmail());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            ServiceUser serviceUser = new ServiceUser();
            serviceUser.supprimerUser(idUser);
            container.getChildren().clear();
            initialize(location, resources);
        });
        // Add ban button to card
        Button banButton = new Button("Ban");
        banButton.setOnAction(event -> {
            ServiceUser serviceUser = new ServiceUser();
            serviceUser.banUser(su.getUserIdByEmail(us.getEmail()));
            sendSms("+216 97397598", "You have been banned from the system.");
            // Change background color of cell to red
            card.setStyle("-fx-background-color: red;");
            
            container.getChildren().clear();
            initialize(location, resources);
        });
        VBox cardContent = new VBox();
        cardContent.setSpacing(5.0);
        cardContent.getChildren().addAll(userImage, userName, userEmail, userPrenom, deleteButton,banButton);
        card.getChildren().add(cardContent);

        cardContainer.getChildren().add(card);
    }

    container.getChildren().add(cardContainer);
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

    
}
    
