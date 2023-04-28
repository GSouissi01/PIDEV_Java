package tn.edu.esprit.tests;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author SOUISSI
 */
public class ClientApp extends Application{
    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("Starting client...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/Client.fxml"));
        Parent root = loader.load();
        
         
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Client");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
}
