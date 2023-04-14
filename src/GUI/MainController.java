/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Khalil.Jammezi
 */
public class MainController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private VBox ap;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void page1(MouseEvent event) {
    bp.setCenter(ap);

    }

    @FXML
    private void page2(MouseEvent event) {
     LoadPage("Page2");
    }

    @FXML
    private void page3(MouseEvent event) {
    LoadPage("Page3");
    
    }
    private void LoadPage(String page){
       Parent root = null;
         try {
              root = FXMLLoader.load(getClass().getResource(page+".fxml"));
         } catch (IOException ex) {
              Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
         }
         bp.setCenter(root);
    }
    
}
