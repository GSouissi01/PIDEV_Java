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
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.beans.value.ChangeListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import org.json.JSONException;
import org.json.JSONObject;


public class FacebookAuth extends Application {

        private static final String APP_ID = "1373179750172872";
    private static final String REDIRECT_URI = "http://localhost/";

    private WebView webView;

    private TextField appIdField;
    private TextField accessTokenField;

    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        appIdField = new TextField(APP_ID);
        gridPane.add(appIdField, 0, 0);

        Button authButton = new Button("Authenticate");
        authButton.setOnAction(event -> authenticate());
        gridPane.add(authButton, 1, 0);

        webView = new WebView();
        webView.setPrefSize(800, 600);
        WebEngine webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                String url = webEngine.getLocation();
                if (url.startsWith(REDIRECT_URI)) {
                    String[] parts = url.split("#access_token=");
                    if (parts.length == 2) {
                        String accessToken = parts[1].split("&")[0];
                        accessTokenField.setText(accessToken);
                    }
                    stage.close();
                }
            }
        });

        accessTokenField = new TextField();
        accessTokenField.setEditable(false);
        gridPane.add(accessTokenField, 0, 1, 2, 1);

        gridPane.add(webView, 0, 2, 2, 1);

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }

    private void authenticate() {
        String appId = appIdField.getText();
        String authUrl = "https://www.facebook.com/dialog/oauth?client_id="
                + appId
                + "&redirect_uri="
                + REDIRECT_URI
                + "&response_type=token";
        webView.getEngine().load(authUrl);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

