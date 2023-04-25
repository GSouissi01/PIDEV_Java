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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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

import com.restfb.*;
import com.restfb.types.*;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;


import tn.edu.esprit.entities.Produit;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PostUpdate;
import facebook4j.auth.AccessToken;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;



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
    @FXML
    private TextField prixmin;
    @FXML
    private TextField prixmax;
    @FXML
    private Button close;
    
    
     

 @Override
public void initialize(URL location, ResourceBundle resources) {
   back.setStyle("-fx-background-color: white; -fx-background-image: url('file:///C:/Users/azizb/Downloads/ret.png'); -fx-background-size: 100% 100%;");

        close.setStyle("-fx-background-image: url('file:///C:/Users/azizb/Downloads/close.png');-fx-background-size: 100% 100%;");

    close.setOnAction(event -> {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
});
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
Label searchLabel = new Label("Recherche:");
Label prixMinLabel = new Label("Prix min:");
Label prixMaxLabel = new Label("Prix max:");

// Création des champs de texte pour les champs de filtre
TextField searchField = new TextField();
TextField prixmin = new TextField();
TextField prixmax = new TextField();

// Création des HBox pour chaque paire d'étiquette/champ de texte
HBox searchBox = new HBox(searchLabel, searchField);
HBox prixMinBox = new HBox(prixMinLabel, prixmin);
HBox prixMaxBox = new HBox(prixMaxLabel, prixmax);

// Définition du VBox contenant les champs de filtre
VBox filterBox = new VBox(searchBox, prixMinBox, prixMaxBox);
filterBox.setSpacing(10);
filterBox.setVisible(false); // Le VBox est caché par défaut

// Définition du bouton pour afficher/cacher le filtre



    
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

    card.setStyle(" -fx-border-color: #000000;"); // Carte avec un fond noir
    card.setOnMouseEntered(event -> {
        card.setStyle("-fx-background-color: #1DB954; -fx-border-color: #000000;");
    });

    // Réinitialiser le style lorsque la souris quitte la carte
    card.setOnMouseExited(event -> {
        card.setStyle(" -fx-border-color: #000000;");
    });
    
    

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
    productName.setFill(javafx.scene.paint.Color.WHITE);
    productName.setFont(cardTitleFont);

    Text productPrice = new Text(String.format("Prix: " +"€%.2f", produit.getPrix()));
    productPrice.setFill(javafx.scene.paint.Color.WHITE);
    productPrice.setFont(cardSubtitleFont);

    Text productDescription = new Text("Stock: " + produit.getStock());
    productDescription.setFill(javafx.scene.paint.Color.WHITE);
    productDescription.setWrappingWidth(175.0);
            
    Text productPromo = new Text(String.format("Promotion: %d%%", produit.getPromotion().getPourcentage()));
    productPromo.setFill(javafx.scene.paint.Color.WHITE);
    productPromo.setWrappingWidth(175.0);
    
    
    VBox cardContent = new VBox();
    cardContent.setSpacing(5.0);
    cardContent.getChildren().addAll(productImage, productName, productPrice, productDescription,productPromo);
    card.getChildren().add(cardContent);
   
if ("active".equals(produit.getPromotion().getStatus())) {
    Button shareButton = new Button();
    shareButton.setStyle("-fx-background-color: white; -fx-background-image: url('file:///C:/Users/azizb/Downloads/fba.png'); -fx-background-size: 100% 100%;");
    shareButton.setOnAction(event -> {
        String appId = "743598390581805";
        String appSecret = "94797061bde2cb22a19d29ab89c47ab2";
        String accessToken = "EAAKkTH3tGi0BAPjuQm6QOsk1AlfykXLjyDk4jTrj0SiUknbJFshasYiZA3uQUFXdW9ZB54ypPQ6m7UKTiDZB4o4jJ33xRRHZBQ1EZBoG0I6FATnuUpyZAWXdxpKFJZAz42wlpIREPm4bIJeLJlwsMFUa2ObK6ZCRoUVI26qOG0mZAkQyGnZBKHsuQ62MWpxciH6B5xZBJlgR1LnQxD0Vszeh5fj";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, appSecret, Version.LATEST);

        // Convert image to byte array
        productImage.setImage(image);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", baos);
        } catch (IOException ex) {
            Logger.getLogger(ListProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] imageData = baos.toByteArray();

        // Upload image to Facebook
        FacebookType response = null;
        response = fbClient.publish("me/photos", FacebookType.class,
                BinaryAttachment.with("product.png", imageData),
                Parameter.with("message", "Check out this product: " + produit.getLibelle() + " for only €" + produit.getPrix()));

        System.out.println("Post ID: " + response.getId());
    });
    cardContent.getChildren().add(shareButton);
}

    card.setOnMouseClicked(event -> {
        try {
            // Generate the QR code for the product
            WritableImage qrCodeImage = generateQRCode(produit);

            // Create an image view to display the QR code
            ImageView qrCodeView = new ImageView(qrCodeImage);

            // Create a new stage to display the QR code as a popup
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(card.getScene().getWindow());
            popup.initStyle(StageStyle.UNDECORATED);

            // Create a layout to hold the QR code image view and the close button
            StackPane layout = new StackPane();
            layout.getChildren().add(qrCodeView);

            // Add the close button to the layout
            Button closeButton = new Button("X");
            closeButton.setOnAction(e -> popup.close());
            closeButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff;");
            closeButton.setAlignment(Pos.TOP_RIGHT);
            closeButton.setTranslateX(90);
            closeButton.setTranslateY(-90);
            layout.getChildren().add(closeButton);

            // Set the scene of the popup to the layout
            popup.setScene(new Scene(layout));
            
            // Show the popup
            popup.showAndWait();
        } catch (WriterException e) {
            e.printStackTrace();
       

        }
    });

        productPane.add(card, col, row);

        col++;
        if (col == 4) {
            col = 0;
            row++;
        }       
    }

    
    // calculer la hauteur préférée de
}

