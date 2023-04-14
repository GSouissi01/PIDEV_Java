package GUI;

import entities.Categorie;
import entities.Depense;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.CategorieCRUD;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class Page3Controller implements Initializable {

    @FXML
    private TableColumn<Categorie, Integer> colLibelle; // TableColumn for "Libelle" data
    @FXML
    private Button btnAjouter; // Button for "Ajouter" action
    @FXML
    private Button btnSupprimer; // Button for "Supprimer" action
    @FXML
    private Button btnModifier; // Button for "Modifier" action
    @FXML
    private TableColumn<Categorie, String> colNom; // TableColumn for "Nom" data
    @FXML
    private TableColumn<Categorie, String> colDescription; // TableColumn for "Description" data
    @FXML
    private TableColumn<Categorie, String> colCode; // TableColumn for "Code" data
    @FXML
    private TableView<Categorie> tableCat; // TableView for displaying data

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Add initialization logic here
        CategorieCRUD crud = new CategorieCRUD();
        // configure table columns
        colLibelle.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        // load data into table
        List<Categorie> categorieList = crud.afficherCategories();
        ObservableList<Categorie> categorieObservableList = FXCollections.observableArrayList(categorieList);
        tableCat.setItems(categorieObservableList);

    }

    @FXML   
    private void ajouterCat(ActionEvent event) throws IOException {
        // TODO: Handle "Ajouter" action here
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterCat.fxml"));
    Parent root = loader.load();
    // create new window
    Stage stage = new Stage();
    stage.setTitle("Ajouter une catégorie ");
    stage.setScene(new Scene(root));
    // display window
    stage.showAndWait();
    // update table view with new data
    CategorieCRUD crud = new CategorieCRUD();
   ObservableList<Categorie> categories = FXCollections.observableArrayList(crud.afficherCategories());
    tableCat.setItems(categories);

    }

    @FXML
    private void supprimerCat(ActionEvent event) {
        try {
             // check if a depense is selected
    if (tableCat == null) {
        // show an error message if no depense is selected
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une dépense à modifier");
        alert.showAndWait();
        return;
    }
            CategorieCRUD crud = new CategorieCRUD();
            crud.supprimerCategorie(tableCat.getSelectionModel().getSelectedItem().getId());
            ObservableList<Categorie> categories = FXCollections.observableArrayList(crud.afficherCategories());
            tableCat.setItems(categories);

        } catch (Exception e) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de suppression");
            alert.setContentText("Veuillez selectionner une catégorie");
            alert.showAndWait();
        }
    }
  
    @FXML
    private void modifierCat(ActionEvent event) throws IOException {
        // get selected categorie
        Categorie categorie = tableCat.getSelectionModel().getSelectedItem();
        // check if a categorie is selected
        if (categorie == null) {
            // show an error message if no categorie is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une catégorie à modifier");
            alert.showAndWait();
            return;
        }
        // load data into the form
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierCat.fxml"));
        Parent root = loader.load();
        //pass selected categorie to the form
        ModifierCatController controller = loader.getController();
        controller.setCategorie(categorie);

        // create new Stage and show modifer categorie form
        Stage stage = new Stage();
        Scene s = new Scene(root);
        stage.setScene(s);
        stage.setTitle("Modifier une catégorie");
        stage.showAndWait();
        // update table view with new data
        CategorieCRUD crud = new CategorieCRUD();
        List<Categorie> categories = FXCollections.observableArrayList(crud.afficherCategories());
       ObservableList<Categorie> categorieObservableList = FXCollections.observableArrayList(categories);
        tableCat.setItems(categorieObservableList);
    
    }

}
