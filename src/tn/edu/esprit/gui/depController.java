/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.edu.esprit.entities.Depense;
import tn.edu.esprit.services.DepenseCRUD;
import static tn.edu.esprit.utils.MyConnection.MyConnection;



/**
 * FXML Controller class
 *
 * @author islem
 */
public class depController implements Initializable {

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;


    private TextField nbrticket;
    private ComboBox<String> nomevent;
    private ComboBox<String> status;
    private TextField catego;
    private TextField prixticket;


    @FXML
    private Button updatebutton;
 

   
    @FXML
    private Button updatebutton1;

    @FXML
    private TextField txtLibelle;
    @FXML
    private TextField txtMontant;
    @FXML
    private TextField MontantTT;

    @FXML
    private ChoiceBox<String> py;
    @FXML
    private ListView<Depense> listdep;
    @FXML
    private DatePicker datetxt;
    @FXML
    private Button add;
    @FXML
    private Button chart;
    @FXML
    private Label paye;
    @FXML
    private Label npaye;
    private Button excel;
    @FXML
    private Button contrat;
    @FXML
    private Button typecontrat;
    @FXML
    private Button promotion;
    @FXML
    private Button reclamation;
    @FXML
    private Button product;
    @FXML
    private Button fournisseur;
    @FXML
    private Button publicite;
    @FXML
    private Button home;
    @FXML
    private Button close;
    @FXML
    private Button user;
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
       
        showDep();

        DepenseCRUD dep = new DepenseCRUD();

        // put data in the labels
        paye.setText(String.valueOf("les depense payé: "+dep.calculerTotalDepensesParEtat("payé")));
        npaye.setText(String.valueOf("les depense non payé: "+dep.calculerTotalDepensesParEtat("non payé")));
        
        //showRec();
        //searchRec();
        py.getItems().addAll("Payé","Non Payé");

