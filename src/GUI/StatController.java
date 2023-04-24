/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import services.DepenseCRUD;

/**
 * FXML Controller class
 *
 * @author Khalil.Jammezi
 */
public class StatController implements Initializable {

    @FXML
    private PieChart pie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    
     DepenseCRUD dao = new DepenseCRUD();

     // Appelez la méthode calculerTotalDepensesParEtat() avec le nom de l'état souhaité
     float totalDepenses1 = dao.calculerTotalDepensesParEtat("Payé");
        float totalDepenses2 = dao.calculerTotalDepensesParEtat("Non payé");

     // Créez une liste d'objets PieChart.Data pour stocker les données du graphique
     ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
             new PieChart.Data("Payé", totalDepenses1),
             new PieChart.Data("Non payé", totalDepenses2)
            
     );

     // Créez un objet PieChart et ajoutez-lui la liste de données
     PieChart chart = new PieChart(pieChartData);
     chart.setTitle("Total des dépenses par état");

     // Ajoutez le graphique à la vue
     pie.getData().addAll(pieChartData);
 }   
    
}   
