/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Categorie;
import Services.ServiceCategorie;
import com.jfoenix.controls.JFXTextField;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * FXML Controller class
 *
 * @author Safe
 */
public class ContratCategoryBackController implements Initializable {

    @FXML
    private TableView<Categorie> table;
    @FXML
    private TableColumn<Categorie, Number> col_id;
    @FXML
    private TableColumn<Categorie, String> col_name;
    @FXML
    private JFXTextField txt_name;
    @FXML
    private JFXTextField txt_id;
    @FXML
    private JFXTextField txt_search;

    /**
     * Initializes the controller class.
     */
    public void updateTable(){
        Services.ServiceCategorie sm = new ServiceCategorie();
        
        List<Categorie> categories = sm.afficher();
        ObservableList<Categorie> listM=FXCollections.observableArrayList(categories);
       col_id.setCellValueFactory(new PropertyValueFactory<Categorie,Number>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Categorie,String>("name"));
        
        
        table.setItems(listM);
        FilteredList<Categorie> filteredData= new FilteredList<>(listM,b->true);
     
       
       ObservableList<Categorie> newdata = listM.stream().filter(n -> n.getName().toLowerCase().contains(txt_search.getText().toLowerCase())
       )
       .collect(Collectors.toCollection(FXCollections::observableArrayList));
      table.setItems(newdata);
    }
    ServiceCategorie sc = new ServiceCategorie();
    @FXML
    private void addCategory(){
        String name = txt_name.getText();
        Categorie c = new Categorie(0, name);
        
        sc.add(c);
         UIManager.put("OptionPane.minimumSize",new Dimension(500,200)); 
            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
          "Arial", Font.BOLD, 30)));
            JOptionPane.showMessageDialog(null, "Categorie ajoutéz avec succés", "Succès!", JOptionPane.INFORMATION_MESSAGE);
            txt_name.clear();
            updateTable();
            
    }
    int index;
    @FXML
    public void getSelected(){
        index = table.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_name.setText(col_name.getCellData(index).toString());
        
            
    }
    @FXML
    private void editCategory(){
        int id=Integer.parseInt(txt_id.getText());
        String name =txt_name.getText();
        sc.modifier(new Categorie(id, name));
        UIManager.put("OptionPane.minimumSize",new Dimension(500,200)); 
            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
          "Arial", Font.BOLD, 30)));
            JOptionPane.showMessageDialog(null, "Categorie modifiée avec succées", "Succèes!", JOptionPane.INFORMATION_MESSAGE);
            txt_name.clear();
            txt_id.clear();
            updateTable();
    }
        @FXML
    private void deleteCategory(){
        int id=Integer.parseInt(txt_id.getText());
        String name =txt_name.getText();
        sc.supprimer(new Categorie(id, name));
        UIManager.put("OptionPane.minimumSize",new Dimension(500,200)); 
            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
          "Arial", Font.BOLD, 30)));
            JOptionPane.showMessageDialog(null, "Categorie supprimée avec succées", "Succèes!", JOptionPane.INFORMATION_MESSAGE);
            txt_name.clear();
            txt_id.clear();
            updateTable();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTable();
    }    
    
}
