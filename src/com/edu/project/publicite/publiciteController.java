/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.publicite;

import DBCnx.MyConnection;
import static DBCnx.MyConnection.MyConnection;
import com.edu.project.entities.publicite;
import com.edu.project.entities.fournisseur;
import com.edu.project.entities.fournisseur;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author islem
 */
public class publiciteController implements Initializable {

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private Button updatebutton;

    @FXML
    private TableColumn<publicite, String> actions;

    fournisseur selectedfournisseur;
    @FXML
    private TextField produittf;
    @FXML
    private ComboBox<fournisseur> nomfourni;
    @FXML
    private TextField imagetf;
    @FXML
    private Button btnbrowse;
    @FXML
    private TableView<publicite> tabfourni;
    @FXML
    private TableColumn<publicite, String> colproduit;
    @FXML
    private TableColumn<publicite, String> colimage;
    @FXML
    private TableColumn<publicite, Integer> colfourni;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showpublicites();
       
        try {
            test();
        } catch (SQLException ex) {
            Logger.getLogger(publiciteAddController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //showRec();
        //searchRec();
    }

    public ObservableList<publicite> getpubliciteList() {
        ObservableList<publicite> compteList = FXCollections.observableArrayList();
        Connection conn = MyConnection();
        String query = "SELECT * FROM publicite";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            publicite publicite;
            

            while (rs.next()) {
                 
                publicite = new publicite(rs.getInt("id"),rs.getInt("fournisseur_id"), rs.getString("image"), rs.getString("produit"));
                compteList.add(publicite);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        System.out.println(compteList);
        return compteList;
    }

    /*/////////////////////////////////////////////////Controle de saisie/////////////////////////////////////////////////////////////////////*/

    private boolean validateFields() {
        if (produittf.getText().isEmpty()) {
            showAlert("Error", "image field is required.");
            return false;
        }

        if (imagetf.getText().isEmpty()) {
            showAlert("Error", "image field is required.");
            return false;
        }
        return true;
    }

    public void showpublicites() {
        ObservableList<publicite> list = getpubliciteList();
        colfourni.setCellValueFactory(new PropertyValueFactory<>("idfournisseur"));
        colproduit.setCellValueFactory(new PropertyValueFactory<>("produit"));
        colimage.setCellValueFactory(new PropertyValueFactory<>("image"));

        tabfourni.setItems(list);
        Callback<TableColumn<publicite, String>, TableCell<publicite, String>> cellFoctory = (TableColumn<publicite, String> param) -> {
            // make cell containing buttons
            final TableCell<publicite, String> cell = new TableCell<publicite, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    publicite publicite = null;
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Alert!");
                                alert.setContentText("This is an alert");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {

                                    try {
                                        PreparedStatement ps = null;
                                        publicite publicites;
                                        publicites = tabfourni.getSelectionModel().getSelectedItem();
                                        String query = "DELETE FROM `fournisseur` WHERE id =" + publicites.getId();
                                        Connection conn = MyConnection();
                                        ps = conn.prepareStatement(query);
                                        ps.execute();
                                        showpublicites();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(publiciteController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else if (result.get() == ButtonType.CANCEL) {
                                    showpublicites();
                                }
                            }
                        });

                        editIcon.setOnMouseClicked((new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {

                                publicite publicites = tabfourni.getSelectionModel().getSelectedItem();

                                produittf.setText(String.valueOf(publicites.getProduit()));
                                

                            }

                        }));
                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);
                    }
                }

            };
            return cell;
        };
        actions.setCellFactory(cellFoctory);
        tabfourni.setItems(list);

    }

    private void showAlert(String title, String fournisseur) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(fournisseur);
        alert.showAndWait();
    }

@FXML
private void onupdate(ActionEvent event) {
    if (event.getSource() == updatebutton) {
        String produit = produittf.getText();
        String image = imagetf.getText(); 
            
        publicite publicite = tabfourni.getSelectionModel().getSelectedItem();
        String query = "UPDATE publicite SET  fournisseur_id = ? , image = ?, produit = ? WHERE id = ?";
        
        try {
            PreparedStatement statement = MyConnection().prepareStatement(query);
            statement.setInt(1, selectedfournisseur.getId());
            statement.setString(2, image);
            statement.setString(3, produit );
            statement.setInt(4, publicite.getId());
            
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful!");
                showpublicites();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    private void test() throws SQLException {
        List<fournisseur> reclamations = retrievefournisseursFromDatabase();
        nomfourni.getItems().addAll(reclamations);
        nomfourni.setPromptText("Select a reclamation");
// Set the cell factory to display only the name of the reclamations
        nomfourni.setCellFactory(c -> new ListCell<fournisseur>() {
            @Override
            protected void updateItem(fournisseur fourni, boolean empty) {
                super.updateItem(fourni, empty);
                if (empty || fourni == null) {
                    setText(null);
                } else {
                    setText(fourni.getSociete());
                }
            }
        });

// Set the button cell to display only the name of the reclamations
        nomfourni.setButtonCell(new ListCell<fournisseur>() {
            @Override
            protected void updateItem(fournisseur fourni, boolean empty) {
                super.updateItem(fourni, empty);
                if (empty || fourni == null) {
                    setText(null);
                } else {
                    setText(fourni.getSociete());
                }
            }
        });
        // Add an event listener to the ComboBox to display the selected reclamation
        nomfourni.setOnAction(e -> {
            selectedfournisseur = nomfourni.getSelectionModel().getSelectedItem();
            if (selectedfournisseur != null) {
                System.out.println("Selected fournisseur: " + selectedfournisseur.getSociete()+ ", ID: " + selectedfournisseur.getId());
            }
        });
    }

    private List<fournisseur> retrievefournisseursFromDatabase() throws SQLException {
        List<fournisseur> reclamations = new ArrayList<>();
        Connection conn = MyConnection();
        // Create a statement and execute the query
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, societe FROM fournisseur");

        // Iterate over the result set and create fournisseur objects
        while (rs.next()) {
            int id = rs.getInt("id");
            String societe = rs.getString("societe");
            fournisseur fourni = new fournisseur(id, societe);
            reclamations.add(fourni);
        }

        // Close the statement and result set
        rs.close();
        stmt.close();

        return reclamations;
    }


     @FXML
    private void actionperformed(ActionEvent event) throws FileNotFoundException {
        FileChooser filechooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");

        //filechooser.setInitialDirectory(new File("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Notex\\src\\com\\gn\\module\\evenements\\image"));
        filechooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);

        File file = filechooser.showOpenDialog(new Stage());
        if (file != null) {
            Image image = new Image(new FileInputStream(file));
            imagetf.setText(file.getName());
            // set fit height and width of ImageView to the image size
            
            // set the image view in your UI, e.g.

        }
    }
}
