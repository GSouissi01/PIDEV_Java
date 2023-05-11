/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.tests;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.entities.Promotion;

import tn.edu.esprit.services.ServiceProduct;
import tn.edu.esprit.services.ServicePromotion;
import tn.edu.esprit.utils.Database;

/**
 *
 * @author azizbramli
 */
public class MainClass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load homeprod.fxml
  FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/tn/edu/esprit/gui/Register1.fxml"));
Parent homeRoot = homeLoader.load();
Scene homeScene = new Scene(homeRoot);

// Supprime la barre de titre
primaryStage.initStyle(StageStyle.UNDECORATED);

primaryStage.setScene(homeScene);
primaryStage.show();

        // Load video.fxml
    /*    FXMLLoader videoLoader = new FXMLLoader(getClass().getResource("../gui/video.fxml"));
        Parent videoRoot = videoLoader.load();
        Scene videoScene = new Scene(videoRoot);

        // Create a new stage for the video scene
        Stage videoStage = new Stage();
        videoStage.setTitle("PUB");
        videoStage.setScene(videoScene);

        // Set the modality to APPLICATION_MODAL to make the video scene a modal window
        videoStage.initModality(Modality.APPLICATION_MODAL);

        // Show the video scene as a modal window
        videoStage.show();

        // Create a timeline to close the video after 20 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            // Close the video stage
            videoStage.close();

            // Show the homeprod scene
            primaryStage.show();
        }));

        // Start the timeline
        timeline.play();*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
