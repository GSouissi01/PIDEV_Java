/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;


import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Produit;

/**
 * FXML Controller class
 *
 * @author azizbramli
 */
public class ListProductController implements Initializable {



    /**
     * Initializes the controller class.
     */
      private ListData listdata = new ListData();

    @FXML
    private GridPane productPane;
      @FXML
    private Button back;
    @FXML
    private AnchorPane side_menu;
    @FXML
    private Label username;
    @FXML
    private Button menu_btn;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;

  

  

 @Override
public void initialize(URL location, ResourceBundle resources) {
       Media media = new Media(new File("C:\\Users\\azizb\\Downloads\\sound.mp3").toURI().toString());

    // Ajouter un événement de souris pour jouer le son lors du survol du bouton "menu_btn"
    menu_btn.setOnMouseEntered(e -> {
        // Créer un nouveau MediaPlayer pour chaque événement de souris
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    });
    
    // Ajouter un événement de souris pour jouer le son lors du survol du bouton "promotion"
   
    
    Font cardTitleFont = new Font("System Bold", 18.0);
    Font cardSubtitleFont = new Font("System", 14.0);
    Font cardBodyFont = new Font("System", 12.0);

    productPane.setHgap(20); // ajouter de l'espace horizontal entre les cartes
    productPane.setVgap(20); // ajouter de l'espace vertical entre les cartes
    productPane.setAlignment(Pos.CENTER);

    int row = 0;
    int col = 0;

    for (Produit produit : listdata.getProduit()) {
          
        StackPane card = new StackPane();
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(200.0, 250.0);
        card.setStyle("-fx-background-color: #FFFFF0; -fx-border-color: #000000;");

        ImageView productImage = new ImageView();
        productImage.setFitHeight(150.0);
        productImage.setFitWidth(150.0);
        productImage.setPickOnBounds(true);
        productImage.setPreserveRatio(true);

        String imageFile = "C:\\xampp\\htdocs\\produit_final\\produit\\public\\images\\product\\" + produit.getImageFile();
        Image image = new Image(new File(imageFile).toURI().toString());
        productImage.setImage(image);

        Text productName = new Text("Libelle: " +produit.getLibelle());
        productName.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        productName.setFill(javafx.scene.paint.Color.BLACK);
        productName.setFont(cardTitleFont);

        Text productPrice = new Text(String.format("Prix: " +"€%.2f", produit.getPrix()));
        productPrice.setFill(javafx.scene.paint.Color.RED);
        productPrice.setFont(cardSubtitleFont);

        Text productDescription = new Text("Stock: " + produit.getStock());
        productDescription.setFill(javafx.scene.paint.Color.BLACK);
        productDescription.setWrappingWidth(175.0);
            
        Text productPromo = new Text(String.format("Promotion: %d%%", produit.getPromotion().getPourcentage()));
        productPromo.setFill(javafx.scene.paint.Color.BLACK);
        productPromo.setWrappingWidth(175.0);

        VBox cardContent = new VBox();
        cardContent.setSpacing(5.0);
        cardContent.getChildren().addAll(productImage, productName, productPrice, productDescription,productPromo);
        card.getChildren().add(cardContent);

        productPane.add(card, col, row);

        col++;
        if (col == 4) {
            col = 0;
            row++;
        }       
    }
    
    // calculer la hauteur préférée de
}

      @FXML
    private void back(ActionEvent event) {
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
// assuming you have a reference to the button object
        Stage stage = (Stage) back.getScene().getWindow();
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
    private void handleSearchFieldAction(KeyEvent event) {
    }

   @FXML
private void handleSearchButtonAction(ActionEvent event) {
    List<Produit> filteredProduits = listdata.getProduit().stream()
        .filter(p -> p.getLibelle().toLowerCase().contains(searchField.getText().toLowerCase()))
        .collect(Collectors.toList());

    productPane.getChildren().clear(); // clear the productPane before adding new products

    Font cardTitleFont = new Font("System Bold", 18.0);
    Font cardSubtitleFont = new Font("System", 14.0);
    Font cardBodyFont = new Font("System", 12.0);

    productPane.setHgap(20); // ajouter de l'espace horizontal entre les cartes
    productPane.setVgap(20); // ajouter de l'espace vertical entre les cartes
    productPane.setAlignment(Pos.CENTER);

    int row = 0;
    int col = 0;

    for (Produit produit : filteredProduits) {
        
             
        StackPane card = new StackPane();
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(200.0, 250.0);
        card.setStyle("-fx-background-color: #FFFFF0; -fx-border-color: #000000;");

        ImageView productImage = new ImageView();
        productImage.setFitHeight(150.0);
        productImage.setFitWidth(150.0);
        productImage.setPickOnBounds(true);
        productImage.setPreserveRatio(true);

        String imageFile = "C:\\xampp\\htdocs\\produit_final\\produit\\public\\images\\product\\" + produit.getImageFile();
        Image image = new Image(new File(imageFile).toURI().toString());
        productImage.setImage(image);

        Text productName = new Text("Libelle: " +produit.getLibelle());
        productName.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        productName.setFill(javafx.scene.paint.Color.BLACK);
        productName.setFont(cardTitleFont);

        Text productPrice = new Text(String.format("Prix: " +"€%.2f", produit.getPrix()));
        productPrice.setFill(javafx.scene.paint.Color.RED);
        productPrice.setFont(cardSubtitleFont);

        Text productDescription = new Text("Stock: " + produit.getStock());
        productDescription.setFill(javafx.scene.paint.Color.BLACK);
        productDescription.setWrappingWidth(175.0);
            
        Text productPromo = new Text(String.format("Promotion: %d%%", produit.getPromotion().getPourcentage()));
        productPromo.setFill(javafx.scene.paint.Color.BLACK);
        productPromo.setWrappingWidth(175.0);

        VBox cardContent = new VBox();
        cardContent.setSpacing(5.0);
        cardContent.getChildren().addAll(productImage, productName, productPrice, productDescription,productPromo);
        card.getChildren().add(cardContent);

        productPane.add(card, col, row);

        col++;
        if (col == 4) {
            col = 0;
            row++;
        }       
    
    }
}

    
}


  

  
  

/*try {
    Produit produit = TableProduit.getSelectionModel().getSelectedItem();
    libelle.setText(produit.getLibelle());
    stock.setText(String.valueOf(produit.getStock()));
    prix.setText(String.valueOf(produit.getPrix()));
    String dateExpirationStr = produit.getDateexpiration().toString(); //assuming it's of type LocalDate
    date.setText(dateExpirationStr);
    prixachat.setText(String.valueOf(produit.getPrixachat()));
    String imagePath = produit.getImageFile();
    Image image = new Image(new ByteArrayInputStream(produit.getImage())); //assuming getImage() returns a byte array
    imgView.setImage(image);
} catch (Exception e) {
    System.out.println(e.getMessage());
}*/

  
  
  

  

