/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.entities.Promotion;
import tn.edu.esprit.services.ServicePromotion;

/**
 * FXML Controller class
 *
 * @author azizbramli
 */
public class AddPromotionController implements Initializable {

    @FXML
    private TextField tfdescription;
    @FXML
    private TextField tfpourcentage;
    @FXML
    private ComboBox<String> tfstatus;
    @FXML
    private DatePicker tfdatedebut;
    @FXML
    private DatePicker tfdatefin;
    
private DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    @FXML
    private TableView<Promotion> TablePromotion;
    @FXML
    private TableColumn<Promotion, String> description;
    @FXML
    private TableColumn<Promotion, Integer> pourcentage;
    @FXML
    private TableColumn<?, ?> datedebut;
    @FXML
    private TableColumn<?, ?> datefin;
    @FXML
    private TableColumn<Promotion, String> status;
    
    private ListData listdata = new ListData();
    @FXML
    private AnchorPane side_menu;
    @FXML
    private Label username;
    @FXML
    private Button menu_btn;
    @FXML
    private Button product;
    @FXML
    private Button promotion;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> options = FXCollections.observableArrayList(
    "active",
    "cancelled",
    "expired"
);
tfstatus.setItems(options);
        
        TablePromotion.setItems(listdata.getPromotion());
      description.setCellValueFactory(new PropertyValueFactory<>("description"));
    pourcentage.setCellValueFactory(new PropertyValueFactory<>("pourcentage"));
    datedebut.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
    datefin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
    status.setCellValueFactory(new PropertyValueFactory<>("status"));
  
              TablePromotion.setOnMouseClicked(event -> {
    Promotion pr = TablePromotion.getSelectionModel().getSelectedItem();
    if (pr != null) {
        fillTextFields(pr);
    }
});

   
    
        // TODO
    }    

@FXML
private void ajouter(ActionEvent event) {
    // Retrieve input values from the GUI
    String description = tfdescription.getText();
    String pourcentageString = tfpourcentage.getText();
    int pourcentage = 0;
    if (!pourcentageString.isEmpty()) {
        pourcentage = Integer.parseInt(pourcentageString);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String datedebut = sdf.format(java.sql.Date.valueOf(tfdatedebut.getValue()));
    String datefin = sdf.format(java.sql.Date.valueOf(tfdatefin.getValue()));
    
  String status = tfstatus.getSelectionModel().getSelectedItem();



    Alert alert;
    if (description.isEmpty() || pourcentageString.isEmpty() || datedebut == null || datefin == null || status == null) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs");
        alert.showAndWait();
    } else {
        try {
            // Create a Promotion object with the input values
            Promotion p = new Promotion(description, pourcentage, java.sql.Date.valueOf(datedebut), java.sql.Date.valueOf(datefin), status);
            
            // Call the ServicePromotion to add the Promotion object to the database
            ServicePromotion promotiondao = new ServicePromotion();
            promotiondao.insert(p);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Ajout avec succés");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un pourcentage valide");
            alert.showAndWait();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'ajout de la promotion");
            alert.showAndWait();
        }
    }
}







// Dans votre méthode fillTextFields(), vous pouvez sélectionner la valeur de l'état de la promotion dans le combobox
private void fillTextFields(Promotion p) {
    String datedebuts = ""+p.getDatedebut();
    LocalDate datedebut = LocalDate.parse(datedebuts);
    
     String datefins = ""+p.getDatefin();
    LocalDate datefin = LocalDate.parse(datefins);

    tfdatedebut.setValue(datedebut);
     tfdatefin.setValue(datefin);
    tfdescription.setText(p.getDescription());
    tfpourcentage.setText(Integer.toString(p.getPourcentage()));
  tfstatus.getSelectionModel().select(p.getStatus());
 
}


@FXML
private void modifier(ActionEvent event) {
    Promotion p = TablePromotion.getSelectionModel().getSelectedItem();
    if (p == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une promotion à modifier");
        alert.showAndWait();
        return;
    }

    String description = tfdescription.getText();
    String stockString = tfpourcentage.getText();
    int pourcentage = 0;
    if (!stockString.isEmpty()) {
        pourcentage = Integer.parseInt(stockString);
    }
    LocalDate datedebut = tfdatedebut.getValue();
    LocalDate datefin = tfdatefin.getValue();
    String status = tfstatus.getSelectionModel().getSelectedItem();

    Alert alert;
    if (description.isEmpty() || stockString.isEmpty() || datedebut == null || datefin == null) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs");
        alert.showAndWait();
    } else {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(java.sql.Date.valueOf(datedebut));
            String dateStrf = sdf.format(java.sql.Date.valueOf(datefin));

            p.setDescription(description);
            p.setPourcentage(pourcentage);
            p.setDatedebut(sdf.parse(dateStr));
            p.setDatefin(sdf.parse(dateStrf));
            p.setStatus(status);

            ServicePromotion sp = new ServicePromotion();
            sp.update(p);

            TablePromotion.refresh();

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Opération effectuée avec succès");
            alert.showAndWait();

            tfdescription.setText("");
            tfpourcentage.setText("");
            tfdatedebut.setValue(null);
            tfdatefin.setValue(null);
            tfstatus.getSelectionModel().clearSelection();
            TablePromotion.getSelectionModel().clearSelection();
        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir des nombres valides");
            alert.showAndWait();
        } catch (ParseException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une date valide");
            alert.showAndWait();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite");
            alert.showAndWait();
        }
    }
}

     public void supprimer() {
        ServicePromotion p = new ServicePromotion();
        p.supprimer(TablePromotion.getSelectionModel().getSelectedItem().getIdpromo());
        System.out.println(TablePromotion.getSelectionModel().getSelectedItem().getIdpromo());
        Promotion pr=new Promotion();
        pr=TablePromotion.getSelectionModel().getSelectedItem();
   
        
    }
  
    @FXML
    private void delete(ActionEvent event) {
         supprimer();
        TablePromotion.getItems().removeAll(TablePromotion.getSelectionModel().getSelectedItem());
      
        TablePromotion.refresh();
        
    }

  @FXML
    private void product(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) product.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void home(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeprod.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) menu_btn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void promotion(ActionEvent event) {
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPromotion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) menu_btn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}