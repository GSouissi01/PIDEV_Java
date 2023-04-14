/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Client;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ClientService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class ModifierClientController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private Button ajouter;
    @FXML
    private Hyperlink prec;
    @FXML
    private Label labelid;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField tel;
private Label label;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelid.setText(Integer.toString(GestionCreditController.connectedClient.getId()));
        nom.setText(GestionCreditController.connectedClient.getNom());

        prenom.setText(GestionCreditController.connectedClient.getPrenom());
        tel.setText(GestionCreditController.connectedClient.getTel());
        email.setText(GestionCreditController.connectedClient.getEmail());

     

     
    }    

    @FXML
    private void insert(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException {
          ClientService service = new ClientService();
        if (nom.getText().equals("") ||prenom.getText().equals("") || email.getText().equals("") ||tel.getText().equals("")   ) {
             
               
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Veuillez saisir tous les champs ");
            a.setHeaderText(null);
            a.showAndWait();
        } 
     

        else {


            
            Client r = new Client(Integer.parseInt(labelid.getText()),nom.getText(),prenom.getText(),email.getText(),tel.getText());

   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmer ");
      alert.setHeaderText("Confirmer");
      alert.setContentText(" ");
      
         Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
                 service.modifierClient(r);

    Parent page1 = FXMLLoader.load(getClass().getResource("GestionCredit.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
          
      
      } else if (option.get() == ButtonType.CANCEL) {
      
      } else {
         this.label.setText("-");
      }
      

          

        }; 
        
    }

    @FXML
    private void prec(ActionEvent event) throws IOException {
         Parent page1 = FXMLLoader.load(getClass().getResource("GestionCredit.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
}
