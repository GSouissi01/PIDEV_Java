/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.edu.esprit.services.ServiceReclamation;

/**
 * FXML Controller class
 *
 * @author SOUISSI
 */
public class AddReclamController implements Initializable {

    @FXML
    private TextArea taDescriptionReclam;
    @FXML
    private TextField tfSujetReclam;
    @FXML
    private TextField tfEmailReclam;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     private ServiceReclamation serviceReclamation;
    @FXML
    private void ajouterReclamation() {
        String email = tfEmailReclam.getText();
        String sujet = tfSujetReclam.getText();
        String description = taDescriptionReclam.getText();

        if (email.isEmpty() || sujet.isEmpty() || description.isEmpty()) {
            // Afficher un message d'erreur si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
            return;
        }

         try {
             ServiceReclamation sr = new ServiceReclamation();
        // Vérifier l'existence de l'utilisateur correspondant à l'adresse e-mail
        boolean utilisateurExiste = sr.utilisateurExiste(email);

        if (!utilisateurExiste) {
            // Afficher un message d'erreur si l'utilisateur n'existe pas
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("L'adresse e-mail spécifiée n'appartient à aucun utilisateur.");
            alert.showAndWait();
            return;
        }

        // Ajouter la nouvelle réclamation
        sr.ajouter(email, sujet, description);

        // Afficher un message de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("La réclamation a été ajoutée avec succès.");
        alert.showAndWait();

        // Effacer les champs
        tfEmailReclam.setText("");
        tfSujetReclam.setText("");
        taDescriptionReclam.setText("");
    } catch (SQLException e) {
        // Afficher un message d'erreur en cas d'échec de l'ajout
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de l'ajout de la réclamation.");
        alert.showAndWait();
        e.printStackTrace();
    }
}
}
