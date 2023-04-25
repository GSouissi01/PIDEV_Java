/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author azizbramli
 */
public class VideoController implements Initializable {
    
  
    private Media media;
    public static String videoName;
    @FXML
    private MediaView mediaview;
    private Slider volume;

    /**
     * Initializes the controller class.
     */
private MediaPlayer mediaPlayer;

@Override
public void initialize(URL url, ResourceBundle rb) {
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
}

    private void play(ActionEvent event) {
    mediaPlayer.play();
}

    private void pause(ActionEvent event) {
    mediaPlayer.pause();
}

    private void stop(ActionEvent event) {
    mediaPlayer.seek(mediaPlayer.getStartTime());
    mediaPlayer.stop();
}
}
