/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Reclamation;
import tn.edu.esprit.services.ServiceReclamation;
import tn.edu.esprit.services.TwilioService;
import static tn.edu.esprit.services.TwilioService.sendSms;
import tn.edu.esprit.utils.Database;

/**
 * FXML Controller class
 *
 * @author SOUISSI
 */
public class BackReclamController implements Initializable {
Connection cnx = Database.getInstance().getCnx();
    @FXML
    private GridPane gridPane;
    @FXML
    private Button product;
    @FXML
    private Button promotion;
    @FXML
    private Button reclamation;
    @FXML
    private Button close;
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
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
          close.setStyle("-fx-background-image: url('file:///C:/Users/azizb/Downloads/close.png');-fx-background-size: 100% 100%;");

    close.setOnAction(event -> {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
});

        
    VBox container = new VBox();
    container.setSpacing(10);
    container.setAlignment(Pos.CENTER);
    container.setPadding(new Insets(20, 0, 0, 0)); // Add 20 pixels of padding to the top
    gridPane.getChildren().add(container);

    ServiceReclamation sr = new ServiceReclamation();
    List<Reclamation> listreclam = sr.getReclamations();

    TilePane cardContainer = new TilePane();
    cardContainer.setPrefColumns(2);
    cardContainer.setTileAlignment(Pos.CENTER);
    cardContainer.setHgap(10);
    cardContainer.setVgap(10);
    cardContainer.setPadding(new Insets(20, 0, 0, 0)); 
    cardContainer.setPrefWidth(600);

    for (Reclamation recl : listreclam) {
        int idRecl=recl.getId();
        StackPane card = new StackPane();
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(250, 250);
        card.setStyle("-fx-background-color: #1a569f; -fx-background-radius: 50px; -fx-border-color: #white; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);");
      

        Text sujet = new Text("Sujet: " + recl.getSujet());
        sujet.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        sujet.setFill(javafx.scene.paint.Color.WHITE);
            Font cardSubtitleFont = null;
        sujet.setFont(cardSubtitleFont);

        Text userEmail = new Text("Email: " + recl.getUser_email());
        userEmail.setFill(javafx.scene.paint.Color.WHITE);
        userEmail.setFont(cardSubtitleFont);

        Text status = new Text("Status: " + recl.getStatus());
        status.setFill(javafx.scene.paint.Color.WHITE);
        status.setWrappingWidth(175.0);
        
        Button accepterBtn = new Button("Accepter");
        accepterBtn.setStyle("-fx-background-color: #00FF00;");
        accepterBtn.setOnAction(event -> {
            recl.setStatus("demande acceptée");
            
           try {
                sr.updateReclamation(idRecl,"Demande acceptée");
                status.setText("Status: Demande acceptée");
            } catch (SQLException ex) {
                ex.printStackTrace();
        // Handle the error in a meaningful way
            }});

        Button refuserBtn = new Button("Refuser");
        refuserBtn.setStyle("-fx-background-color: #FF0000;");
        refuserBtn.setOnAction(event -> {
            
            try {
        sr.updateReclamation(idRecl,"Demande refusée");
        status.setText("Status: Demande refusée");
         String sms="Bonjour cher client, nous vous envoyons cet sms pour vous informer que votre demande de récupération de votre compte a été refusée.";
         TwilioService.sendSms("+216 97397598", sms);
    } catch (SQLException ex) {
        ex.printStackTrace();
            status.setText("Status: " + recl.getStatus());
        
    }});

        VBox cardContent = new VBox(10, sujet, userEmail, status, accepterBtn, refuserBtn);
        cardContent.setAlignment(Pos.CENTER);

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
}
