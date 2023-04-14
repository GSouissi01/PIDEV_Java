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
import java.util.ResourceBundle;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
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
    private FlowPane productPane;
      @FXML
    private Button back;
    @FXML
    private AnchorPane side_menu;
    @FXML
    private Label username;
    @FXML
    private Button menu_btn;

  

  

  
@Override
public void initialize(URL location, ResourceBundle resources) {
    
    Font cardTitleFont = new Font("System Bold", 18.0);
    Font cardSubtitleFont = new Font("System", 14.0);
    Font cardBodyFont = new Font("System", 12.0);

    productPane.setHgap(10);
    productPane.setVgap(10);
    productPane.setAlignment(Pos.CENTER);

    int count = 0;
    HBox cardContainer = new HBox();
    cardContainer.setSpacing(10.0);
    cardContainer.setAlignment(Pos.CENTER);
    productPane.getChildren().add(cardContainer);

    for (Produit produit : listdata.getProduit()) {
        if (count % 3 == 0 && count > 0) {
            cardContainer = new HBox();
            cardContainer.setSpacing(10.0);
            cardContainer.setAlignment(Pos.CENTER);
            productPane.getChildren().add(cardContainer);
        }
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
        productDescription.setFill(javafx.scene.paint.Color.BLACK);
        productDescription.setWrappingWidth(175.0);

        VBox cardContent = new VBox();
        cardContent.setSpacing(5.0);
        cardContent.getChildren().addAll(productImage, productName, productPrice, productDescription,productPromo);
        card.getChildren().add(cardContent);

        cardContainer.getChildren().add(card);
        count++;
    }
}

      
    
}