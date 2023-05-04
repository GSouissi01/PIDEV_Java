/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;


import tn.edu.esprit.utils.Database;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.edu.esprit.entities.TypeContrat;
import tn.edu.esprit.entities.contrat;
import static tn.edu.esprit.utils.MyConnection.MyConnection;

/**
 * FXML Controller class
 *
 * @author islem
 */
public class typecontratController implements Initializable {

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private Button updatebutton;

    @FXML
    private TableColumn<TypeContrat, String> actions;

    contrat selectedcontrat;

    @FXML
    private Label dateDebut;
    @FXML
    private TextField nomtf;
    @FXML
    private ComboBox<contrat> contrattf;
    @FXML
    private TableColumn<TypeContrat, String> coltype;
    @FXML
    private TableColumn<TypeContrat,Integer> colcontrat;
    @FXML
    private TableView<TypeContrat> tabtype;
    @FXML
    private Button promotion;
    @FXML
    private Button reclamation;
    @FXML
    private Button product;
    @FXML
    private Button test;
    @FXML
    private Button contrat;
    @FXML
    private Button typecontrat;
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
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
});
        showTypeContrats();
       
        try {
            test();
        } catch (SQLException ex) {
            Logger.getLogger(typecontratAddController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //showRec();
        //searchRec();
    }

    public ObservableList<TypeContrat> getTypeContratList() {
        ObservableList<TypeContrat> compteList = FXCollections.observableArrayList();
        Connection conn = MyConnection();
        String query = "SELECT * FROM type_contrat";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            TypeContrat TypeContrat;
            

            while (rs.next()) {
                 
                TypeContrat = new TypeContrat(rs.getInt("id"),rs.getInt("contrat_id"), rs.getString("nom"));
                compteList.add(TypeContrat);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        System.out.println(compteList);
        return compteList;
    }

    /*/////////////////////////////////////////////////Controle de saisie/////////////////////////////////////////////////////////////////////*/

    private boolean validateFields() {
        if (nomtf.getText().isEmpty()) {
            showAlert("Error", "image field is required.");
            return false;
        }

        return true;
    }

    public void showTypeContrats() {
        ObservableList<TypeContrat> list = getTypeContratList();
        colcontrat.setCellValueFactory(new PropertyValueFactory<>("contrat_id"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("nom"));
      

        tabtype.setItems(list);
        Callback<TableColumn<TypeContrat, String>, TableCell<TypeContrat, String>> cellFoctory = (TableColumn<TypeContrat, String> param) -> {
            // make cell containing buttons
            final TableCell<TypeContrat, String> cell = new TableCell<TypeContrat, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    TypeContrat TypeContrat = null;
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
                                        TypeContrat TypeContrats;
                                        TypeContrats = tabtype.getSelectionModel().getSelectedItem();
                                        String query = "DELETE FROM `type_contrat` WHERE id =" + TypeContrats.getId();
                                        Connection conn = MyConnection();
                                        ps = conn.prepareStatement(query);
                                        ps.execute();
                                        showTypeContrats();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(typecontratController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else if (result.get() == ButtonType.CANCEL) {
                                    showTypeContrats();
                                }
                            }
                        });

                        editIcon.setOnMouseClicked((new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {

                                TypeContrat TypeContrats = tabtype.getSelectionModel().getSelectedItem();

                                nomtf.setText(String.valueOf(TypeContrats.getNom()));
                                

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
        tabtype.setItems(list);

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
        String nom = nomtf.getText();
        
            
        TypeContrat TypeContrat = tabtype.getSelectionModel().getSelectedItem();
        String query = "UPDATE type_contrat SET  nom = ? , contrat_id = ?  WHERE id = ?";
        
        try {
            PreparedStatement statement = MyConnection().prepareStatement(query);
            statement.setString(1, nom);
            statement.setInt(2, selectedcontrat.getId());
            statement.setInt(3, TypeContrat.getId());
       
            
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful!");
                showTypeContrats();
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
        List<contrat> fournis = retrievefournisseursFromDatabase();
        contrattf.getItems().addAll(fournis);
        contrattf.setPromptText("Select contrat");
// Set the cell factory to display only the name of the reclamations
        contrattf.setCellFactory(c -> new ListCell<contrat>() {
            @Override
            protected void updateItem(contrat fourni, boolean empty) {
                super.updateItem(fourni, empty);
                if (empty || fourni == null) {
                    setText(null);
                } else {
                    setText(fourni.getNomcontrat());
                }
            }
        });

// Set the button cell to display only the name of the reclamations
        contrattf.setButtonCell(new ListCell<contrat>() {
            @Override
            protected void updateItem(contrat cont, boolean empty) {
                super.updateItem(cont, empty);
                if (empty || cont == null) {
                    setText(null);
                } else {
                    setText(cont.getNomcontrat());
                }
            }
        });
        // Add an event listener to the ComboBox to display the selected reclamation
        contrattf.setOnAction(e -> {
            selectedcontrat = contrattf.getSelectionModel().getSelectedItem();
            if (selectedcontrat != null) {
                System.out.println("Selected contrat: " + selectedcontrat.getNomcontrat()+ ", ID: " + selectedcontrat.getId());
            }
        });
    }

    private List<contrat> retrievefournisseursFromDatabase() throws SQLException {
        List<contrat> fournis = new ArrayList<>();
        Connection conn = MyConnection();
        // Create a statement and execute the query
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, nomcontrat FROM contrat");

        // Iterate over the result set and create TypeContrat objects
        while (rs.next()) {
            int id = rs.getInt("id");
            String nomCont = rs.getString("nomcontrat");
            contrat fourni = new contrat(id, nomCont);
            fournis.add(fourni);
        }

        // Close the statement and result set
        rs.close();
        stmt.close();

        return fournis;
    }


     
 @FXML
    public void handleLogOutBtn(ActionEvent event) {
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

    @FXML
    private void promotion(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPromotion.fxml"));
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

    @FXML
    private void reclamation(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BackReclam.fxml"));
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
    private void user(ActionEvent event) {
              try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BackEnd.fxml"));
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
    private void home(ActionEvent event) {
              try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dash.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) home.getScene().getWindow();
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
   private void test1(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("typecontratAdd.fxml"));
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

   

}
