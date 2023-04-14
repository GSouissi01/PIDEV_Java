/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class Login1Controller implements Initializable {

    @FXML
    private BorderPane login_form;
    @FXML
    private Button tfCreateAcc1_btn;
    @FXML
    private Button tfLogin1_btn;
    @FXML
    private TextField tfEmail_Login;
    @FXML
    private PasswordField pfPassword_Login;
    @FXML
    private FXMLLoader registrationLoader;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleCreateAccountButtonAction(ActionEvent event) throws IOException {
        if (registrationLoader == null) {
            registrationLoader = new FXMLLoader(getClass().getResource("Register1.fxml"));
            Parent root = registrationLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } else {
            Parent root = registrationLoader.getRoot(); 
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
    String email = tfEmail_Login.getText();
    String password = pfPassword_Login.getText();

    ServiceUser su = new ServiceUser();
    User u = su.login(email, password);

    if (u == null) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Invalid email or password");
        alert.showAndWait();
    }  else {
        
    if(u.getRole().equals("ADMIN")) {
        Parent root = FXMLLoader.load(getClass().getResource("BackEnd.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } else if(u.getRole().equals("USER")) {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserAcc1.fxml"));
        Parent root = loader.load();
        UserAcc1Controller acc=loader.getController();
                        acc.setTextNom(u.getNom());
                        acc.setTextPrenom(u.getPrenom());
                        acc.setTextEmail(u.getEmail());
                        acc.setTextTel(""+u.getTel());
                        acc.setTextSup(u.getNomSup());
                        acc.setTextAdresse(u.getAdresseSup());
                        acc.initData(u);
                        
                        tfEmail_Login.getScene().setRoot(root);
        
            Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
}
}
