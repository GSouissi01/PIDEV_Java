/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class LoginController implements Initializable {

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
            registrationLoader = new FXMLLoader(getClass().getResource("Register.fxml"));
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
    } else {
        // Navigate to the main page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserAcc.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
}
