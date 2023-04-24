/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Depense;
import java.io.FileOutputStream;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Desktop;
import java.io.File;
import javafx.scene.control.Label;
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
    private TableColumn<Depense, Double> colMontant;

    @FXML
    private TableColumn<Depense, String> colDate;

    @FXML
    private TableColumn<Depense, Boolean> colPaye;

    @FXML
    private Button btnAjouter;


    @FXML
    private Button btnModifier;
    @FXML
    private Button stat;
    @FXML
    private Label mtpaye;
    @FXML
    private Label mtNpaye;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button d;
    @FXML
    private TableColumn<Depense, String> coldesc;
    @FXML
    private TableColumn<?, ?> colPaye1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnModifier.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT));
        btnSupprimer.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TRASH));
        btnAjouter.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PLUS));
        stat.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.BAR_CHART));
        d.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT));

        
        
        DepenseCRUD crud = new DepenseCRUD();
        // load data into table
   
List<Depense> depensesList = crud.afficherDepenses();

        // configure table columns

        colMontant.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPaye.setCellValueFactory(new PropertyValueFactory<>("etat"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("id"));

        

ObservableList<Depense> depensesObservableList = FXCollections.observableList(depensesList);

   // make cell containing buttons
        

        tableDepenses.setItems(depensesObservableList);

        //set text for label
        mtpaye.setText(String.valueOf(crud.calculerTotalDepensesParEtat("payé")));
        mtNpaye.setText(String.valueOf(crud.calculerTotalDepensesParEtat("non payé")));
        
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


    @FXML
    private void imprimer(ActionEvent event) {
          try {
        // Get the selected depenses from the table
        ObservableList<Depense> selectedDepenses = tableDepenses.getSelectionModel().getSelectedItems();

        if (selectedDepenses.isEmpty()) {
            // Show an alert if no depense is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune dépense sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner au moins une dépense pour l'impression.");
            alert.showAndWait();
        } else {
            // Create a new PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("SelectedDepenses.pdf"));
            document.open();

            // Add a title to the PDF document with custom font and color
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLUE);
            Paragraph title = new Paragraph("Liste des Dépenses Sélectionnées", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Create a table to display the depenses
            PdfPTable table = new PdfPTable(4); // 4 columns
            table.setWidthPercentage(100); // Set table width to 100% of the page

            // Set table headers with custom font and color
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            headerCell.setPhrase(new Phrase("Description", headerFont));
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Montant Total", headerFont));
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Date", headerFont));
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Payé", headerFont));
            table.addCell(headerCell);

            // Loop through the selected depenses and add them to the PDF document
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            for (Depense depense : selectedDepenses) {
                PdfPCell cell = new PdfPCell();
                cell.setPadding(5);
                cell.setPhrase(new Phrase(depense.getdescription(), cellFont));
                table.addCell(cell);

                cell.setPhrase(new Phrase(String.valueOf(depense.getTotal_par_mois()), cellFont));
                table.addCell(cell);

                cell.setPhrase(new Phrase(depense.getDate(), cellFont));
                table.addCell(cell);

                cell.setPhrase(new Phrase(depense.getEtat(), cellFont));
                table.addCell(cell);
            }

            document.add(table); // Add the table to the document
            document.close();

            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Impression");
            alert.setHeaderText(null);
            alert.setContentText("Les dépenses sélectionnées ont été imprimées avec succès en PDF.");
            alert.showAndWait();
            
            // Open the PDF file with the default PDF viewer
            if (Desktop.isDesktopSupported()) {
                File file = new File("SelectedDepenses.pdf");
                Desktop.getDesktop().open(file);
            }
        }
    } catch (Exception e) {
        // Show an error message if an exception occurs
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de l'impression des dépenses sélectionnées en PDF.");
        alert.showAndWait();
        e.printStackTrace();
    }
    }

    @FXML
    private void stat(ActionEvent event) throws IOException {
        //load the FXML file for stat 
        
    FXMLLoader loader = new FXMLLoader(getClass().getResource("stat.fxml"));
    Parent root = loader.load();
    // create new window
    Stage stage = new Stage();
    stage.setTitle("stat dépense");
    stage.setScene(new Scene(root));
    // display window
    stage.showAndWait();
    }




}