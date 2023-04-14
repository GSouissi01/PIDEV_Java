/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Contrat;
import Services.ServiceContrat;
import Services.ServiceCategorie;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * FXML Controller class
 *
 * @author Safe
 */
public class ContratBackController implements Initializable {

    @FXML
    private TableView<Contrat> table;
    @FXML
    private TableColumn<Contrat, Number> col_id;
    @FXML
    private JFXTextField txt_id;
    @FXML
    private TableColumn<Contrat, String> col_datesignature;
    @FXML
    private TableColumn<Contrat, String> col_dateexpiration;
    @FXML
    private TableColumn<Contrat, String> col_montant;
    @FXML
    private TableColumn<Contrat, String> col_imagecontrat;
   
    @FXML
    private JFXTextField txt_datesignature;
    @FXML
    private JFXTextField txt_dateexpiration;
    @FXML
    private JFXTextField txt_montant;
    @FXML
    private ImageView imagecontrat;
    @FXML
    private JFXComboBox<String> combo;
    @FXML
    private Label filepath;
    @FXML
    private AnchorPane anch;
    @FXML
    private Label label_img;
    @FXML
    private TextField txt_search;

    /**
     * Initializes the controller class.
     */
        public void comboBoxList(){
          Services.ServiceCategorie sa = new ServiceCategorie();
         List<String> articles = sa.getAllNames();
        ObservableList<String> observable = FXCollections.observableArrayList(articles);
        combo.setItems(observable);
    }
    
    @FXML
     public void updateTable(){
         
        
        String val = combo.getValue();
        ServiceContrat sa = new ServiceContrat();
        List<Contrat> art = sa.getContratByCategoryName(val);
        ObservableList<Contrat> listM=FXCollections.observableArrayList(art);
       col_id.setCellValueFactory(new PropertyValueFactory<Contrat,Number>("id"));
         col_datesignature.setCellValueFactory(new PropertyValueFactory<Contrat,String>("datesignature"));
        col_dateexpiration.setCellValueFactory(new PropertyValueFactory<Contrat,String>("dateexpiration"));
        col_montant.setCellValueFactory(new PropertyValueFactory<Contrat,String>("montant"));
        col_imagecontrat.setCellValueFactory(new PropertyValueFactory<Contrat,String>("imagecontrat"));
        table.setItems(listM);
        
        FilteredList<Contrat> filteredData= new FilteredList<>(listM,b->true);
     
       
       ObservableList<Contrat> newdata = listM.stream().filter(n -> n.getDateexpiration().toLowerCase().contains(txt_search.getText().toLowerCase())||
               n.getDatesignature().toLowerCase().contains(txt_search.getText().toLowerCase())||
               n.getMontant().toLowerCase().contains(txt_search.getText().toLowerCase())
              ).collect(Collectors.toCollection(FXCollections::observableArrayList));
      table.setItems(newdata);
    }
 
       int index;
    @FXML
    public void getSelected(){
        index = table.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_datesignature.setText(col_datesignature.getCellData(index).toString());
        txt_dateexpiration.setText(col_dateexpiration.getCellData(index).toString());
        txt_montant.setText(col_montant.getCellData(index).toString());
        label_img.setText("");
      String picture ="file:" +  col_imagecontrat.getCellData(index).toString();
        filepath.setText(col_imagecontrat.getCellData(index).toString());
    Image image1 = new Image(picture, 110, 110, false, true);            
            imagecontrat.setImage(image1);

        
            
    }
    private void  clearTextfields(){
    txt_id.clear();
    txt_datesignature.clear();
    txt_dateexpiration.clear();
    txt_montant.clear();
    
    filepath.setText("");
    imagecontrat.setImage(null);

}

    @FXML
    private void addContrat() {
       
        System.out.println("title"+txt_datesignature.getText());
      
      ServiceCategorie sa = new ServiceCategorie();
      ServiceContrat sar = new ServiceContrat();
      sar.add(new Contrat(txt_datesignature.getText(), txt_dateexpiration.getText(), txt_montant.getText(), filepath.getText()));
      UIManager.put("OptionPane.minimumSize",new Dimension(500,200)); 
            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
          "Arial", Font.BOLD, 30)));
            JOptionPane.showMessageDialog(null, "Contrat ajouté avec succées", "Succèes!", JOptionPane.INFORMATION_MESSAGE);
            clearTextfields();
            updateTable();
    }
    
    @FXML
    private void editContrat() {
       
      
      ServiceCategorie sa = new ServiceCategorie();
      ServiceContrat sar = new ServiceContrat();
      sar.modifier(new Contrat(Integer.parseInt(txt_id.getText()),txt_datesignature.getText(), txt_dateexpiration.getText(), txt_montant.getText(), filepath.getText()));
      UIManager.put("OptionPane.minimumSize",new Dimension(500,200)); 
            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
          "Arial", Font.BOLD, 30)));
            JOptionPane.showMessageDialog(null, "Contrat modifié avec succées", "Succèes!", JOptionPane.INFORMATION_MESSAGE);
            clearTextfields();
            updateTable();
    }
            @FXML
    private void deleteContrat(){
        ServiceContrat sa = new ServiceContrat();
        sa.supprimer(new Contrat(Integer.parseInt(txt_id.getText())));
        UIManager.put("OptionPane.minimumSize",new Dimension(500,200)); 
            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
          "Arial", Font.BOLD, 30)));
            JOptionPane.showMessageDialog(null, "Contrat supprimé avec succées", "Succèes!", JOptionPane.INFORMATION_MESSAGE);
            clearTextfields();
            updateTable();
    }
    @FXML
    public void insertImage() {
         FileChooser open = new FileChooser();
        
        Stage stage = (Stage)anch.getScene().getWindow();
        
        File file = open.showOpenDialog(stage);
        
        if(file != null){
            
            String path = file.getAbsolutePath();
            
            path = path.replace("\\", "\\\\");
            
            filepath.setText(path);

            Image img = new Image(file.toURI().toString(), 110, 110, false, true);
            
            imagecontrat.setImage(img);
            label_img.setText("");
        }else{
            
            System.out.println("NO DATA EXIST!");
            filepath.setText(null);
            
        }
    }



   @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxList();
    }  

    void setDatesignature(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setDateexpiration(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setMontant(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setImagecontrat(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
