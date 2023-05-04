/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.edu.esprit.entities.fournisseur;
import static tn.edu.esprit.utils.MyConnection.MyConnection;


/**
 * FXML Controller class
 *
 * @author islem
 */
public class fournisseurController implements Initializable {

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
    private TextField societetf;
    @FXML
    private TextField teltf;
    @FXML
    private TextField respotf;
    private TableView<fournisseur> tabfourni;
    @FXML
    private Button updatebutton1;
    @FXML
    private ListView<fournisseur> listfourni;
    @FXML
    private TextField searchtf;
    @FXML
    private Button searchbtn;
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
    private Button test;
    @FXML
    private Button close;
    @FXML
    private Button fournisseur;
    @FXML
    private Button publicite;
    @FXML
    private Button user;
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
        Stage stageToClose = (Stage) close.getScene().getWindow();
        stageToClose.close();
    });
       
        showfournisseurs();
        //showRec();
        //searchRec();
    }

    public ObservableList<fournisseur> getfournisseurList() {
        ObservableList<fournisseur> compteList = FXCollections.observableArrayList();
        Connection conn = MyConnection();
        String query = "SELECT * FROM fournisseur";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            fournisseur fournisseur;

            while (rs.next()) {
                fournisseur = new fournisseur(rs.getInt("id"),rs.getInt("numtel") , rs.getString("societe"), rs.getString("nomrespo") );
                compteList.add(fournisseur);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return compteList;
    }

    /*/////////////////////////////////////////////////Controle de saisie/////////////////////////////////////////////////////////////////////*/
    private boolean validateFields() {
        if (societetf.getText().isEmpty()) {
            showAlert("Error", "societe field is required.");
            return false;
        }

        if (teltf.getText().isEmpty()) {
            showAlert("Error", "tel field is required.");
            return false;
        }
        if (respotf.getText().isEmpty()) {
            showAlert("Error", "respo field is required.");
            return false;
        }
        return true;
    }


  public void showfournisseurs() {
  listfourni.setStyle("-fx-background-color: transparent;");
    try {
        listfourni.getItems().clear();
        Connection conn = MyConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM fournisseur");

        // Define a predicate to filter the ResultSet
        Predicate<fournisseur> filterPredicate = fournisseur -> {
            String searchText = searchtf.getText().toLowerCase();
            if (searchText.isEmpty()) {
                // No search text, so all fournisseurs match
                return true;
            } else {
                // Check if the search text is contained in any of the fournisseur properties
                return fournisseur.getSociete().toLowerCase().contains(searchText)
                        || Integer.toString(fournisseur.getNumtel()).contains(searchText)
                        || fournisseur.getNomrespo().toLowerCase().contains(searchText)
                        || Integer.toString(fournisseur.getRating()).contains(searchText);
            }
        };

        while (rs.next()) {
            fournisseur currentFournisseur = new fournisseur(rs.getInt("id"), rs.getInt("numtel"), rs.getInt("rating"), rs.getString("societe"), rs.getString("nomrespo"));
            if (filterPredicate.test(currentFournisseur)) {
                listfourni.getItems().add(currentFournisseur);
            }
        }

        listfourni.setCellFactory(new Callback<ListView<fournisseur>, ListCell<fournisseur>>() {
            @Override
            public ListCell<fournisseur> call(ListView<fournisseur> param) {
                return new ListCell<fournisseur>() {
                    @Override
                    protected void updateItem(fournisseur item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            // Create a VBox to hold the content of the card
                            VBox card = new VBox();
                            card.setAlignment(Pos.CENTER);
                            card.setPadding(new Insets(10));
                            card.setSpacing(10);

                            // Create labels to display the fournisseur properties
                            Label societeLabel = new Label("Societe: " + item.getSociete());
                            Label telLabel = new Label("Telephone: " + item.getNumtel());
                            Label respoLabel = new Label("Nom Responsable: " + item.getNomrespo());
                            Label ratingLabel = new Label("Rating: " + item.getRating());
                            VBox ratingVBox = getRatingVBox(item);
                            // Add the labels to the card
                            card.getChildren().addAll(societeLabel, telLabel, respoLabel,ratingLabel,ratingVBox);

                            // Create a HBox to hold the edit and delete buttons
                            HBox buttonsBox = new HBox();
                            buttonsBox.setAlignment(Pos.CENTER);
                            buttonsBox.setSpacing(10);

                            // Create the edit button
                            Button editButton = new Button("Edit");
                            editButton.setOnAction(event -> {
                                societetf.setText(String.valueOf(item.getSociete()));
                                teltf.setText(String.valueOf(item.getNumtel()));
                                respotf.setText(String.valueOf(item.getNomrespo()));
                            });

                            // Create the delete button
                            Button deleteButton = new Button("Delete");
                            deleteButton.setOnAction(event -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setContentText("Are you sure you want to delete this fournisseur?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    try {
                                        PreparedStatement ps = null;
                                        String query = "DELETE FROM `fournisseur` WHERE id =" + item.getId();
                                        Connection conn = MyConnection();
                                        ps = conn.prepareStatement(query);
                                        ps.execute();
                                        listfourni.getItems().remove(item);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(fournisseurController.class.getName()).log(Level.SEVERE, null, ex);
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
        String societe  = societetf.getText();
        int tel = Integer.parseInt(teltf.getText()) ; 
        String respo = respotf.getText();
     
        
            fournisseur fournisseur = listfourni.getSelectionModel().getSelectedItem();
            String query = "UPDATE fournisseur SET societe = '" + societe + "' ,  numtel = '" + tel + "' , nomrespo = '" + respo + "'  WHERE id='" + fournisseur.getId() + "' ";
            executeQuery(query);
            showfournisseurs();
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

        // Add a title to the PDF
        Paragraph title = new Paragraph("Fournisseur Data");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        PdfPTable pdfTable = new PdfPTable(4);
        addTableHeader(pdfTable);
        addRows(pdfTable, listfourni);
        document.add(pdfTable);
        document.close();

        // Create a new FileChooser to allow the user to choose where to save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("table.pdf");

        // Set the initial directory for the FileChooser to the user's home directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Show the FileChooser and wait for the user to select a file
        File file = fileChooser.showSaveDialog(listfourni.getScene().getWindow());

        // If the user selected a file, copy the contents of the temporary file to the selected file
        if (file != null) {
            Files.copy(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    } catch (IOException | DocumentException e) {
        e.printStackTrace();
    }
}

private void addTableHeader(PdfPTable pdfTable) {
    pdfTable.addCell("ID");
    pdfTable.addCell("Societe");
    pdfTable.addCell("Numtel");
    pdfTable.addCell("Nomrespo");
}

private void addRows(PdfPTable pdfTable, ListView<fournisseur> listView) {
    ObservableList<fournisseur> items = listView.getItems();
    for (fournisseur item : items) {
        pdfTable.addCell(String.valueOf(item.getId()));
        pdfTable.addCell(item.getSociete());
        pdfTable.addCell(String.valueOf (item.getNumtel()));
        pdfTable.addCell(item.getNomrespo());
    }
}
    private void setRating(HBox ratingBox, int rating) {
        for (int i = 0; i < ratingBox.getChildren().size(); i++) {
            FontAwesomeIconView star = (FontAwesomeIconView) ratingBox.getChildren().get(i);
            if (i < rating) {
                star.setFill(javafx.scene.paint.Color.GOLD);
            } else {
                star.setFill(javafx.scene.paint.Color.GRAY);
            }
        }
    }

    public VBox getRatingVBox(fournisseur f) {
    // Create the rating VBox
    VBox ratingVBox = new VBox();
    ratingVBox.setAlignment(Pos.CENTER);
    ratingVBox.setSpacing(10);

    // Add the rating stars
    HBox ratingBox = new HBox();
    ratingBox.setAlignment(Pos.CENTER);
    ratingBox.setSpacing(5);
    for (int K = 0; K < 5; K++) {
        FontAwesomeIconView star = new FontAwesomeIconView(FontAwesomeIcon.STAR);
        star.setFill(Color.GRAY);
        star.setSize("2em");
        int index = K;
        star.setOnMouseClicked(event -> {
            int newRating = index + 1;
            try {
                PreparedStatement ps = null;
                String query = "UPDATE `fournisseur` SET `rating` = ? WHERE `id` = ?";
                Connection conn = MyConnection();
                ps = conn.prepareStatement(query);
                ps.setInt(1, newRating);
                ps.setInt(2, f.getId());
                ps.executeUpdate();
                f.setRating(newRating);
                setRating(ratingBox, newRating);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Rating Submitted");
                alert.setContentText("Thank you for your rating!");
                alert.showAndWait();
                showfournisseurs();
            } catch (SQLException ex) {
                Logger.getLogger(fournisseurController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ratingBox.getChildren().add(star);
    }
    ratingVBox.getChildren().add(ratingBox);

    // Add the checkbox
    return ratingVBox;
}

    @FXML
    private void search(ActionEvent event) {
        showfournisseurs(); 
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
    private void test(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fournisseuradd.fxml"));
        AnchorPane popupContent = loader.load();

        Popup popup = new Popup();
        popup.getContent().add(popupContent);

        // Add a close button with an "X" symbol
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10px;");
        closeButton.setOnAction(closeEvent -> {
            if (popup != null) {
                popup.hide();
            }
        });
        closeButton.setOnMouseClicked(closeEvent -> popup.hide());
        popupContent.getChildren().add(closeButton);

        popup.show(test.getScene().getWindow());
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
