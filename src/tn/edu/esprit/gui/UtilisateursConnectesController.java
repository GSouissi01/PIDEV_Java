/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import tn.edu.esprit.entites.User;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


/**
 * FXML Controller class
 *
 * @author SOUISSI
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import javafx.scene.Scene;
import tn.edu.esprit.services.ServiceUser;

public class UtilisateursConnectesController extends ListCell<String> {

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView profileImage;

    private ServiceUser userService;

    public UtilisateursConnectesController(ServiceUser userService) {
        this.userService = userService;
    }
/*
    @Override
    protected void updateItem(String username, boolean empty) {
        super.updateItem(username, empty);
        if (empty || username == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Récupère l'utilisateur correspondant au nom d'utilisateur
            User user = userService.getUserByUsername(username);

            // Affiche le nom d'utilisateur et l'image de profil
            usernameLabel.setText(user.getUsername());
            if (user.getImagePath() != null) {
                profileImage.setImage(new Image(user.getImagePath()));
            }

            // Personnalise la cellule de la liste
            setText(null);
            setGraphic(new VBox(usernameLabel, profileImage));
        }
    }

    public static void showConnectedUsers(ServiceUser userService) {
        // Récupère la liste des utilisateurs dans la base de données
        List<User> allUsers = userService.getAllUsers();

        // Crée une liste observable pour les noms d'utilisateurs
        ObservableList<String> usernames = FXCollections.observableArrayList();
        for (User user : allUsers) {
            usernames.add(user.getEmail());
        }

        // Crée une ListView pour afficher les noms d'utilisateurs
        ListView<String> userListView = new ListView<>();
        userListView.setItems(usernames);

        // Personnalise l'affichage des noms d'utilisateurs dans la liste
        userListView.setCellFactory(param -> new UtilisateursConnectesController(userService));

        // Crée une fenêtre pour la liste d'utilisateurs
        Stage userListStage = new Stage();
        userListStage.setTitle("Liste des utilisateurs connectés");

        // Permet à l'utilisateur de sélectionner un utilisateur avec qui discuter
        userListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String selectedUser = userListView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    // Ouvre une fenêtre de chat avec l'utilisateur sélectionné
                    openChatWindow(selectedUser, userService);
                }
            }
        });

        // Ajoute la ListView à la scène
        Scene userListScene = new Scene(userListView, 200, 400);
        userListStage.setScene(userListScene);
        userListStage.show();
    }

    // Fonction pour ouvrir une fenêtre de chat avec l'utilisateur sélectionné
        private static void openChatWindow(String username, ServiceUser userService) {
    // Récupère l'utilisateur sélectionné à partir de la base de données
    User selectedUser = userService.getUserByEmail(username);
    if (selectedUser == null) {
    // Affiche une alerte si l'utilisateur sélectionné n'est pas trouvé dans la base de données
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Utilisateur non trouvé");
    alert.setHeaderText("Impossible de trouver l'utilisateur sélectionné.");
    alert.setContentText("Veuillez sélectionner un autre utilisateur.");
    alert.showAndWait();
    return;
    }
}


    @FXML
    private void handleLogOutBtn(ActionEvent event) {
    }
}*/
}