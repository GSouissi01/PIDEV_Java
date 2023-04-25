/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.edu.esprit.services.ServiceProduct;

/**
 * FXML Controller class
 *
 * @author azizbramli
 */
public class PieChartController implements Initializable {

    @FXML
    private PieChart pieChart;
    @FXML
    private AnchorPane side_menu;
    @FXML
    private Label username;
    @FXML
    private Button menu_btn;
    @FXML
    private Button product;
    @FXML
    private Button promotion;
    @FXML
    private Button close;
    @FXML
    private Button calc;
    
    

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
    
    calc.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:///C:/Users/azizb/Downloads/calc.png'); -fx-background-size: 100% 100%;");

       
       
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
       
       pieChart.setPrefSize(700, 500);
       
        ServiceProduct serviceProduct = new ServiceProduct();
        ObservableList<PieChart.Data> pieChartData;
        try {
            pieChartData = serviceProduct.getPrixProduits();
            pieChart.setData(pieChartData);
            
        } catch (SQLException ex) {
            // handle exception
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
        Stage stage = (Stage) promotion.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

 @FXML
private void calculator(ActionEvent event) {
   try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("calculator.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Create a new stage for the calculator window
        Stage calculatorStage = new Stage();

        // Supprime la barre de titre de la fenêtre
        calculatorStage.initStyle(StageStyle.UNDECORATED);

        // Créer le bouton de fermeture
        Button closeButton = new Button("x");
        closeButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff;");
        closeButton.setOnAction(e -> calculatorStage.close());
        closeButton.setAlignment(Pos.TOP_RIGHT);

        // Créer un layout pour la calculatrice et le bouton de fermeture
        BorderPane layout = new BorderPane();
        layout.setCenter(root);
        layout.setTop(closeButton);
        BorderPane.setAlignment(closeButton, Pos.TOP_RIGHT);

        // Ajouter le layout à la scène
        scene.setRoot(layout);

        calculatorStage.setTitle("Calculator");
        calculatorStage.setScene(scene);

        // Show the calculator window
        calculatorStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
