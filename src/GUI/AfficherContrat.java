/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
/**
 *
 * @author wissa
 */
public class AfficherContrat  implements Initializable {
      @FXML
    private Label lbdatesignature  ;
    @FXML
    private Label lbdateexpiration ;
    @FXML
    private Label lbmontant  ;
    @FXML
    private Label lbimagecontrat ;
    /**
     * Initializes the controller class.
     */
    
      @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setDatesignature(String datesignature){
        lbdatesignature.setText(datesignature);
    }
    
    public void setDateexpiration(String dateexpiration){
        lbdateexpiration.setText(dateexpiration);
    }
    public void setMontant(String montant){
        lbmontant.setText(montant);
    }
    public void setImagecontrat(String imagecontrat){
        lbimagecontrat.setText(imagecontrat);
    }
}
