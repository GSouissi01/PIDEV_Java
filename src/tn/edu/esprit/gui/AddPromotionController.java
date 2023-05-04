/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.File;
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
import javafx.scene.Node;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    private Button product;
    @FXML
    private Button promotion;
    @FXML
    private TextField tftitre;
    @FXML
    private TableColumn<Promotion, String> titre;
    @FXML
    private Button close;
    @FXML
    private Button user;
    @FXML
    private Button reclamation;
    @FXML
    private Button typecontrat;
    @FXML
    private Button contrat;
    @FXML
    private Button fournisseur;
    @FXML
    private Button publicite;
    @FXML
    private Button home;
    @FXML
    private Button depense;
    @FXML
    private Button categorie;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          close.setStyle("-fx-background-image: url('file:///C:/Users/azizb/Downloads/close.png');-fx-background-size: 100% 100%;");

    close.setOnAction(event -> {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
});

        TablePromotion.setStyle("-fx-font-family: Arial; -fx-selection-bar: lightblue;");

// Charger un fichier CSS externe
TablePromotion.getStylesheets().add("file:///C:/Users/azizb/Downloads/tableview.css");
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
    titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
  
              TablePromotion.setOnMouseClicked(event -> {
    Promotion pr = TablePromotion.getSelectionModel().getSelectedItem();
    if (pr != null) {
        fillTextFields(pr);
    }
});

      Media media = new Media(new File("C:\\Users\\azizb\\Downloads\\sound.mp3").toURI().toString());

    // Ajouter un événement de souris pour jouer le son lors du survol du bouton "menu_btn"
   
    // Ajouter un événement de souris pour jouer le son lors du survol du bouton "promotion"
    promotion.setOnMouseEntered(e -> {
        // Créer un nouveau MediaPlayer pour chaque événement de souris
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    });
    
    // Ajouter un événement de souris pour jouer le son lors du survol du bouton "product"
    product.setOnMouseEntered(e -> {
        // Créer un nouveau MediaPlayer pour chaque événement de souris
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    });
    
        // TODO
    }    

@FXML
private void ajouter(ActionEvent event) {
    // Retrieve input values from the GUI
    String titre=tftitre.getText();
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
    }else if (tfdatedebut.getValue().isAfter(tfdatefin.getValue())|| tfdatedebut.getValue().isBefore(LocalDate.now()) || tfdatefin.getValue().isBefore(LocalDate.now())) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("La date de fin doit être après la date de début et être supérieure à la date actuelle");
        alert.showAndWait();
    } else if (pourcentage < 0 ) {
    alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error Message");
    alert.setHeaderText(null);
    alert.setContentText("Le pourcentage doit être positive ");
    alert.showAndWait();
} else {
        try {
            // Create a Promotion object with the input values
            Promotion p = new Promotion(description, pourcentage, java.sql.Date.valueOf(datedebut), java.sql.Date.valueOf(datefin), status,titre);
            
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
 tftitre.setText(p.getTitre());
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

    String titre=tftitre.getText();
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
    if (description.isEmpty() || stockString.isEmpty() || datedebut == null || datefin == null||titre.isEmpty()) {
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
             p.setTitre(titre);

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
    public void handleLogOutBtn(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register1.fxml"));
            Parent root = loader.load();

            // Create a new scene and display it
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void promotion(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPromotion.fxml"));
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
    private void reclamation(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BackReclam.fxml"));
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
    private void contrat(ActionEvent event) {
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("contrat.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) contrat.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void typecontrat(ActionEvent event) {
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("typecontrat.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) typecontrat.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

     @FXML
    private void fournisseur(ActionEvent event) {
             try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fournisseur.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) fournisseur.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void publicite(ActionEvent event) {
             try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("publicite.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) publicite.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void user(ActionEvent event) {
              try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BackEnd.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) user.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void home(ActionEvent event) {
              try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dash.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) home.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void depense(ActionEvent event) {
              try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("depense.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) depense.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void categorie(ActionEvent event) {
              try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cat.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) categorie.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}