private static final String HMAC_ALGORITHM = "HmacSHA256";

  public static String generate(String appSecret, String accessToken) 
          throws NoSuchAlgorithmException, InvalidKeyException {
    String message = accessToken;
    Mac hmac = Mac.getInstance(HMAC_ALGORITHM);
    SecretKeySpec keySpec = new SecretKeySpec(appSecret.getBytes(), HMAC_ALGORITHM);
    hmac.init(keySpec);
    byte[] hmacResult = hmac.doFinal(message.getBytes());
    return bytesToHexString(hmacResult);
  }

  private static String bytesToHexString(byte[] bytes) {
    StringBuilder builder = new StringBuilder();
    for (byte b : bytes) {
      builder.append(String.format("%02x", b));
    }
    return builder.toString();
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
            .filter(p -> {
                boolean match = true;
                if (!searchField.getText().isEmpty()) {
                    match = p.getLibelle().toLowerCase().contains(searchField.getText().toLowerCase());
                }
                return match;
            })
            .filter(p -> {
                boolean match = true;
                if (!prixmin.getText().isEmpty()) {
                    try {
                        match = p.getPrix() >= Double.parseDouble(prixmin.getText());
                    } catch (NumberFormatException e) {
                        match = false;
                    }
                }
                return match;
            })
            .filter(p -> {
                boolean match = true;
                if (!prixmax.getText().isEmpty()) {
                    try {
                        match = p.getPrix() <= Double.parseDouble(prixmax.getText());
                    } catch (NumberFormatException e) {
                        match = false;
                    }
                }
                return match;
            })
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
        card.setStyle(" -fx-border-color: #000000;"); // Carte avec un fond noir
        card.setOnMouseEntered(event1 -> {
            card.setStyle("-fx-background-color: #1DB954; -fx-border-color: #000000;");
        });

        // Réinitialiser le style lorsque la souris quitte la carte
        card.setOnMouseExited(event1 -> {
            card.setStyle(" -fx-border-color: #000000;");
        });


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
        productName.setFill(javafx.scene.paint.Color.WHITE);
        productName.setFont(cardTitleFont);

        Text productPrice = new Text(String.format("Prix: " +"€%.2f", produit.getPrix()));
        productPrice.setFill(javafx.scene.paint.Color.WHITE);
        productPrice.setFont(cardSubtitleFont);

        Text productDescription = new Text("Stock: " + produit.getStock());
        productDescription.setFill(javafx.scene.paint.Color.WHITE);
        productDescription.setWrappingWidth(175.0);
            
        Text productPromo = new Text(String.format("Promotion: %d%%", produit.getPromotion().getPourcentage()));
        productPromo.setFill(javafx.scene.paint.Color.WHITE);
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




public WritableImage generateQRCode(Produit produit) throws WriterException {
    // Concatenate all the information of the product into a single string
    String productInfo = "libell:"+produit.getLibelle() + "," +
           "Prix:"+ produit.getPrix() + "," +
            "Stock:"+produit.getStock() + "," +
            "Promo:"+produit.getPromotion().getPourcentage() + "," +
            "image:"+produit.getImageFile();

    // Set up the QR code parameters
    Map<EncodeHintType, Object> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    hints.put(EncodeHintType.MARGIN, 1);

    // Generate the QR code
    QRCodeWriter writer = new QRCodeWriter();
    BitMatrix bitMatrix = writer.encode(productInfo, BarcodeFormat.QR_CODE, 200, 200, hints);

    // Convert the bit matrix to a buffered image
    BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < 200; x++) {
        for (int y = 0; y < 200; y++) {
            bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
        }
    }

    // Convert the buffered image to a writable image
    return SwingFXUtils.toFXImage(bufferedImage, null);
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

  
  
  

  

