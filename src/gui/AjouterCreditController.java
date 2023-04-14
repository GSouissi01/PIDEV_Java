/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Credit;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.CreditService;
import tools.MyDB;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class AjouterCreditController implements Initializable {
     Connection connexion;  
  public AjouterCreditController() {
        connexion = MyDB.getInstance().getConnection();
    } 
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<Integer> inputCLIENT;
    @FXML
    private TextField inputvaleur;
    @FXML
    private Button ajouter;
    @FXML
    private Hyperlink prec;
private Label label;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          try {
            String req = "select * from client";
            Statement stm = connexion.createStatement();
            ResultSet rst = stm.executeQuery(req);
            
            while (rst.next()) {
                
                Integer xx = rst.getInt("id");
                inputCLIENT.getItems().add(xx);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }    

    @FXML
    private void insert(ActionEvent event) throws SQLException, IOException {
           CreditService service = new CreditService();
        if (inputvaleur.getText().equals("")   ) {
             
               
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Veuillez saisir tous les champs ");
            a.setHeaderText(null);
            a.showAndWait();
        } 
     

        else {

     java.util.Date date2 = java.util.Date.from(this.date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date2.getTime());      
            
            Credit r = new Credit(inputCLIENT.getValue(),Float.parseFloat(inputvaleur.getText()),sqlDate);

   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmer ");
      alert.setHeaderText("Confirmer");
      alert.setContentText(" ");
      
         Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
                 service.ajouterCredit(r);

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
