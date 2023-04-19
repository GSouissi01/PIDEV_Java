/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.contrat;

import static DBCnx.MyConnection.MyConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author islem
 */
public class contratAddController implements Initializable {

    @FXML
    private Button btnajouter;

    contratController obj = new contratController();
    @FXML
    private TextField montanttf;
    @FXML
    private TextField imagetf;
    @FXML
    private DatePicker datesignaturetf;
    @FXML
    private DatePicker dateexpirationtf;
    @FXML
    private Button btnbrowse;
    @FXML
    private TextField nomcontrattf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

private boolean validateFields() {
        LocalDate datedeb = datesignaturetf.getValue();
        LocalDate datefin = dateexpirationtf.getValue();
        LocalDate today = LocalDate.now();
        if (imagetf.getText().isEmpty()) {
            showAlert("Error", "image field is required.");
            return false;
        }

        if (montanttf.getText().isEmpty()) {
            showAlert("Error", "Prix field is required.");
            return false;
        } else if (!montanttf.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert("Error", "Prix must be a number.");
            return false;
        }
        if (datedeb == null) {
            showAlert("Error", "Please select a start date.");
            return false;
        }

        if (datedeb.isBefore(today)) {
            showAlert("Error", "The start date must be in the future.");
            return false;
        }

        if (datefin == null) {
            showAlert("Error", "Please select an end date.");
            return false;
        }

        if (datefin.isBefore(today)) {
            showAlert("Error", "The end date must be in the future.");
            return false;
        }

        if (datefin.isBefore(datedeb)) {
            showAlert("Error", "The end date must be after the start date.");
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
                obj.showcontrats();
            }
        }

    }

private void executeQuery(String query) {
    try (Connection conn = MyConnection();
         PreparedStatement statement = conn.prepareStatement(query)) {
        statement.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

 private void insert() {
    Integer montat = Integer.valueOf(montanttf.getText());
    String image = imagetf.getText();
    String nom = nomcontrattf.getText();
    String datedeb = String.valueOf(datesignaturetf.getValue());
    String datefin = String.valueOf(dateexpirationtf.getValue());

    validateFields();
    Connection conn = MyConnection();
    String query = "INSERT INTO `contrat`(`nomcontrat`, `datesignature`, `dateexpiration`, `montant`, `imagecontrat`) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
        statement.setString(1, nom);
        statement.setString(2, datedeb);
        statement.setString(3, datefin);
        statement.setInt(4, montat);
        statement.setString(5, image);
        statement.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("contrat ");
        alert.setHeaderText(null);
        alert.setContentText("added successfully");
        alert.showAndWait();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    @FXML
    private void browse(ActionEvent event) throws FileNotFoundException {
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
