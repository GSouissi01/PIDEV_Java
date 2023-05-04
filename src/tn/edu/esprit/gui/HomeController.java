/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.entities.Promotion;
import tn.edu.esprit.services.ServiceProduct;
import tn.edu.esprit.services.ServicePromotion;
import javafx.embed.swing.SwingFXUtils;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Popup;
import javafx.util.Callback;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 * FXML Controller class
 *
 * @author azizbramli
 */
public class HomeController implements Initializable {

    @FXML
    private TextField tfLibelle;
    @FXML
    private TextField tfStock;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfPrixAchat;
    @FXML
    private Button tfAdd;
    @FXML
    private DatePicker tfDateExp;
    @FXML
    private Button btnAddImage;
    @FXML
    private ImageView Image;
    @FXML
    private TextField URLImage;
    @FXML
    private TableView<Produit> TableProduit;
    @FXML
    private TableColumn<Produit, String> libelle;
    @FXML
    private TableColumn<Produit, Integer> stock;
    @FXML
    private TableColumn<Produit, Float> prix;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TableColumn<Produit, Float> prixachat;
    @FXML
    private TableColumn<Produit, ImageView> img;
    
    
    private ListData listdata = new ListData();
    private Button afficher;
    @FXML
    private ComboBox<String> combo;
    
    @FXML
    private Button product;
    @FXML
    private Button promotion;
    @FXML
    private Button stat;
    @FXML
    private Button pdf;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button test;
    @FXML
    private Button close;
    @FXML
    private Button user;
    @FXML
    private Button reclamation;
    @FXML
    private Button contrat;
    @FXML
    private Button typecontrat;
    @FXML
    private Button fournisseur;
    @FXML
    private Button publicite;
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
    

pdf.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:///C:/Users/azizb/Downloads/pdf.png'); -fx-background-size: 100% 100%;");


stat.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:///C:/Users/azizb/Downloads/stat1.png'); -fx-background-size: 100% 100%;");
test.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:///C:/Users/azizb/Downloads/add.png'); -fx-background-size: 100% 100%;");
   close.setStyle("-fx-background-image: url('file:///C:/Users/azizb/Downloads/close.png');-fx-background-size: 100% 100%;");

    close.setOnAction(event -> {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
});





    
    
