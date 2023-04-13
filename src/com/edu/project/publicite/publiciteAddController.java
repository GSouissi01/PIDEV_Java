/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.publicite;

import static DBCnx.MyConnection.MyConnection;
import com.edu.project.entities.fournisseur;
import com.edu.project.entities.fournisseur;
import com.edu.project.fournisseur.fournisseurController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class publiciteAddController implements Initializable {

    publiciteController obj = new publiciteController();
    @FXML
    private Button btnajouter;

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
    private Label dateDebut;
    @FXML
    private Label DateFin;
    @FXML
    private ImageView imageview;

 private void insert() {
    String produit = produittf.getText();
    String image = imagetf.getText();
    int idfourni = selectedfournisseur.getId();

    validateFields();

    String query = "INSERT INTO publicite (fournisseur_id, image, produit) VALUES (?, ?, ?)";

    try (Connection conn = MyConnection();
         PreparedStatement statement = conn.prepareStatement(query)) {
        statement.setInt(1, idfourni);
        statement.setString(2, image);
        statement.setString(3, produit);
        statement.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("publicite");
        alert.setHeaderText(null);
        alert.setContentText("added successfully");
        alert.showAndWait();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            test();
        } catch (SQLException ex) {
            Logger.getLogger(publiciteAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void OnCreate(ActionEvent event) {
        if (event.getSource() == btnajouter) {
            if (validateFields()) {
                insert();
                obj.showpublicites();
            }

        }
    }

    private void test() throws SQLException {
        List<fournisseur> fournis = retrievefournisseursFromDatabase();
        nomfourni.getItems().addAll(fournis);
        nomfourni.setPromptText("Select fournisseur");
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
        List<fournisseur> fournis = new ArrayList<>();
        Connection conn = MyConnection();
        // Create a statement and execute the query
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, societe FROM fournisseur");

        // Iterate over the result set and create fournisseur objects
        while (rs.next()) {
            int id = rs.getInt("id");
            String societe = rs.getString("societe");
            fournisseur fourni = new fournisseur(id, societe);
            fournis.add(fourni);
        }

        // Close the statement and result set
        rs.close();
        stmt.close();

        return fournis;
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
            imageview.setImage(image);
            // set the image view in your UI, e.g.

        }
    }

}
