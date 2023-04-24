package GUI;

import entities.Depense;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import services.DepenseCRUD;

public class ModifierDepenseController implements Initializable {



    @FXML
    private TextField txtLibelle;

    @FXML
    private TextField txtMontant;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Button btnModifier;

    private Depense depense;
    private TextField txtDate;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Region navHome;
    @FXML
    private Text libid;
    @FXML
    private TextField txtMontantTT;
    @FXML
    private ChoiceBox<String> py;
    
    
 


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            dpDate = new DatePicker(); // or initialize it with the appropriate DatePicker object

    py.getItems().addAll("Payé","Non Payé");


    }

    public void setDepense(Depense depense) {
        this.depense = depense;
        // set the fields with the values of the selected depense
        txtLibelle.setText(depense.getdescription());
        txtMontant.setText(String.valueOf(depense.getPrix()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); // specify the date format with time part
        LocalDateTime dateTime = LocalDateTime.parse(depense.getDate(), formatter); // parse the date with the formatter
         System.out.println(dateTime.toLocalDate());
         System.out.println(dateTime);
        dpDate.setValue(dateTime.toLocalDate()); // set the date in the DatePicker
    
        libid.setText(String.valueOf(depense.getId()));
    
        txtMontantTT.setText(String.valueOf(depense. getTotal_par_mois()));
    
        if(!depense.getEtat().contains("Payé")){
            py.selectionModelProperty().get().select(0);
    
        } else {
            py.selectionModelProperty().get().select(1);
        }
        
    }

    @FXML
    void modifierDepense() {
        if(txtLibelle.getText().isEmpty() || txtMontant.getText().isEmpty() || dpDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return;
        }
        if (Float.parseFloat(txtMontant.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant ne peut pas être négatif");
            alert.showAndWait();
            return;
        }
        if (Float.parseFloat(txtMontantTT.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant ne peut pas être négatif");
            alert.showAndWait();
            return;
        }
        if (Float.parseFloat(txtMontant.getText()) > Float.parseFloat(txtMontantTT.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant ne peut pas être supérieur au montant total");
            alert.showAndWait();
            return;
        }
        if (dpDate.getValue().isAfter(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La date ne peut pas être supérieure à la date d'aujourd'hui");
            alert.showAndWait();
            return;
        }

        // update the depense in the database
        DepenseCRUD crud = new DepenseCRUD();
Depense updatedDepense = new Depense(depense.getId(), Float.parseFloat(txtMontant.getText()),dpDate.getValue().toString(), txtLibelle.getText(),Float.parseFloat(txtMontantTT.getText()),py.getValue());

        boolean result = crud.modifierDepense(updatedDepense);

        if (result) {
            // show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setHeaderText(null);
            alert.setContentText("La dépense a été modifiée avec succès");
            alert.showAndWait();

            // close the dialog window
            btnModifier.getScene().getWindow().hide();
        } else {
            // show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la modification de la dépense");
            alert.showAndWait();
        }
    }

    @FXML
    private void annuler(ActionEvent event) {

        btnAnnuler.getScene().getWindow().hide();
    }
    

    @FXML
    private void showHomeView(MouseEvent event) {
    }

}
