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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChatClient extends Application {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private TextArea messageArea = new TextArea();
    private TextField messageField = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        // Set up the message area
        messageArea.setEditable(false);
        root.setCenter(messageArea);

        // Set up the message field
        messageField.setOnAction(event -> {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                out.println(message);
                messageField.clear();
            }
        });
        root.setBottom(messageField);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Connect to the server
        try {
            socket = new Socket("localhost", 8000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Read messages from the server and update the message area
            final BufferedReader finalIn = in;
            new Thread(() -> {
                try {
                    String line;
                    while ((line = finalIn.readLine()) != null) {
                        final String message = line; // separate variable to store the value of line
                        Platform.runLater(() -> messageArea.appendText(message + "\n"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }

    @Override
    public void stop() throws Exception {
        // Close the socket and input/output streams
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
