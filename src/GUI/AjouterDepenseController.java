package GUI;

import entities.Depense;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import services.DepenseCRUD;

public class AjouterDepenseController implements Initializable {

    @FXML
    private TextField fxLibelle;
    @FXML
    private TextField fxMontant;
    @FXML
    private TextField ttt;
    @FXML
    private DatePicker fxDate;
    @FXML
    private CheckBox fxPaye;
    @FXML
    private Button fxButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save(ActionEvent event) {
         if(fxLibelle.getText().isEmpty() || fxMontant.getText().isEmpty() || fxDate.getValue() == null || ttt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return;
        }
        if (Float.parseFloat(fxMontant.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant ne peut pas être négatif");
            alert.showAndWait();
            return;
        }
        if (Float.parseFloat(ttt.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant ne peut pas être négatif");
            alert.showAndWait();
            return;
        }
        if (Float.parseFloat(fxMontant.getText()) > Float.parseFloat(ttt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant ne peut pas être supérieur au montant total");
            alert.showAndWait();
            return;
        }
        if (fxDate.getValue().isAfter(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La date ne peut pas être supérieure à la date d'aujourd'hui");
            alert.showAndWait();
            return;
        }
        String libelle = fxLibelle.getText();
float montant = Float.parseFloat(fxMontant.getText());
        String date = fxDate.getValue().toString();
        float tt =  Float.parseFloat(ttt.getText());
        String etat = fxPaye.isSelected() ? "Payé" : "Non payé";
        
        Depense depense = new Depense(montant, date,libelle,tt ,etat);


        DepenseCRUD depenseCRUD = new DepenseCRUD();
        try {
            depenseCRUD.ajouterDepense(depense);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ajouté depense avec succés");
            alert.setHeaderText(null);
            alert.setContentText("Depense ajouté avec succés");
            alert.showAndWait();
            fxLibelle.setText("");
            fxMontant.setText("");
            fxDate.setValue(null);
            ttt.setText("");

            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
                
    }
    
}
