/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.tests;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.entities.Promotion;
import tn.edu.esprit.services.ServiceProduct;
import tn.edu.esprit.services.ServicePromotion;
import tn.edu.esprit.utils.Database;

/**
 *
 * @author ghada
 */
public class MainClass extends Application {
       @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/homeprod.fxml"));
        Parent root = loader.load();
        
         
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("HOME");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         launch(args);
      /*  Produit p=new Produit();
        ServiceProduct sp = new ServiceProduct();
       p= sp.displayById(25);
        System.out.println(p);
*/     // String waa="dhdhd";
        //ServicePromotion pser=new ServicePromotion();
   // Promotion pr=new Promotion();
   //pr=pser.getOneByName(waa);
  // pr=pser.displayById(1);
  // System.out.println(pr);

    }
}
