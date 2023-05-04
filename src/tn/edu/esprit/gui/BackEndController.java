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
import tn.edu.esprit.entities.User;
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
    @FXML
    private Button product;
    @FXML
    private Button promotion;
    @FXML
    private Button close;
    @FXML
    private Button reclamation;
    @FXML
    private Button back;
    @FXML
    private Button contrat;
    @FXML
    private Button typecontrat;
    @FXML
    private Button fournisseur;
    @FXML
    private Button publicite;
    @FXML
    private Button user;
    @FXML
    private Button home;
    @FXML
    private Button depense;
    @FXML
    private Button categorie;

    /**
     * Initializes the controller class.
     */
    @Override
public void initialize(URL location, ResourceBundle resources)
{
       back.setStyle("-fx-background-color: white; -fx-background-image: url('file:///C:/Users/azizb/Downloads/ret.png'); -fx-background-size: 100% 100%;");

      close.setStyle("-fx-background-image: url('file:///C:/Users/azizb/Downloads/close.png');-fx-background-size: 100% 100%;");

    close.setOnAction(event -> {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
});

    TilePane container = new TilePane();
    container.setAlignment(Pos.TOP_LEFT); 
  
container.setTileAlignment(Pos.CENTER);
container.setHgap(10);
container.setVgap(10);
container.setPadding(new Insets(20, 0, 0, -400)); // Add 20 pixels of padding to the top
    gridPane.getChildren().add(container);

    ServiceUser su = new ServiceUser();
    ObservableList<User> listuser = su.afficherusers();

    TilePane cardContainer = new TilePane();
    cardContainer.setAlignment(Pos.TOP_LEFT); 
    cardContainer.setStyle("-fx-alignment: center");
  
    
    cardContainer.setTileAlignment(Pos.CENTER);
    cardContainer.setHgap(20);
    cardContainer.setVgap(20);


    for (User us : listuser) {
        StackPane card = new StackPane();
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(250, 250);
        card.setStyle("-fx-background-color: #1a569f; -fx-background-radius: 50px; -fx-border-color: #white; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);");
        
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

    @FXML
    private void back(ActionEvent event) {
              try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dash.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
}
    
