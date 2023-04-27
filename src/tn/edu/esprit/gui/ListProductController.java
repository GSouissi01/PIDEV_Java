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
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import tn.edu.esprit.entities.CartItem;
import tn.edu.esprit.services.ServiceProduct;




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
    private TextField prixmin;
    @FXML
    private TextField prixmax;
    @FXML
    private Button close;
    

    private List<CartItem> cartItems = new ArrayList<>();
    @FXML
    private Button cartButton;
    

   
final double[] totalPrice = {0.0};
Label totalPriceLabel = new Label(String.format("%.2f €", totalPrice[0]));
 
private void updateTotalPriceLabel(double totalPrice, Label totalPriceLabel) {
    totalPriceLabel.setText(String.format("%.2f €", totalPrice));
    totalPriceLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
    totalPriceLabel.setStyle("-fx-font-size: 18;"); // increase font size to 24
}


private void addProductToCart(Produit produit) {
    // Vérifier si le produit est déjà dans le panier
    for (CartItem cartItem : cartItems) {
        if (cartItem.getProduct().equals(produit)) {
            // Le produit est déjà dans le panier, incrémenter la quantité
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            System.out.println("La quantité du produit " + produit.getLibelle() + " a été incrémentée.");
             if ("active".equals(produit.getPromotion().getStatus())) {
            totalPrice[0] += cartItem.getProduct().getPrix()*(cartItem.getProduct().getPromotion().getPourcentage() /100);
             } else {
                  totalPrice[0] += cartItem.getProduct().getPrix();
             }
            updateTotalPriceLabel(totalPrice[0], totalPriceLabel); // mettre à jour le label du montant total
            return;
        }
    
    }
    // Le produit n'est pas dans le panier, ajouter un nouveau CartItem avec une quantité de 1
    if ("active".equals(produit.getPromotion().getStatus())) {
        cartItems.add(new CartItem(produit, 1, produit.getPrix() * (1 - (produit.getPromotion().getPourcentage() / 100))));
        System.out.println(produit.getPromotion().getPourcentage());
        totalPrice[0] +=  produit.getPrix() * (1 - (produit.getPromotion().getPourcentage() / 100));
         updateTotalPriceLabel(totalPrice[0], totalPriceLabel); 
        System.out.println(totalPrice[0]);
    } else {
        cartItems.add(new CartItem(produit, 1, produit.getPrix()));
        totalPrice[0] += produit.getPrix();
         updateTotalPriceLabel(totalPrice[0], totalPriceLabel); 
    }
    System.out.println("Le produit " + produit.getLibelle() + " a été ajouté au panier.");
   // mettre à jour le label du montant total
}



 @Override
