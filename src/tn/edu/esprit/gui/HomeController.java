/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

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
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.entities.Promotion;
import tn.edu.esprit.services.ServiceProduct;
import tn.edu.esprit.services.ServicePromotion;

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
    @FXML
    private Button afficher;
    @FXML
    private ComboBox<String> combo;
    @FXML
    private AnchorPane side_menu;
    @FXML
    private Button menu_btn;
    @FXML
    private Label username;
    @FXML
    private Button product;
    @FXML
    private Button promotion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           Media media = new Media(new File("C:\\Users\\azizb\\Downloads\\sound.mp3").toURI().toString());

    // Ajouter un événement de souris pour jouer le son lors du survol du bouton "menu_btn"
    menu_btn.setOnMouseEntered(e -> {
        // Créer un nouveau MediaPlayer pour chaque événement de souris
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    });
    
    // Ajouter un événement de souris pour jouer le son lors du survol du bouton "promotion"
    promotion.setOnMouseEntered(e -> {
        // Créer un nouveau MediaPlayer pour chaque événement de souris
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    });
    
    // Ajouter un événement de souris pour jouer le son lors du survol du bouton "product"
    product.setOnMouseEntered(e -> {
        // Créer un nouveau MediaPlayer pour chaque événement de souris
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    });
        // TODO
        // Appliquer du CSS inline
TableProduit.setStyle("-fx-font-family: Arial; -fx-selection-bar: lightblue;");

// Charger un fichier CSS externe
TableProduit.getStylesheets().add("file:///C:/Users/azizb/Downloads/tableview.css");


        List<Promotion> promotions = listdata.getPromotion();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (Promotion categoriePromotion : promotions) {
            observableList.add(categoriePromotion.toString());
        }
        combo.setItems(observableList);
        // TODO

        TableProduit.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillTextFields(newSelection);
            }
        });
        TableProduit.setItems(listdata.getProduit());
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
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

        // Load the data into the table
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

        String path = "C:\\xampp\\htdocs\\produit_final\\produit\\public\\images\\product\\" + produit.getImageFile();
         String path1 =  produit.getImageFile();
        File file = new File(path);
        URLImage.setText(path1);
        Image img = new Image(file.toURI().toString());
        Image.setImage(img);

    }

    @FXML
    private void modifier(ActionEvent event) {
        Produit p = TableProduit.getSelectionModel().getSelectedItem();
        TableProduit.getSelectionModel().getSelectedItem();

        p = TableProduit.getSelectionModel().getSelectedItem();
        String date = "" + p.getDateexpiration();
        LocalDate localDate_s = LocalDate.parse(date);

        tfDateExp.setValue(localDate_s);

//start_date.setString(""+promo.getStart_date());
        tfLibelle.setText("" + p.getLibelle());
        tfStock.setText("" + p.getStock());
        tfPrix.setText("" + p.getPrix());
        tfPrixAchat.setText("" + p.getPrixachat());

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

                // Check if the user has selected a row in the table view to update
                p = TableProduit.getSelectionModel().getSelectedItem();
                // If a row is selected, update the existing Produit object with the new input values

                p.setLibelle(libelle);
                p.setStock(stock);
                p.setPrix(prix);
                p.setDateexpiration(sdf.parse(dateStr));
                p.setPrixachat(prixAchat);
                p.setImageFile(URLImage.getText());

                // Call the ServiceProduct to add or update the Produit object in the database
                ServiceProduct sp = new ServiceProduct();

                sp.modifier(p); // update an existing product

                TableProduit.refresh();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Opération effectuée avec succès");
                alert.showAndWait();

                // Reset the input fields and the selectedProduit variable
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
    private void afficher(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduct.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
// assuming you have a reference to the button object
            Stage stage = (Stage) afficher.getScene().getWindow();
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
    private void home(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeprod.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
// assuming you have a reference to the button object
            Stage stage = (Stage) menu_btn.getScene().getWindow();
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
            Stage stage = (Stage) menu_btn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
