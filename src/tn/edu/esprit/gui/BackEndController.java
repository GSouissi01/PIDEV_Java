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
import javafx.scene.layout.RowConstraints;
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
    VBox cardContent = new VBox();
    
    Font cardTitleFont = new Font("System Bold", 18.0);
    Font cardSubtitleFont = new Font("System", 14.0);
    Font cardBodyFont = new Font("System", 12.0);

    gridPane.setHgap(20); // ajouter de l'espace horizontal entre les cartes
    gridPane.setVgap(20); // ajouter de l'espace vertical entre les cartes
    gridPane.setAlignment(Pos.CENTER);

    int row = 0;
    int col = 0;
    ServiceUser su = new ServiceUser();
    ObservableList<User> listuser = su.afficherusers();
 
for (User us : listuser) {
    
    
    StackPane card = new StackPane();
    card.setAlignment(Pos.CENTER);
    card.setPrefSize(250.0, 300.0);
    card.setStyle("-fx-background-color: #3ed3ea; -fx-background-radius: 50px;"); // Carte avec un fond noir
    card.setOnMouseEntered(event -> {
        card.setStyle("-fx-background-color: #181962;-fx-background-radius: 50px;");
    });

    // Réinitialiser le style lorsque la souris quitte la carte
    card.setOnMouseExited(event -> {
        card.setStyle("-fx-background-color: #3ed3ea;-fx-background-radius: 50px;");
    });
    
   

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

        
    Text userName = new Text("Username: " +us.getEmail());
    userName.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
    userName.setFill(javafx.scene.paint.Color.WHITE);
    userName.setFont(cardTitleFont);
    
    Text userPrenom = new Text("Pénom: " + us.getPrenom());
    userPrenom.setFill(javafx.scene.paint.Color.WHITE);
    userPrenom.setFont(cardTitleFont);

    Text userNom = new Text("Nom: " + us.getNom());
    userNom.setFill(javafx.scene.paint.Color.WHITE);
    userPrenom.setFont(cardTitleFont);
    userNom.setWrappingWidth(175.0);
           
	int idUser=su.getUserIdByEmail(us.getEmail());
        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("round-button");
deleteButton.setOnAction(event -> {
    ServiceUser serviceUser = new ServiceUser();
    serviceUser.supprimerUser(idUser);
    
    card.setStyle("-fx-background-color: transparent;");
    cardContent.getChildren().clear();
    initialize(location, resources);
});

Button banButton = new Button("Ban");
banButton.getStyleClass().add("round-button");
banButton.getStyleClass().add("blue-button");
banButton.setOnAction(event -> {
    ServiceUser serviceUser = new ServiceUser();
    serviceUser.banUser(su.getUserIdByEmail(us.getEmail()));
    sendSms("+216 97397598", "You have been banned from the system.");
    cardContent.getChildren().clear();
    initialize(location, resources);
});

Button unbanButton = new Button("Unban");
unbanButton.getStyleClass().add("round-button");
unbanButton.getStyleClass().add("green-button");
unbanButton.setOnAction(event -> {
    ServiceUser serviceUser = new ServiceUser();
    serviceUser.unbanUser(su.getUserIdByEmail(us.getEmail()));
    sendSms("+216 97397598", "Your account has been restored");
    card.setStyle("-fx-background-color: green;");
    cardContent.getChildren().clear();
    initialize(location, resources);
});


HBox buttonBox = new HBox(deleteButton, banButton, unbanButton);
buttonBox.setSpacing(10);
buttonBox.setAlignment(Pos.CENTER);

cardContent.getChildren().addAll(userImage,userName, userPrenom, userNom, buttonBox);
card.getChildren().add(cardContent);


gridPane.add(card, col, row);

        col++;
        if (col == 4) {
            col = 0;
            row++;
        }       
    }}

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
    
