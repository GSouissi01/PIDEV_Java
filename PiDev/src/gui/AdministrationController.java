/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class AdministrationController implements Initializable {

    private BorderPane BorderPane;
    @FXML
    private HBox root;
    @FXML
    private AnchorPane side_menu;
    @FXML
    private ImageView logo;
    @FXML
    private Label username;
    @FXML
    private AnchorPane anchoreview;
    @FXML
    private Button btnredit;
    @FXML
    private Button statistique;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        MainPage();
    }

    private void MainPage() {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("GestionCredit.fxml"));
            anchoreview.getChildren().clear();
            anchoreview.getChildren().add(view);
        } catch (IOException ex) {
             ex.printStackTrace();
        }
    }




    @FXML
    private void btncredit(ActionEvent event) {
               try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("GestionCredit.fxml"));
            anchoreview.getChildren().clear();
            anchoreview.getChildren().add(view);
        } catch (IOException ex) {
             ex.printStackTrace();
        }
    }

    @FXML
    private void btnstat(ActionEvent event) {
                try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("stat.fxml"));
            anchoreview.getChildren().clear();
            anchoreview.getChildren().add(view);
        } catch (IOException ex) {
             ex.printStackTrace();
        }
    }
}
