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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.edu.esprit.entites.Message;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.services.ChatService;
import tn.edu.esprit.services.MessageService;
import tn.edu.esprit.utils.Database;

/**
 * FXML Controller class
 *
 * @author SOUISSI
 */
public class ChatController implements Initializable {

    @FXML
    private Label destinataireLabel;

    @FXML
    private VBox messageArea;

    @FXML
    private TextField messageField;

    private User currentUser;

    private User destinataire;

    private List<Message> messages;

    private Connection cnx;

    public ChatController(Connection cnx) {
        this.cnx = cnx;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurer l'interface utilisateur
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendMessage();
            }
        });
    }

    public void startChat(User destinataire) {
        // Initialiser la conversation
        init(currentUser, destinataire);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void init(User currentUser, User destinataire) {
        this.currentUser = currentUser;
        this.destinataire = destinataire;
        this.destinataireLabel.setText(destinataire.getNom() + " " + destinataire.getPrenom());
        // Charger l'image du destinataire
	// Charger l'image du destinataire à partir d'un fichier sur le disque
Image image = new Image(destinataire.getImagePath());
ImageView imageView = new ImageView(image);
imageView.setFitHeight(50);
imageView.setFitWidth(50);
destinataireLabel.setGraphic(imageView);

        // Afficher les messages précédents
        this.messages = loadMessages();
        displayMessages();
        // Initialiser le service de chat
    }

    private List<Message> loadMessages() {
    // Charge tous les messages de la base de données entre l'utilisateur courant et le destinataire
    List<Message> messages = new ArrayList<>();
    try  {
        String sql = "SELECT * FROM message WHERE (from_id = ? AND to_id = ?) OR (from_id = ? AND to_id = ?) ORDER BY date ASC";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setInt(1, currentUser.getId());
        statement.setInt(2, destinataire.getId());
        statement.setInt(3, destinataire.getId());
        statement.setInt(4, currentUser.getId());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int id = result.getInt("id");
            int fromId = result.getInt("from_id");
            int toId = result.getInt("to_id");
            String content = result.getString("content");
            Timestamp timestamp = result.getTimestamp("date");
            LocalDateTime dateTime = timestamp.toLocalDateTime();
            Message message = new Message(id, fromId, toId, content, dateTime);
            messages.add(message);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return messages;
}

private void displayMessages() {
    // Afficher tous les messages dans la zone de chat
    messageArea.getChildren().clear();
    for (Message message : messages) {
        // Créer un message VBox
        VBox messageBox = new VBox();
        messageBox.setAlignment(Pos.CENTER_LEFT);
        // Créer un label pour le contenu du message
        Label messageContent = new Label(message.getContent());
        // Ajouter une classe CSS pour formater l'apparence du message
        messageContent.getStyleClass().add("message-content");
        // Créer un label pour l'heure d'envoi du message
        Label messageTime = new Label(message.getFormattedTime());
        // Ajouter une classe CSS pour formater l'apparence de l'heure
        messageTime.getStyleClass().add("message-time");
        // Ajouter le contenu et l'heure du message à la boîte de message
        messageBox.getChildren().addAll(messageContent, messageTime);
        // Ajouter la boîte de message à la zone de message
        messageArea.getChildren().add(messageBox);
    }
}

private void sendMessage() {
    // Envoyer le message
    String content = messageField.getText();
    if (!content.isEmpty()) {
        Message message = new Message(currentUser.getId(), destinataire.getId(), content);
        boolean sent = MessageService.sendMessage(message, cnx);
        if (sent) {
            messageField.clear();
            messages.add(message);
            displayMessages();
        }
    }
}
}