        add.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PLUS));
        updatebutton.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
        chart.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.BAR_CHART));
        updatebutton1.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PRINT));

    

    }

    public ObservableList<Depense> getdepenseList() {
        ObservableList<Depense> compteList = FXCollections.observableArrayList();
        Connection conn = MyConnection();
        String query = "SELECT * FROM depense";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Depense depense;

            while (rs.next()) {
                depense = new Depense(
                    rs.getInt("id"),
                     rs.getFloat("prix"),
                      rs.getString("date"),
                         rs.getString("description"),
                          rs.getFloat("total_par_mois"),
                          rs.getString("etat")
                );
            }
          
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return compteList;
    }

    /*/////////////////////////////////////////////////Controle de saisie/////////////////////////////////////////////////////////////////////*/
    private boolean validateFields() {
        if (MontantTT.getText().isEmpty() || txtLibelle.getText().isEmpty() || txtMontant.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return false;
        }
    
        if (!txtMontant.getText().matches("[0-9]*\\.?[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant doit être un nombre");
            alert.showAndWait();
            return false;
        }
        if (!MontantTT.getText().matches("[0-9]*\\.?[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant doit être un nombre");
            alert.showAndWait();
            return false;
        }
        if (Float.parseFloat(txtMontant.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant ne peut pas être négatif");
            alert.showAndWait();
            return false;
        }
        if (Float.parseFloat(MontantTT.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant ne peut pas être négatif");
            alert.showAndWait();
            return false;
        }
        if (Float.parseFloat(txtMontant.getText()) > Float.parseFloat(MontantTT.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant ne peut pas être supérieur au montant total");
            alert.showAndWait();
            return false;
        }

        if (datetxt.getValue().isAfter(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La date ne peut pas être supérieure à la date d'aujourd'hui");
            alert.showAndWait();
            return false;
        }
        return true;
    }


  public void showDep() {
      listdep.setStyle("-fx-background-color: transparent;");
    try {
        listdep.getItems().clear();
        Connection conn = MyConnection();
        ResultSet rs = conn.createStatement().executeQuery("select * from depense");

        while (rs.next()) {
            listdep.getItems().add(new Depense(
                    rs.getInt("id"),
                    rs.getFloat("prix"),
                    rs.getString("date"),
                    rs.getString("description"),
                    rs.getFloat("total_par_mois"),
                    rs.getString("etat")
            ));
        }

        listdep.setCellFactory(new Callback<ListView<Depense>, ListCell<Depense>>() {
            @Override
            public ListCell<Depense> call(ListView<Depense> param) {
                return new ListCell<Depense>() {
                    @Override
                    protected void updateItem(Depense item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            // Create a VBox to hold the content of the card
                            VBox card = new VBox();
                            card.setAlignment(Pos.CENTER);
                            card.setPadding(new Insets(10));
                            card.setSpacing(5);

                            // Create labels to display the Depense properties
                            Label descriptioLabel = new Label("Description: " + item.getdescription());
                            Label montantLabel = new Label("Montant: " + item.getPrix());
                            Label dateLabel = new Label("Date: " + item.getDate());
                            Label etatLabel = new Label("Etat: " + item.getEtat());
                            Label totalLabel = new Label("Total: " + item.getTotal_par_mois());
                            // Add the labels to the card
                            card.getChildren().addAll(descriptioLabel, montantLabel, dateLabel, etatLabel, totalLabel);

                            // Create a HBox to hold the edit and delete buttons
                            HBox buttonsBox = new HBox();
                            buttonsBox.setAlignment(Pos.CENTER);
                            buttonsBox.setSpacing(5);

                            // Create the edit button
                            Button editButton = new Button("Edit");
                            editButton.setOnAction(event -> {
                                // Set the selected Depense to the text fields
                              
                                txtLibelle.setText(String.valueOf(item.getdescription()));
                                txtMontant.setText(String.valueOf(item.getPrix()));
                                MontantTT.setText(String.valueOf(item.getTotal_par_mois()));
                                //put datat in datepicker
                                String date = item.getDate();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                                LocalDate localDate = LocalDate.parse(date, formatter);
                                
                                datetxt.setValue(localDate);
                                //put data in choicebox
                               //get first item of ChoiceBox
                                String firstItem = py.getItems().get(0);
                                //get second item of ChoiceBox
                                String secondItem = py.getItems().get(1);
                                if (item.getEtat().equals(firstItem)) {
                                    py.setValue(firstItem);
                                } else {
                                    py.setValue(secondItem);
                                }
                               
                                




                            });

                            // Create the delete button
                            Button deleteButton = new Button("Delete");
                            deleteButton.setOnAction(event -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setContentText("Are you sure you want to delete this Depense?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    try {
                                        PreparedStatement ps = null;
                                        String query = "DELETE FROM `depense` WHERE id =" + item.getId();
                                        Connection conn = MyConnection();
                                        ps = conn.prepareStatement(query);
                                        ps.execute();
                                        listdep.getItems().remove(item);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(depController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });

                            // Add the buttons to the HBox
                            buttonsBox.getChildren().addAll(editButton, deleteButton);

                            // Add the HBox to the card
                            card.getChildren().add(buttonsBox);

                            // Set the cell content to the card
                            setGraphic(card);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}


  
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    

    @FXML
    private void onupdate(ActionEvent event) {
        if (event.getSource() == updatebutton) {
             if (validateFields()) {
    
      //check if selecteditem
            if (listdep.getSelectionModel().getSelectedItem() == null) {
                showAlert("Error", "Please select a Depense");
                return;
            }
            Depense dep = listdep.getSelectionModel().getSelectedItem();
            
            String query = "UPDATE `depense` SET `prix`='" + txtMontant.getText() + "',`date`='" + datetxt.getValue() + "',`description`='" + txtLibelle.getText() + "',`total_par_mois`='" + MontantTT.getText() + "',`etat`='" + py.getValue() + "' WHERE id =" + dep.getId();
            executeQuery(query);
            //altert update
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Depense updated successfully");
            alert.showAndWait();

            showDep();  
        }
        }
    }

    private void executeQuery(String query) {
        Connection conn = MyConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }
@FXML
private void handle(ActionEvent event) {
    Document document = new Document();
    try {
        // Create a temporary file with a unique name to store the PDF
        File tempFile = File.createTempFile("table", ".pdf");

        // Set the file to be deleted on exit
        tempFile.deleteOnExit();

        // Write the PDF to the temporary file
        PdfWriter.getInstance(document, new FileOutputStream(tempFile));
        document.open();

  // Add a title to the PDF document with custom font and color
  Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLUE);
  Paragraph title = new Paragraph("Liste des Dépenses", titleFont);
  title.setAlignment(Element.ALIGN_CENTER);
  document.add(title);
  document.add(new Paragraph("\n"));





        PdfPTable pdfTable = new PdfPTable(6);
        addTableHeader(pdfTable);
        addRows(pdfTable, listdep );
        document.add(pdfTable);
        document.close();

        // Create a new FileChooser to allow the user to choose where to save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("table.pdf");

        // Set the initial directory for the FileChooser to the user's home directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Show the FileChooser and wait for the user to select a file
        File file = fileChooser.showSaveDialog(listdep.getScene().getWindow());

           // Show a success message
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Impression");
           alert.setHeaderText(null);
           alert.setContentText("Les dépenses sélectionnées ont été imprimées avec succès en PDF.");
           alert.showAndWait();
           
           // Open the PDF file with the default PDF viewer
           if (Desktop.isDesktopSupported()) {
               Desktop.getDesktop().open(tempFile);
           }

        // If the user selected a file, copy the contents of the temporary file to the selected file
        if (file != null) {
            Files.copy(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    } catch (IOException | DocumentException e) {
        e.printStackTrace();
    }
}

private void addTableHeader(PdfPTable pdfTable) {
    Stream.of("ID", "Description", "Prix", "Date", "Total", "Etat")
            .forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(columnTitle));
                pdfTable.addCell(header);
            });
}

private void addRows(PdfPTable pdfTable, ListView<Depense> listView) {
    ObservableList<Depense> items = listView.getItems();
    for (Depense item : items) {
        pdfTable.addCell(String.valueOf(item.getId()));
        pdfTable.addCell(item.getdescription());
        pdfTable.addCell(String.valueOf(item.getPrix()));
        pdfTable.addCell(item.getDate());
        pdfTable.addCell(String.valueOf(item.getTotal_par_mois()));
        pdfTable.addCell(item.getEtat() );
}


}

    @FXML
    private void add(ActionEvent event) {
        if (event.getSource() == add) {
            if (validateFields()) {
                String query = "INSERT INTO `depense`(`prix`, `date`, `description`, `total_par_mois`, `etat`) VALUES ('" + txtMontant.getText() + "','" + datetxt.getValue() + "','" + txtLibelle.getText() + "','" + MontantTT.getText() + "','" + py.getValue() + "')";
                executeQuery(query);
                //altert add
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Depense added successfully");
                alert.showAndWait();

                showDep();
            }
        }
        
    }

    @FXML
    private void chart(ActionEvent event) {
        if (event.getSource() == chart) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("stat.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                
            }
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
    private void home(ActionEvent event) {
               try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dash.fxml"));
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
    private void reclamation(ActionEvent event) {
               try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BackReclam.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) reclamation.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void user(ActionEvent event) {
               try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BanckEnd.fxml"));
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

    @FXML
    private void handleLogOutBtn(ActionEvent event) {
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
    private void promotion(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPromotion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) promotion.getScene().getWindow();
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

    
    }
    
    