public void initialize(URL location, ResourceBundle resources) {
   back.setStyle("-fx-background-color: white; -fx-background-image: url('file:///C:/Users/azizb/Downloads/ret.png'); -fx-background-size: 100% 100%;");

        close.setStyle("-fx-background-image: url('file:///C:/Users/azizb/Downloads/close.png');-fx-background-size: 100% 100%;");
        cartButton.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:///C:/Users/azizb/Downloads/bb.png'); -fx-background-size: 100% 100%;");

    close.setOnAction(event -> {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
});
    
    searchField.getStylesheets().add("file:///C:/Users/azizb/Downloads/field.css");
    prixmin.getStylesheets().add("file:///C:/Users/azizb/Downloads/field.css");
    prixmax.getStylesheets().add("file:///C:/Users/azizb/Downloads/field.css");
    
 


    
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
    
 Button addToCartButton = new Button();
    addToCartButton.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:///C:/Users/azizb/Downloads/addtocart.png'); -fx-background-size: 100% 100%;");

addToCartButton.setOnAction((e) -> {
    addProductToCart(produit);
    updateTotalPriceLabel(totalPrice[0], totalPriceLabel);
    Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Produit ajouté au panier");
alert.setHeaderText(null);
alert.setContentText("Le produit " + produit.getLibelle() + " a été ajouté au panier.");

alert.showAndWait();

    // Ajouter un nouveau CartItem avec une quantité de 1
});
cardContent.getChildren().add(addToCartButton); // Ajouter le bouton à la quatrième colonne de la ligne


HBox buttonBox = new HBox(addToCartButton);
   
if ("active".equals(produit.getPromotion().getStatus())) {
    Button shareButton = new Button();
    shareButton.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:///C:/Users/azizb/Downloads/fb.png'); -fx-background-size: 100% 100%;");
    shareButton.setOnAction(event -> {
        String appId = "743598390581805";
        String appSecret = "94797061bde2cb22a19d29ab89c47ab2";
        String accessToken = "EAAKkTH3tGi0BAO5xTJFAMkhUmI9S5n8xoqWw2RUDqCE2yaMBpbT5PP6YRZBfb0oG7kYbPwlxZA5lH53X4JNY67QQGQE8xnKZAaZAFWDvuUOdjLLjEgYZAtmPv4TNOZBeZAd75plfGLBOBqHfoabUKIchkcAlH36bkWbFbRMIm8QzOiDadjrzH3F";
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
                Parameter.with("message", "Check out this product: " + produit.getLibelle() + " for only " + produit.getPrix()+"DT  "+"With Promotion: "+produit.getPromotion().getPourcentage()+"%"+"          Promotion valable jusqu'au  : "+produit.getDateexpiration()));

        System.out.println("Post ID: " + response.getId());
    });
        buttonBox.getChildren().add(shareButton);
   
}
cardContent.getChildren().add(new Label(" ")); 
cardContent.getChildren().add(buttonBox);





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

 cartButton.setOnAction(event -> {
    Stage cartStage = new Stage();
  GridPane cartGrid = new GridPane();
  
  Text title = new Text("VOTRE PANNIER ");
title.setFont(Font.font("Arial", FontWeight.BOLD, 34));

StackPane.setAlignment(title, Pos.TOP_CENTER);

// Ajouter le titre et le contenu de votre page dans le StackPane
GridPane.setMargin(title, new Insets(-100, 0, 0, 0)); // ajouter une marge de 30 pixels en haut

cartGrid.getChildren().addAll(title);



cartGrid.setHgap(150.0);
cartGrid.setVgap(100.0);
cartGrid.setPadding(new Insets(10.0));
cartGrid.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
// Add separator between rows




// créer les en-têtes de colonnes
Label nameLabelHeader = new Label("Produit");
Label libelle = new Label("Libelle");
Label priceLabelHeader = new Label("Prix unitaire");
Label quantityLabelHeader = new Label("Quantité");
Label actionsLabelHeader = new Label("Actions");

GridPane.setColumnSpan(actionsLabelHeader, 2); // fusionner deux colonnes pour l'en-tête des actions

GridPane.setConstraints(nameLabelHeader, 0, 0);
GridPane.setConstraints(libelle, 1, 0);
GridPane.setConstraints(priceLabelHeader, 2, 0);
GridPane.setConstraints(quantityLabelHeader, 3, 0);
GridPane.setConstraints(actionsLabelHeader, 4, 0);

nameLabelHeader.setStyle("-fx-font-size: 20px");
libelle.setStyle("-fx-font-size: 20px");
priceLabelHeader.setStyle("-fx-font-size: 20px");
quantityLabelHeader.setStyle("-fx-font-size: 20px");
actionsLabelHeader.setStyle("-fx-font-size: 20px");
cartGrid.getChildren().addAll(nameLabelHeader, libelle,priceLabelHeader, quantityLabelHeader, actionsLabelHeader);

int rowIndex = 1;
totalPrice[0] = 0.0;

for (CartItem cartItem : cartItems) {
    Label nameLabel = new Label(cartItem.getProduct().getLibelle());
    Label priceLabel = new Label(String.format("%.2f €", cartItem.getProduct().getPrix()));
    Label quantityLabel = new Label(String.valueOf(cartItem.getQuantity()));
     ImageView productImage = new ImageView();
    productImage.setFitHeight(50);
    productImage.setPreserveRatio(true);
    String imageFile = "C:\\xampp\\htdocs\\produit_final\\produit\\public\\images\\product\\" + cartItem.getProduct().getImageFile();
    Image image = new Image(new File(imageFile).toURI().toString());
    productImage.setImage(image);
    
    Button removeItemButton = new Button("Retirer");
    removeItemButton.setOnAction(e -> {
        cartItems.remove(cartItem);
        // supprimer la ligne correspondante
        totalPrice[0] -= cartItem.getProduct().getPrix() * cartItem.getQuantity();
        Label totalPriceLabel = new Label(String.format("%.2f €", totalPrice[0]));// mettre à jour le montant total
        updateTotalPriceLabel(totalPrice[0], totalPriceLabel); // mettre à jour le label du montant total
    });

Button removeItemButtons = new Button("-");
removeItemButtons.setOnAction(e -> {
    if (cartItem.getQuantity() > 1) {
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        
        quantityLabel.setText(String.valueOf(cartItem.getQuantity()));
 if ("active".equals(cartItem.getProduct().getPromotion().getStatus())) {
      totalPrice[0] -= cartItem.getProduct().getPrix()*cartItem.getProduct().getPromotion().getPourcentage()/100;
    
 }else{
      totalPrice[0] -= cartItem.getProduct().getPrix();
 }

       
        System.out.println(totalPrice[0]);

        updateTotalPriceLabel(totalPrice[0], totalPriceLabel); // mettre à jour le label du montant total
    }
});


    Button addItemButton = new Button("+");
    addItemButton.setOnAction(e -> {
      if (cartItem.getQuantity() > 0) {
        cartItem.setQuantity(cartItem.getQuantity() +1);
        quantityLabel.setText(String.valueOf(cartItem.getQuantity()));


        if ("active".equals(cartItem.getProduct().getPromotion().getStatus())) {
            
      totalPrice[0] += cartItem.getProduct().getPrix()*cartItem.getProduct().getPromotion().getPourcentage()/100;
    
 }else{
      totalPrice[0] += cartItem.getProduct().getPrix();
 }
        System.out.println(totalPrice[0]);

        updateTotalPriceLabel(totalPrice[0], totalPriceLabel); // mettre à jour le label du montant total
    } // mettre à jour le label du montant total
    });
    
    
    
    
    

 productImage.setFitHeight(100); // set image height to 50 pixels
productImage.setFitWidth(100); // set image width to 50 pixels

nameLabel.setFont(new Font(18)); // set font size of nameLabel to 18
priceLabel.setFont(new Font(18)); // set font size of priceLabel to 18
quantityLabel.setFont(new Font(18)); // set font size of quantityLabel to 18

removeItemButton.setFont(new Font(18)); // set font size of removeItemButton to 18
addItemButton.setFont(new Font(18)); // set font size of addItemButton to 18

GridPane.setConstraints(productImage, 0, rowIndex);
GridPane.setConstraints(nameLabel, 1, rowIndex);
GridPane.setConstraints(priceLabel, 2, rowIndex);
GridPane.setConstraints(quantityLabel, 3, rowIndex);
GridPane.setConstraints(removeItemButtons, 4, rowIndex);
GridPane.setConstraints(addItemButton, 5, rowIndex);

    cartGrid.getChildren().addAll(productImage, nameLabel, priceLabel, quantityLabel, removeItemButtons, addItemButton);
    rowIndex++;
    
    totalPrice[0] += cartItem.getProduct().getPrix() * cartItem.getQuantity(); // mettre à jour le montant total
}

Label totalLabel = new Label("Total : ");

totalLabel.setFont(new Font("Arial", 20));


GridPane.setConstraints(totalLabel, 0, rowIndex);
GridPane.setConstraints(totalPriceLabel, 1, rowIndex);
GridPane.setColumnSpan(totalPriceLabel, 4); // fusionner les 4 dernières colonnes pour afficher le montant total
rowIndex++;

cartGrid.getChildren().addAll(totalLabel, totalPriceLabel);

// créer le bouton de validation de la commande
Button validateButton = new Button("Valider la commande");
validateButton.setOnAction(e -> {
    for (CartItem cartItem : cartItems) {
        int quantity = cartItem.getQuantity();
        Produit product = cartItem.getProduct();
        product.setStock(product.getStock() - quantity);
        // update stock in the database
        ServiceProduct service = new ServiceProduct();
        service.stock(product);
    }
    
    // show notification
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Commande validée");
    alert.setHeaderText(null);
    alert.setContentText("Votre commande a été validée avec succès.");
    alert.showAndWait();
    
    
    
});
validateButton.setStyle("-fx-background-color: #1db954; " +
                        "-fx-background-radius: 50; " +
                        "-fx-text-fill: #ffffff; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8px 16px;");
validateButton.setOnMouseEntered(e -> {
    validateButton.setStyle("-fx-background-color: #1ed760; " +
                            "-fx-background-radius: 50; " +
                            "-fx-text-fill: #ffffff; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 8px 16px;");
});
validateButton.setOnMouseExited(e -> {
    validateButton.setStyle("-fx-background-color: #1db954; " +
                            "-fx-background-radius: 50; " +
                            "-fx-text-fill: #ffffff; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 8px 16px;");
});



GridPane.setConstraints(validateButton, 0, rowIndex);
GridPane.setColumnSpan(validateButton, 5);
rowIndex++;

cartGrid.getChildren().add(validateButton);



// créer un conteneur pour afficher le panier et le bouton de validation
VBox cartBox = new VBox(cartGrid);
cartBox.setPadding(new Insets(80.0));
cartBox.setSpacing(60.0);

// afficher le conteneur dans la scène



// Créer un BorderPane pour la décoration de la fenêtre
BorderPane decoration = new BorderPane();

// Créer un bouton pour fermer la fenêtre
Button closeButton = new Button("X");
closeButton.setOnAction(e -> cartStage.close());

// Ajouter le bouton à la partie droite du BorderPane
decoration.setRight(closeButton);

// Ajouter le cartBox à la partie centrale du BorderPane
decoration.setCenter(cartBox);

// Appliquer le style CSS à la décoration de la fenêtre
decoration.setStyle("-fx-background-color: #F5F5DC; " +
                     "-fx-border-color: #4CAF50; " +
                     "-fx-border-width: 2;");

// Créer une nouvelle scène avec la décoration personnalisée
Scene cartScene = new Scene(decoration, 1600, 800);

// Masquer la barre de titre de la fenêtre
cartStage.initStyle(StageStyle.UNDECORATED);

// Afficher la fenêtre
cartStage.setScene(cartScene);
cartStage.show();



});
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
private void handleSearchFieldChanged(ActionEvent event) {
    filterProduits();
}

@FXML
private void handlePrixMinFieldChanged(ActionEvent event) {
    filterProduits();
}

@FXML
private void handlePrixMaxFieldChanged(ActionEvent event) {
    filterProduits();
}

private void filterProduits() {
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

  
  
  

  

