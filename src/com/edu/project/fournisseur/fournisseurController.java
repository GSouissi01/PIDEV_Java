/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.fournisseur;

import DBCnx.MyConnection;
import static DBCnx.MyConnection.MyConnection;
import com.edu.project.entities.fournisseur;
import com.edu.project.entities.fournisseur;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;


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
    private TableColumn<fournisseur, String> actions;

    @FXML
    private TextField societetf;
    @FXML
    private TextField teltf;
    @FXML
    private TextField respotf;
    @FXML
    private TableView<fournisseur> tabfourni;
    @FXML
    private TableColumn<fournisseur, String> colsociete;
    @FXML
    private TableColumn<fournisseur, Integer> coltel;
    @FXML
    private TableColumn<fournisseur, String> colrespo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
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
        ObservableList<fournisseur> list = getfournisseurList();
        colsociete.setCellValueFactory(new PropertyValueFactory<>("societe"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("numtel"));
        colrespo.setCellValueFactory(new PropertyValueFactory<>("nomrespo"));
  
        

        tabfourni.setItems(list);
        Callback<TableColumn<fournisseur, String>, TableCell<fournisseur, String>> cellFoctory = (TableColumn<fournisseur, String> param) -> {
            // make cell containing buttons
            final TableCell<fournisseur, String> cell = new TableCell<fournisseur, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    fournisseur fournisseur = null;
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
                                    fournisseur fournisseurs;
                                    fournisseurs = tabfourni.getSelectionModel().getSelectedItem();
                                    String query = "DELETE FROM `fournisseur` WHERE id =" + fournisseurs.getId();
                                    Connection conn = MyConnection();
                                    ps = conn.prepareStatement(query);
                                    ps.execute();
                                    showfournisseurs();

                                } catch (SQLException ex) {
                                    Logger.getLogger(fournisseurController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                } else if (result.get() == ButtonType.CANCEL) {
                                    showfournisseurs();
                                }
                            }
                        });

                        editIcon.setOnMouseClicked((new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {

                                fournisseur fournisseurs = tabfourni.getSelectionModel().getSelectedItem();
                                
                                societetf.setText(String.valueOf(fournisseurs.getSociete()));
                                teltf.setText(String.valueOf(fournisseurs.getNumtel()));
                                 respotf.setText(String.valueOf(fournisseurs.getNomrespo()));
                           
                                
                                

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
     
        
            fournisseur fournisseur = tabfourni.getSelectionModel().getSelectedItem();
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


}