    try {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateProduct.fxml"));
    Parent root = loader.load();
    UpdateProductController updateController = loader.getController();

    tfLibelle.setText(updateController.tflibelleUpdate.getText());
    tfStock.setText(updateController.tfStockUpdate.getText());
    tfPrix.setText(updateController.tfPrixUpdate.getText());
    tfPrixAchat.setText(updateController.tfPrixAchatUpdate.getText());
    tfDateExp.setValue(updateController.tfDateExpUpdate.getValue());
    Image.setImage(updateController.ImageUpdate.getImage());
} catch (IOException e) {
    e.printStackTrace();
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    Media media = new Media(new File("C:\\Users\\azizb\\Downloads\\sound.mp3").toURI().toString());

    // Add a mouse event to play the sound when hovering over the "menu_btn" button
  

    // Add a mouse event to play the sound when hovering over the "promotion" button
    promotion.setOnMouseEntered(e -> {
        // Create a new MediaPlayer for each mouse event
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    });

    // Add a mouse event to play the sound when hovering over the "product" button
    product.setOnMouseEntered(e -> {
        // Create a new MediaPlayer for each mouse event
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    });

    // Apply inline CSS
    TableProduit.setStyle("-fx-font-family: Arial; -fx-selection-bar: lightblue;");

    // Load an external CSS file
    TableProduit.getStylesheets().add("file:///C:/Users/azizb/Downloads/tableview.css");

    List<Promotion> promotions = listdata.getPromotion();
    ObservableList<String> observableList = FXCollections.observableArrayList();
    for (Promotion categoriePromotion : promotions) {
        observableList.add(categoriePromotion.toString());
    }
    combo.setItems(observableList);

    TableProduit.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            fillTextFields(newSelection);
        }
    });
    TableProduit.setItems(listdata.getProduit());
    libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
    stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    stock.setCellFactory(column -> new TableCell<Produit, Integer>() {
        @Override
        protected void updateItem(Integer item, boolean empty) {
            //super.updateItem(item, empty);
            if (item == null || empty) {
              
            } else {
                setText(item.toString());
                if (item < 10) {
                    
                    
                   
                    try {
                        displayNotification("Low Stock", "The stock for " + ((Produit) getTableRow().getItem()).getLibelle() + " is running low ("+((Produit) getTableRow().getItem()).getStock()+"Kg)" );
                    } catch (IOException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                   
                   
                }
            }
        }
    });
    prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
    date.setCellValueFactory(new PropertyValueFactory<>("dateexpiration"));
    prixachat.setCellValueFactory(new PropertyValueFactory<>("prixachat"));
 

    img.setCellValueFactory(cellData -> {
        String imageFile = "C:\\xampp\\htdocs\\produit_final\\produit\\public\\images\\product\\" + cellData.getValue().getImageFile();
        Image image = new Image(new File(imageFile).toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return new SimpleObjectProperty<>(imageView);
    });
    
    TableColumn<Produit, Void> modifierColonne = new TableColumn<>("Modifier");


// Créez une cellule de bouton pour la colonne Modifier
Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>> celluleModifierFactory = new Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>>() {
    @Override
    public TableCell<Produit, Void> call(final TableColumn<Produit, Void> param) {
        final TableCell<Produit, Void> cellule = new TableCell<Produit, Void>() {
        private final Button bouton = new Button();

{
bouton.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:///C:/Users/azizb/Downloads/edit.png'); -fx-background-size: 100% 100%;");

    bouton.setOnAction((ActionEvent event) -> {
        Produit produit = getTableView().getItems().get(getIndex());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateProduct.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateProductController controller = loader.getController();
        controller.setProduit(produit);

        Popup popup = new Popup();
        popup.getContent().add(root);

        // Add a close button with an "X" symbol
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10px;");
        closeButton.setOnAction(closeEvent -> {
            if (popup != null) {
                popup.hide();
            }
        });
        closeButton.setOnMouseClicked(closeEvent -> popup.hide());
        ((AnchorPane) root).getChildren().add(closeButton);

        popup.show(bouton.getScene().getWindow());
    });
}


            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(bouton);
                }
            }
        };
        return cellule;
    }
};


// Créez une cellule de bouton pour la colonne Supprimer
final TableColumn<Produit, Void> actionsColumn = new TableColumn<>("Supprimer");
Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>> cellFactory = new Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>>() {
    @Override
    public TableCell<Produit, Void> call(final TableColumn<Produit, Void> param) {
        final TableCell<Produit, Void> cell = new TableCell<Produit, Void>() {
            private final Button supprimerButton = new Button();

            {
supprimerButton.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:///C:/Users/azizb/Downloads/remove.png'); -fx-background-size: 100% 100%;");

                supprimerButton.setOnAction((ActionEvent event) -> {
                    Produit produit = getTableView().getItems().get(getIndex());
                    ServiceProduct p = new ServiceProduct();
                    p.supprimer(produit.getId());
                    getTableView().getItems().remove(produit);
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(supprimerButton);
                }
            }
        };
        return cell;
    }
};
actionsColumn.setCellFactory(cellFactory);
TableProduit.getColumns().add(actionsColumn);


// Associez les cellules de bouton aux colonnes correspondantes
modifierColonne.setCellFactory(celluleModifierFactory);


// Ajoutez les colonnes Modifier et Supprimer à votre TableView
TableProduit.getColumns().addAll(modifierColonne);


    // Load the data into the table
}

