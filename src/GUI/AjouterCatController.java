/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Categorie;
import entities.Depense;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import services.CategorieCRUD;
import services.DepenseCRUD;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Khalil.Jammezi
 */
public class AjouterCatController implements Initializable {

    @FXML
    private TextField fxNom;
    @FXML
    private TextField fxDescription;
    @FXML
    private TextField fxCode;
    @FXML
    private TextField fxImage;
    @FXML
    private Button fxButton;
    @FXML
    private Button fxAnnulerButton;
    @FXML
    private Button fxBrowseButton;
    @FXML
    private ComboBox<Depense> fxDepenseId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         // Populate the ComboBox with depenses
 DepenseCRUD depenseCRUD = new DepenseCRUD();
    ObservableList<Depense> depenses = FXCollections.observableArrayList(depenseCRUD.afficherDepenses());

    fxDepenseId.setItems(depenses);
   
    

        
    }    

    @FXML
    private void save(ActionEvent event) {
        if (fxNom.getText().isEmpty() || fxDescription.getText().isEmpty() || fxCode.getText().isEmpty() || fxImage.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return;
        }
        if (fxNom.getText().length() >10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le nom ne peut pas dépasser 10 caractères");
            alert.showAndWait();
            return;
        }
        if (fxDescription.getText().length() > 200) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("La description ne peut pas dépasser 200 caractères");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(fxCode.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le code ne peut pas être négatif");
            alert.showAndWait();
            return;
        }
   try{
        CategorieCRUD categorieCRUD = new CategorieCRUD();
        // Create a Categorie object with the values from the UI components
        Categorie categorie = new Categorie();
        categorie.setNom(fxNom.getText());
        categorie.setDescription(fxDescription.getText());
        categorie.setCode(Integer.parseInt(fxCode.getText())); 
        categorie.setImage(fxImage.getText());
        categorie.setDepenseId(5);
        categorieCRUD.ajouterCategorie(categorie);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Catégorie ajoutée avec succès");
        alert.showAndWait();
        fxNom.getScene().getWindow().hide();
    }catch(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite");
        alert.showAndWait();
    }

    }
    
      @FXML
    private void annuler(ActionEvent event) {
        fxAnnulerButton.getScene().getWindow().hide();
    }

    @FXML
    private void browseImage(ActionEvent event) {
         FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"),
            new ExtensionFilter("All Files", "*.*")
        );
        Stage stage = (Stage) fxButton.getScene().getWindow();
        String imagePath = fileChooser.showOpenDialog(stage).getPath();
        fxImage.setText(imagePath);
    }
    
}
