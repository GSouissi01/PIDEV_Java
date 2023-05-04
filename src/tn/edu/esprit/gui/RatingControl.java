/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

/**
 *
 * @author azizbramli
 */
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class RatingControl extends HBox {

    private int rating;
    private ImageView[] stars;

    public RatingControl(int maxRating) {
        this.rating = 0;
        this.stars = new ImageView[maxRating];
        
        // Créer une image d'étoile pour les étoiles pleines
        Image starFull = new Image(getClass().getResourceAsStream("star_full.png"));
        
        // Créer une image d'étoile pour les étoiles vides
        Image starEmpty = new Image(getClass().getResourceAsStream("star_empty.png"));

        // Initialiser les étoiles avec des images d'étoiles vides
        for (int i = 0; i < maxRating; i++) {
            ImageView star = new ImageView(starEmpty);
            star.setFitWidth(25);
            star.setFitHeight(25);
            star.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> setRating(getChildren().indexOf(event.getSource()) + 1));
            stars[i] = star;
            getChildren().add(star);
        }
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;

        // Mettre à jour l'affichage des étoiles en fonction du nombre d'étoiles sélectionnées
        for (int i = 0; i < stars.length; i++) {
            if (i < rating) {
                stars[i].setImage(new Image(getClass().getResourceAsStream("star_full.png")));
            } else {
                stars[i].setImage(new Image(getClass().getResourceAsStream("star_empty.png")));
            }
        }
    }
}
