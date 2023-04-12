/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import tn.edu.esprit.entities.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import tn.edu.esprit.services.ServiceUser;
import com.sun.org.apache.xerces.internal.util.FeatureState;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.edu.esprit.utils.Database;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class RegisterController implements Initializable {

    @FXML
    private BorderPane register_form;
    @FXML
    private Button tfLogin_btn;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfAdresse;
    @FXML
    private TextField tfSup;
    @FXML
    private TextField tfTel;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfEmail;
    @FXML
    private Button tfCreateAcc_btn;

    
    private Connection connect; 
    @FXML
    private BorderPane login_form;
    @FXML
    private Button tfCreateAcc1_btn;
    @FXML
    private Button tfLogin1_btn;
    @FXML
    private TextField tfEmailLogin;
    @FXML
    private PasswordField tfPasswordLogin;
    @FXML
    private FXMLLoader loginLoader;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        if (loginLoader == null) {
            loginLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loginLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } else {
            Parent root = loginLoader.getRoot();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
    }
    
    @FXML
    private void saveUser(ActionEvent event)throws IOException {
        
        PasswordField passwordField = new PasswordField();
        String nom=tfNom.getText();
        String prenom=tfPrenom.getText();
        String email=tfEmail.getText();
        String password=tfPassword.getText();
        String telString=tfTel.getText();
        if (!telString.isEmpty()) {
            int tel = Integer.parseInt(telString);
        }
        String nomSup=tfSup.getText();
        String adresseSup=tfAdresse.getText();
        
        try{
            Alert alert; 
            if(nom.isEmpty() || prenom.isEmpty() ||email.isEmpty() || password.isEmpty() || telString.isEmpty() || nomSup.isEmpty() || adresseSup.isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
            }
            else{
                if(password.length() <8){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Password");
                    alert.showAndWait();
                }
                else{
                    int tel = Integer.parseInt(telString);
                    User u = new User(email,password,nom,prenom,tel,nomSup,adresseSup);
                    ServiceUser su=new ServiceUser();
                    su.ajouter(u);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Ajout avec succés");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UserAcc.fxml"));
                    try{
                        Parent root = loader.load();
                        UserAccController acc=loader.getController();
                        acc.setTextNom(u.getNom());
                        acc.setTextPrenom(u.getPrenom());
                        acc.setTextEmail(u.getEmail());
                        acc.setTextTel(""+u.getTel());
                        acc.setTextSup(u.getNomSup());
                        acc.setTextAdresse(u.getAdresseSup());
                        
                        tfNom.getScene().setRoot(root);
                    }
                    catch(IOException ex){
                        System.out.println("Error :" +ex.getMessage());
                    }
                    
                    /*Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();*/
                    }
                }
            }catch(Exception e){
                       e.printStackTrace();
                    }
    }
    
}
