/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Client;
import entity.Credit;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
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
import service.CreditService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class AjouterClientController implements Initializable {

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
        // TODO
    }    

    @FXML
    private void insert(ActionEvent event) throws IOException, SQLException {
         ClientService service = new ClientService();
        if (nom.getText().equals("") ||prenom.getText().equals("") || email.getText().equals("") ||tel.getText().equals("")   ) {
             
               
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Veuillez saisir tous les champs ");
            a.setHeaderText(null);
            a.showAndWait();
            // Vérification de l'email
            if (!email.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            a.setContentText("Veuillez saisir tous les champs ");
            a.setHeaderText(null);
            a.showAndWait();
            }
        } 
     

        else {


            
            Client r = new Client(nom.getText(),prenom.getText(),email.getText(),tel.getText());

   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmer ");
      alert.setHeaderText("Confirmer");
      alert.setContentText(" ");
      
         Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
                 service.ajouterClient(r);

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
