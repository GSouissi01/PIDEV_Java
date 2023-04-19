/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.tests;

/**
 *
 * @author SOUISSI
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.gui.ChatController;
import tn.edu.esprit.utils.Database;

public class ChatApp extends Application {

   Connection cnx = Database.getInstance().getCnx();

    @Override
    public void start(Stage stage) {
        // Connexion à la base de données
        

        // Création de l'interface utilisateur
        VBox root = new VBox();
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Chat avec Alice");
        stage.show();

        // Initialisation du contrôleur de chat
        ChatController chatController = new ChatController(cnx);
        User alice = new User(1, "Alice", "Dupont");
        User bob = new User(2, "Bob", "Martin");
        chatController.setCurrentUser(bob);
        chatController.startChat(alice);

        // Ajout de la zone de chat à l'interface utilisateur
        root.getChildren().add(chatController.getMessageArea());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        // Fermeture de la connexion à la base de données
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