private void displayNotification(String title, String message) throws IOException {
    if (!SystemTray.isSupported()) {
        return;
    }

    SystemTray tray = SystemTray.getSystemTray();
    BufferedImage bufferedImage = ImageIO.read(new File("C:/Users/azizb/Downloads/tick1.png"));
   
JLabel label = new JLabel(new ImageIcon(bufferedImage));

    TrayIcon trayIcon = new TrayIcon(bufferedImage, "Product Manager");

    trayIcon.setImageAutoSize(true);
    trayIcon.setToolTip("Product Manager");

    try {
        tray.add(trayIcon);
        trayIcon.displayMessage(title, message, TrayIcon.MessageType.WARNING);
    } catch (AWTException e) {
        e.printStackTrace();
    }
}


    
    
    @FXML
    private void AddImage(ActionEvent event) throws FileNotFoundException, IOException {
        Random rand = new Random();
        int x = rand.nextInt(1000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        String DBPath = "" + x + ".jpg";

        if (file != null) {
            FileInputStream Fsource = new FileInputStream(file.getAbsolutePath());
            FileOutputStream Fdestination = new FileOutputStream(DBPath);
            BufferedInputStream bin = new BufferedInputStream(Fsource);
            BufferedOutputStream bou = new BufferedOutputStream(Fdestination);
            System.out.println(file.getAbsoluteFile());
            String path = file.getName();
            String res;
            int len;
            len = path.length();
            res = path;
            //   res = path.substring(47,len);
            //    System.out.println(res);
            Image img = new Image(file.toURI().toString());
            Image.setImage(img);
            URLImage.setText(res);
            int b = 0;
            while (b != -1) {
                b = bin.read();
                bou.write(b);
            }
            bin.close();
            bou.close();

        } else {
            System.out.println("error");
        }
    }

    @FXML
    private void saveUser(ActionEvent event) throws IOException {
        
 


        String promo = (String) combo.getValue();
        ServicePromotion pser = new ServicePromotion();
        Promotion pr = new Promotion();
        pr = pser.getOneByName(promo);
        System.out.println(promo);

        String libelle = tfLibelle.getText();
        String stockString = tfStock.getText();
        int stock = 0;
        if (!stockString.isEmpty()) {
            stock = Integer.parseInt(stockString);
        }
        String prixString = tfPrix.getText();
        float prix = 0;
        if (!prixString.isEmpty()) {
            prix = Float.parseFloat(prixString);
        }

        String prixAchatString = tfPrixAchat.getText();
        float prixAchat = 0;
        if (!prixAchatString.isEmpty()) {
            prixAchat = Float.parseFloat(prixAchatString);
        }

        Alert alert;
        if (libelle.isEmpty() || stockString.isEmpty() || prixString.isEmpty() || Objects.isNull(tfDateExp) || prixAchatString.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        } else if (!libelle.matches("^[a-zA-Z]+$")) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Le libellé ne doit contenir que des lettres");
            alert.showAndWait();
        } else if (stock < 0 || prix < 0 || prixAchat < 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Les valeurs de stock, prix et prix d'achat doivent être positives");
            alert.showAndWait();
        } else if (tfDateExp.getValue().isBefore(LocalDate.now())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("La date d'expiration doit être supérieure à aujourd'hui");
            alert.showAndWait();
        } else {
            try {
                // Change the format of the date returned by the DatePicker to match the format expected by the database
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(java.sql.Date.valueOf(tfDateExp.getValue()));

                // Create a Produit object with the input values
                Produit p = new Produit(libelle, stock, prix, sdf.parse(dateStr), pr, prixAchat, URLImage.getText());
                // Call the ServiceProduct to add the Produit object to the database
                ServiceProduct sp = new ServiceProduct();
                sp.ajouter(p);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Ajout avec succés");
                alert.showAndWait();
                TableProduit.refresh();
            } catch (NumberFormatException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir des nombres valides");
                alert.showAndWait();
            } catch (ParseException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir une date valide");
                alert.showAndWait();
            } catch (Exception e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de l'ajout du produit");
                alert.showAndWait();
            }
        }
    }

    private void Liste_produit(ActionEvent event) {

        try {

            Produit produit = TableProduit.getSelectionModel().getSelectedItem();
              

            String path = "C:\\xampp\\htdocs\\produit_final\\produit\\public\\images\\product\\" + produit.getImageFile();
            File file = new File(path);
            URLImage.setText(path);
            Image img = new Image(file.toURI().toString());
            Image.setImage(img);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    public void supprimer() {
        ServiceProduct p = new ServiceProduct();
        p.supprimer(TableProduit.getSelectionModel().getSelectedItem().getId());
        System.out.println(TableProduit.getSelectionModel().getSelectedItem().getId());
        Produit pr = new Produit();
        pr = TableProduit.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void delete(ActionEvent event) {
        supprimer();
        TableProduit.getItems().removeAll(TableProduit.getSelectionModel().getSelectedItem());

        TableProduit.refresh();

    }

    private void fillTextFields(Produit p) {
        String date = "" + p.getDateexpiration();
        LocalDate localDate_s = LocalDate.parse(date);

        tfDateExp.setValue(localDate_s);
        tfLibelle.setText(p.getLibelle());
        tfStock.setText(Integer.toString(p.getStock()));
        tfPrix.setText(Float.toString(p.getPrix()));
        tfPrixAchat.setText(Float.toString(p.getPrixachat()));
        URLImage.setText(p.getImageFile());

        Produit produit = TableProduit.getSelectionModel().getSelectedItem();
        combo.getSelectionModel().select(produit.getPromotion().getTitre());
        String path = "C:\\xampp\\htdocs\\produit_final\\produit\\public\\images\\product\\" + produit.getImageFile();
         String path1 =  produit.getImageFile();
        File file = new File(path);
        URLImage.setText(path1);
        Image img = new Image(file.toURI().toString());
        Image.setImage(img);

    }

  @FXML
private void modifier(ActionEvent event) {
    String promo = (String) combo.getValue();
    ServicePromotion pser = new ServicePromotion();
    Promotion pr = pser.getOneByName(promo);

    Produit p = TableProduit.getSelectionModel().getSelectedItem();
    String date = "" + p.getDateexpiration();
    LocalDate localDate_s = LocalDate.parse(date);

    tfDateExp.setValue(localDate_s);
    tfLibelle.setText(p.getLibelle());
    tfStock.setText("" + p.getStock());
    tfPrix.setText("" + p.getPrix());
    tfPrixAchat.setText("" + p.getPrixachat());
    URLImage.setText(p.getImageFile());

    Alert alert;
    String libelle = tfLibelle.getText();
    String stockString = tfStock.getText();
    String prixString = tfPrix.getText();
    String prixAchatString = tfPrixAchat.getText();

    if (libelle.isEmpty() || stockString.isEmpty() || prixString.isEmpty() || Objects.isNull(tfDateExp.getValue()) || prixAchatString.isEmpty()) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs");
        alert.showAndWait();
    } else if (!libelle.matches("^[a-zA-Z]+$")) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Le libellé ne doit contenir que des lettres");
        alert.showAndWait();
    } else {
        try {
            int stock = Integer.parseInt(stockString);
            float prix = Float.parseFloat(prixString);
            float prixAchat = Float.parseFloat(prixAchatString);

            if (stock < 0 || prix < 0 || prixAchat < 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Les valeurs de stock, prix et prix d'achat doivent être positives");
                alert.showAndWait();
            } else if (tfDateExp.getValue().isBefore(LocalDate.now())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("La date d'expiration doit être supérieure à aujourd'hui");
                alert.showAndWait();
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(java.sql.Date.valueOf(tfDateExp.getValue()));
                p.setLibelle(libelle);
                p.setStock(stock);
                p.setPrix(prix);
                p.setDateexpiration(sdf.parse(dateStr));
                p.setPromotion(pr);
                p.setPrixachat(prixAchat);
                p.setImageFile(URLImage.getText());

                ServiceProduct sp = new ServiceProduct();
                sp.modifier(p);

                TableProduit.refresh();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Opération effectuée avec succès");
                alert.showAndWait();

                tfLibelle.clear();
                tfStock.clear();
                tfPrix.clear();
                tfPrixAchat.clear();
                tfDateExp.setValue(null);
                URLImage.clear();
            }
            } catch (NumberFormatException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir des nombres valides");
                alert.showAndWait();
            } catch (ParseException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir une date valide");
                alert.showAndWait();
            } catch (Exception e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);

            }

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
    private void stat(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PieChart.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
// assuming you have a reference to the button object
            Stage stage = (Stage) stat.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

@FXML
private void pdf(ActionEvent event) {
    List<Produit> produits = TableProduit.getItems();
    PDFGenerator pd = new PDFGenerator();
    try {
        pd.generatePdf("liste_produits", produits);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PDF");
        alert.setHeaderText(null);
        alert.setContentText("!!!PDF exported!!!");
        alert.showAndWait();
        System.out.println("impression done");
    } catch (Exception ex) {
        Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText("!!!No product selected!!!");
        alert.showAndWait();
    }
}

  @FXML
private void test(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
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

}


                


