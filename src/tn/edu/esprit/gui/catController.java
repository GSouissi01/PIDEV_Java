/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;



import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;


import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
import tn.edu.esprit.entities.Categorie;
import tn.edu.esprit.entities.Depense;
import static tn.edu.esprit.utils.MyConnection.MyConnection;

/**
 * FXML Controller class
 *
 * @author author
 *
 */
public class catController implements Initializable {

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private Button updatebutton;

    @FXML
    private TableColumn<Categorie, String> actions;

    Depense selectedDepense;
  
    @FXML
    private Button btnbrowse;
    @FXML
    private Label DateFin;
    @FXML
    private TextField nomcat;
    @FXML
    private ComboBox<Depense> nomDep;
    @FXML
    private TextField img;
    @FXML
    private TextField desccat;
    @FXML
    private TextField codecat;
    @FXML
    private Button add;
    @FXML
    private TableColumn<Categorie, String>colnom;
    @FXML
    private TableColumn<Categorie, String> colcode;
    @FXML
    private TableView<Categorie> tabcat;
    @FXML
    private Button export;
    @FXML
    private TextField searchField;
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
    private ListView<?> listdep;
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
        showcat();
       
        try {
            test();
        } catch (SQLException ex) {
            Logger.getLogger(catController.class.getName()).log(Level.SEVERE, null, ex);
        }
        export.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.UPLOAD));
        add.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PLUS));
        updatebutton.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
   
    


        //showRec();
        //searchRec();
    }

    public ObservableList<Categorie> getcatList()  {
        ObservableList<Categorie> compteList = FXCollections.observableArrayList();
        Connection conn = MyConnection();
        String query = "SELECT * FROM categorie";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Categorie categorie;
            

            while (rs.next()) {
                 
                categorie = new Categorie(rs.getInt("id"), rs.getInt("depense_id"), rs.getString("nom"), rs.getString("description"), rs.getInt("code"), rs.getString("image"));
                compteList.add(categorie);
            }
            

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        System.out.println(compteList);
        return compteList;
    }


    /*/////////////////////////////////////////////////Controle de saisie/////////////////////////////////////////////////////////////////////*/

    private boolean validateFields() {
        if (nomcat.getText().isEmpty() || desccat.getText().isEmpty() || codecat.getText().isEmpty() || img.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return false;
        }
        if (nomcat.getText().length() > 20) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("nom must be less than 20 characters");
            alert.showAndWait();
            return false;
        }
        if (desccat.getText().length() > 20) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("description must be less than 20 characters");
            alert.showAndWait();
            return false;
        }

        if (!codecat.getText().matches("[0-9]*")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("code must be number");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void showcat() {
        ObservableList<Categorie> list = getcatList();
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colcode.setCellValueFactory(new PropertyValueFactory<>("code"));

        FilteredList<Categorie> filteredList = new FilteredList<>(list, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(categorie -> {
                System.out.println("oldValue : " + oldValue + " newValue : " + newValue);
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (categorie.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } 
                if (Integer.toString(categorie.getCode()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                

                return false;
            });
        });

        SortedList<Categorie> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tabcat.comparatorProperty());



       
        
        Callback<TableColumn<Categorie, String>, TableCell<Categorie, String>> cellFoctory = (TableColumn<Categorie, String> param) -> {
            // make cell containing buttons
            final TableCell<Categorie, String> cell = new TableCell<Categorie, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    Categorie categorie = null;
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
                                        Categorie categorie;

                                        categorie = tabcat.getSelectionModel().getSelectedItem();
                                        String query = "DELETE FROM `categorie` WHERE id =" + categorie.getId();
                                        Connection conn = MyConnection();
                                        ps = conn.prepareStatement(query);
                                        ps.execute();
                                        showcat();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(catController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else if (result.get() == ButtonType.CANCEL) {
                                    showcat();
                                }
                            }
                        });

                        editIcon.setOnMouseClicked((new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {

                                Categorie categorie = tabcat.getSelectionModel().getSelectedItem();
                                nomcat.setText(categorie.getNom());
                                desccat.setText(categorie.getDescription());
                                codecat.setText(String.valueOf(categorie.getCode()));
                                img.setText(categorie.getImage());
                                 

                             
                                

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
        tabcat.setItems(sortedList);


    }

    private void showAlert(String title, String fournisseur) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(fournisseur);
        alert.showAndWait();
    }

private void onupdate(ActionEvent event) {
    if (event.getSource() == updatebutton) {
        String nom = nomcat.getText();
        String description = desccat.getText();
        int code = Integer.parseInt(codecat.getText());
        String image = img.getText();
        Categorie categorie = tabcat.getSelectionModel().getSelectedItem();
        String query = "UPDATE categorie SET nom = ? , description = ?, code = ?, image = ? WHERE id = ?"; 
            
        
        try {
            PreparedStatement statement = MyConnection().prepareStatement(query);
            statement.setInt(5, categorie.getId());
            statement.setString(1, nom);
            statement.setString(2, description);
            statement.setInt(3, code);
            statement.setString(4, image);

                

            
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("Information Dialog");
               alert.setHeaderText(null);
                alert.setContentText("Categorie updated successfully!");
                alert.showAndWait();
        
                showcat();
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

        List<Depense> depenses = retrievedepenseFromDatabase();
        nomDep.getItems().addAll(depenses);
        nomDep.setPromptText("Select a depense");
// Set the cell factory to display only the name of the reclamations
        nomDep.setCellFactory(c -> new ListCell<Depense>() {
            @Override
            protected void updateItem(Depense dep, boolean empty) {
                super.updateItem(dep, empty);
                if (empty || dep == null) {
                    setText(null);
                } else {
                    setText(dep.getdescription());
                }
            }
        });

// Set the button cell to display only the name of the reclamations
        nomDep.setButtonCell(new ListCell<Depense>() {
            @Override
            protected void updateItem(Depense dep, boolean empty) {
                super.updateItem(dep, empty);
                if (empty || dep == null) {
                    setText(null);
                } else {
                    setText(dep.getdescription());
                }
            }
        });
        // Add an event listener to the ComboBox to display the selected reclamation
        nomDep.setOnAction(e -> {
            selectedDepense = nomDep.getSelectionModel().getSelectedItem();
            if (selectedDepense != null) {
                System.out.println("Selected depense: " + selectedDepense.getdescription());
            }
        });
    }

    private List<Depense> retrievedepenseFromDatabase() throws SQLException {
        List<Depense> dep = new ArrayList<>();
        Connection conn = MyConnection();
        // Create a statement and execute the query
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, description FROM depense");

        // Iterate over the result set and create fournisseur objects
        while (rs.next()) {
            int id = rs.getInt("id");
            String description = rs.getString("description");
            Depense depense = new Depense(id, description);
            dep.add(depense);
        }

        // Close the statement and result set
        rs.close();
        stmt.close();

        return dep;
    }


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
            img.setText(file.getName());
            // set fit height and width of ImageView to the image size
            
            // set the image view in your UI, e.g.

        }
    }

    private void add(ActionEvent event) {
        
        String nom = nomcat.getText();
        String description = desccat.getText();
        int code = Integer.parseInt(codecat.getText());
        String image = img.getText();
        int depense = selectedDepense.getId();
        String query = "INSERT INTO categorie (nom,description,code,image,depense_id) VALUES ('" + nom + "','" + description + "','" + code + "','" + image + "','" + depense + "')";
        executeQuery(query);
        //alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Categorie ajoutée avec succés");
        alert.showAndWait();
        showcat();
    }

    private void export(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to CSV");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                FileWriter fileWriter = new FileWriter(selectedFile);
                ObservableList<Categorie> categories = tabcat.getItems();
                fileWriter.append("Nom, Description, Code\n");
                for (Categorie category : categories) {
                    fileWriter.append(category.getNom());
                    fileWriter.append(",");
                    fileWriter.append(category.getDescription());
                    fileWriter.append(",");
                    fileWriter.append(String.valueOf(category.getCode()));
                    fileWriter.append("\n");
                }
                fileWriter.close();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Export Successful");
                alert.setHeaderText(null);
                alert.setContentText("Data exported to " + selectedFile.getAbsolutePath());
                alert.showAndWait();
                //open file
                Desktop.getDesktop().open(selectedFile);
                    
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Export Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while exporting the data");
                alert.showAndWait();
            }
        }
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
        Stage stage = (Stage) promotion.getScene().getWindow();
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
}
