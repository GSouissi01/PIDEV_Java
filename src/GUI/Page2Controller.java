/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Depense;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.DepenseCRUD;

/**
 * FXML Controller class
 *
 * @author Khalil.Jammezi
 */
public class Page2Controller implements Initializable {

  @FXML
    private TableView<Depense> tableDepenses;

    @FXML
    private TableColumn<Depense, String> colLibelle;

    @FXML
    private TableColumn<Depense, Double> colMontant;

    @FXML
    private TableColumn<Depense, String> colDate;

    @FXML
    private TableColumn<Depense, Boolean> colPaye;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnSupprimer;

    @FXML
    private Button btnModifier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        DepenseCRUD crud = new DepenseCRUD();

        // configure table columns
        colLibelle.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPaye.setCellValueFactory(new PropertyValueFactory<>("etat"));

        // load data into table
   
List<Depense> depensesList = crud.afficherDepenses();

ObservableList<Depense> depensesObservableList = FXCollections.observableList(depensesList);

        tableDepenses.setItems(depensesObservableList);
        
    }    

   @FXML
void ajouterDepense(ActionEvent event) throws IOException {
    // load AjouterDepense.fxml
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterDepense.fxml"));
    Parent root = loader.load();
    // create new window
    Stage stage = new Stage();
    stage.setTitle("Ajouter une dépense");
    stage.setScene(new Scene(root));
    // display window
    stage.showAndWait();
    // update table view with new data
    DepenseCRUD crud = new DepenseCRUD();
   ObservableList<Depense> depenses = FXCollections.observableArrayList(crud.afficherDepenses());

    tableDepenses.setItems(depenses);
    
}
@FXML
void supprimerDepense(ActionEvent event) {
    // get the selected depense(s)
    ObservableList<Depense> selectedDepenses = tableDepenses.getSelectionModel().getSelectedItems();

    // check if a depense is selected
    if (selectedDepenses.isEmpty()) {
        // show an error message if no depense is selected
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une dépense à supprimer");
        alert.showAndWait();
        return;
    }

    // ask for confirmation before deleting
    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    confirmation.setTitle("Confirmation");
    confirmation.setHeaderText(null);
    confirmation.setContentText("Êtes-vous sûr(e) de vouloir supprimer cette (ces) dépense(s) ?");
    Optional<ButtonType> result = confirmation.showAndWait();

    if (result.get() == ButtonType.OK) {
        // delete the categorie records related to the selected depense(s)
        DepenseCRUD crud = new DepenseCRUD();
        for (Depense depense : selectedDepenses) {
            crud.supprimerCategories(depense.getId());
        }

        // delete the selected depense(s) from the database
        for (Depense depense : selectedDepenses) {
            crud.supprimerDepense(depense.getId());
        }

        // remove the selected depense(s) from the table view
        tableDepenses.getItems().removeAll(selectedDepenses);
    }
}
   @FXML
void modifierDepense(ActionEvent event) throws IOException {
    // get the selected depense
    Depense selectedDepense = tableDepenses.getSelectionModel().getSelectedItem();

    // check if a depense is selected
    if (selectedDepense == null) {
        // show an error message if no depense is selected
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une dépense à modifier");
        alert.showAndWait();
        return;
    }

    // load the FXML file for the modifier depense form
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierDepense.fxml"));
    Parent root = loader.load();

    // pass the selected depense to the controller of the modifier depense form
    ModifierDepenseController controller = loader.getController();
    controller.setDepense(selectedDepense);

    // create a new stage and show the modifier depense form
    Stage stage = new Stage();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("Modifier la dépense");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.showAndWait();

    // update the table view after modification
    DepenseCRUD crud = new DepenseCRUD();
    List<Depense> depensesList = crud.afficherDepenses();
    ObservableList<Depense> depenses = FXCollections.observableArrayList(depensesList);
    tableDepenses.setItems(depenses);
}
    
}
