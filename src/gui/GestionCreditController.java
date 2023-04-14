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
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ClientService;
import service.CreditService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class GestionCreditController implements Initializable {
private Label label;
public static Credit connectedCredit;
public static Client connectedClient;
    @FXML
    private Button Home;
    @FXML
    private Button N_BMHome1;
    @FXML
    private TableView<Credit> tableview2;
    @FXML
    private TableColumn<?, ?> client_id;
    @FXML
    private TableColumn<?, ?> valeur;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private Button Modifier;
    @FXML
    private Button Supprimer;
    @FXML
    private Button gotocredit;
    @FXML
    private TableView<Client> tableview3;
    @FXML
    private TableColumn<?, ?> nom;
    @FXML
    private TableColumn<?, ?> prenom;
    @FXML
    private TableColumn<?, ?> email;
    @FXML
    private TableColumn<?, ?> tel;
    @FXML
    private Button Modifier1;
    @FXML
    private Button gotoclient;
    @FXML
    private Button Supprimer2;
 public ObservableList<Credit> list;
  public ObservableList<Client> list1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         CreditService pss = new CreditService();
        ArrayList<Credit> c = new ArrayList<>();
        try {
            c = (ArrayList<Credit>) pss.AfficherCredit();
        } catch (SQLException ex) {
        }
        
        ObservableList<Credit> obs2 = FXCollections.observableArrayList(c);
        tableview2.setItems(obs2);
        
        
           
        
        
 client_id .setCellValueFactory(new PropertyValueFactory<>("client_id"));
        valeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));


                   ClientService pss2 = new ClientService();
        ArrayList<Client> c2 = new ArrayList<>();
        try {
            c2 = (ArrayList<Client>) pss2.AfficherClient();
        } catch (SQLException ex) {
        }
        
        ObservableList<Client> obs22 = FXCollections.observableArrayList(c2);
        tableview3.setItems(obs22);
        
        
           
        
        
 nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));



    }    
  public void resetTableData() throws SQLDataException, SQLException {
      CreditService cs = new CreditService();
      List<Credit> listevents = new ArrayList<>();
        listevents = cs.AfficherCredit();
        ObservableList<Credit> data = FXCollections.observableArrayList(listevents);
        tableview2.setItems(data);
    }   
    public void resetTableData2() throws SQLDataException, SQLException {
      ClientService cs = new ClientService();
      List<Client> listevents = new ArrayList<>();
        listevents = cs.AfficherClient();
        ObservableList<Client> data = FXCollections.observableArrayList(listevents);
        tableview3.setItems(data);
    }   

    @FXML
    private void Home(ActionEvent event) {
    }

    @FXML
    private void Modifier(ActionEvent event) throws IOException {
          
         CreditService ps = new CreditService();
        Credit c = new Credit(tableview2.getSelectionModel().getSelectedItem().getId(),
                tableview2.getSelectionModel().getSelectedItem().getClient_id(),
                 tableview2.getSelectionModel().getSelectedItem().getValeur(),
                     tableview2.getSelectionModel().getSelectedItem().getDate()     );
          
               
        GestionCreditController.connectedCredit = c;
        
       Parent page1 = FXMLLoader.load(getClass().getResource("ModifierCredit.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Supprimer(ActionEvent event) throws SQLException {
          if (event.getSource() == Supprimer) {
              Credit rec = new Credit();

            rec.setId(tableview2.getSelectionModel().getSelectedItem().getId());
            CreditService cs = new CreditService();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Delete");
      alert.setHeaderText("Are you sure want to delete this Credit");
      alert.setContentText(" ");

      // option != null.
      Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
         
          
          cs.SupprimerCredit(rec);
           
           
         
       
      } else if (option.get() == ButtonType.CANCEL) {
      
      } else {
         this.label.setText("-");
      }
          
           
         
            
            
            resetTableData();

        }    
        
    }

    @FXML
    private void gotocredit(ActionEvent event) throws IOException {
         Parent page1 = FXMLLoader.load(getClass().getResource("AjouterCredit.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    private void gotoclient(ActionEvent event) throws IOException {
         Parent page1 = FXMLLoader.load(getClass().getResource("AjouterClient.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Supprimer2(ActionEvent event) throws SQLException {
            if (event.getSource() == Supprimer2) {
              Client rec = new Client();

            rec.setId(tableview3.getSelectionModel().getSelectedItem().getId());
            ClientService cs = new ClientService();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Delete");
      alert.setHeaderText("Are you sure want to delete this Client");
      alert.setContentText(" ");

      // option != null.
      Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
         
          
          cs.SupprimerClient(rec);
           
           
         
       
      } else if (option.get() == ButtonType.CANCEL) {
      
      } else {
         this.label.setText("-");
      }
          
           
         
            
            
            resetTableData2();

        }    
    }

    @FXML
    private void Modifier1(ActionEvent event) throws IOException {
                ClientService ps = new ClientService();
        Client c = new Client(tableview3.getSelectionModel().getSelectedItem().getId(),
                tableview3.getSelectionModel().getSelectedItem().getNom(),
                 tableview3.getSelectionModel().getSelectedItem().getPrenom(),
                     tableview3.getSelectionModel().getSelectedItem().getEmail(),
         tableview3.getSelectionModel().getSelectedItem().getTel());
          
               
        GestionCreditController.connectedClient = c;
        
        
        Parent page1 = FXMLLoader.load(getClass().getResource("ModifierClient.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    
}
