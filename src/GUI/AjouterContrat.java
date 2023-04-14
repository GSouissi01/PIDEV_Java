/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import Entities.Contrat;
import Services.ServiceContrat;
/**
 *
 * @author wissa
 */
public class AjouterContrat implements Initializable{
       @FXML
    private TextField tfdatesignature  ;
    @FXML
    private TextField tfdateexpiration ;
    @FXML
    private TextField ttfmontant  ;
    @FXML
    private TextField tfimagecontrat ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ajouterPersonne(ActionEvent event) throws IOException {
        if (tfdatesignature.getText().isEmpty() || tfdateexpiration.getText().isEmpty() || ttfmontant.getText().isEmpty()|| tfimagecontrat.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Nom ou prenom invalide(s)", ButtonType.OK);
            a.showAndWait();
        } else {
            ServiceContrat sp = new ServiceContrat();
            Contrat p = new Contrat(tfdatesignature.getText(), tfdateexpiration.getText(),ttfmontant.getText(),tfimagecontrat.getText());
            sp.add(p);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Personne added !", ButtonType.OK);
            a.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherPersonne.fxml"));
            Parent root = loader.load();
            tfdatesignature.getScene().setRoot(root);
            ContratBackController apc = loader.getController();
            apc.setDatesignature(tfdatesignature.getText());
            apc.setDateexpiration(tfdateexpiration.getText());
            apc.setMontant(ttfmontant.getText());
            apc.setImagecontrat(tfimagecontrat.getText());
        }

    }

}
