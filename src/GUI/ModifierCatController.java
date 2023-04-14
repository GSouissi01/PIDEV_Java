/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.CategorieCRUD;
import entities.Categorie;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * 
 */
public class ModifierCatController implements Initializable {
   private Categorie categorie;
    @FXML
    private TextField fxNom;
    @FXML
    private TextField fxDescription;
    @FXML
    private TextField fxCode;
    @FXML
    private TextField fxImage;
    @FXML
    private Button fxAnnulerButton;
    @FXML
    private Button fxBrowseButton;
    @FXML
    private Button fxModifer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
 public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
        fxNom.setText(categorie.getNom());
        fxDescription.setText(categorie.getDescription());
        fxCode.setText(String.valueOf(categorie.getCode()));
        fxImage.setText(categorie.getImage());

    }
    @FXML
    private void modifier(ActionEvent event) {
        if (fxNom.getText().isEmpty() || fxDescription.getText().isEmpty() || fxCode.getText().isEmpty() || fxImage.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return;
        }
        if (fxNom.getText().length() > 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le nom ne doit pas dépasser 10 caractères");
            alert.showAndWait();
            return;
        }
        if (fxDescription.getText().length() > 200) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("La description ne doit pas dépasser 200 caractères");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(fxCode.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le code ne doit pas être négatif");
            alert.showAndWait();
            return;
        }

        
        
      CategorieCRUD categorieCRUD = new CategorieCRUD();
        categorie.setNom(fxNom.getText());
        categorie.setDescription(fxDescription.getText());
        categorie.setCode(Integer.parseInt(fxCode.getText()));
        categorie.setImage(fxImage.getText());
        categorieCRUD.modifierCategorie(categorie);
        fxModifer.getScene().getWindow().hide();
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

    Stage stage = (Stage) fxBrowseButton.getScene().getWindow();
    File selectedFile = fileChooser.showOpenDialog(stage);

    if (selectedFile != null) {
        String imagePath = selectedFile.getPath();
        fxImage.setText(imagePath);
    } else {
        // Handle case when no file is selected
        System.out.println("Aucun fichier sélectionné.");
    }
    }
    
}
