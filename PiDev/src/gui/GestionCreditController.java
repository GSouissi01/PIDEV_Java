/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Client;
import entity.Credit;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import service.ClientService;
import service.CreditService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class GestionCreditController implements Initializable {

    private Label label;
    public static Credit connectedCredit;
    public static Client connectedClient;
    @FXML
    private TableView<Credit> tableview2;
    @FXML
    private TableColumn<?, ?> client_id;
    @FXML
    private TableColumn<?, ?> valeur;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private Button Modifier;
    @FXML
    private Button Supprimer;
    @FXML
    private Button gotocredit;
    @FXML
    private TableView<Client> tableview3;
    @FXML
    private TableColumn<?, ?> nom;
    @FXML
    private TableColumn<?, ?> prenom;
    @FXML
    private TableColumn<?, ?> email;
    @FXML
    private TableColumn<?, ?> tel;
    @FXML
    private Button Modifier1;
    @FXML
    private Button gotoclient;
    @FXML
    private Button Supprimer2;
    public ObservableList<Credit> list;
    public ObservableList<Client> list1;
    @FXML
    private Button pdf2;
    @FXML
    private Button afficherpardate;
    @FXML
    private TextField inputRech;
    @FXML
    private Button pdf3;
    @FXML
    private Button afficherparnom;
    @FXML
    private TextField inputRech1;
    @FXML
    private AnchorPane anchorepa;
    @FXML
    private AnchorPane anchorepanecredit;
    @FXML
    private ListView<Credit> listcredit;
    @FXML
    private ListView<Client> listclient;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CreditService pss = new CreditService();
        ArrayList<Credit> c = new ArrayList<>();
        try {
            c = (ArrayList<Credit>) pss.AfficherCredit();
        } catch (SQLException ex) {
        }

        ObservableList<Credit> obs2 = FXCollections.observableArrayList(c);
        listcredit.setItems(obs2);

        listcredit.setCellFactory(param -> new ListCell<Credit>() {
            @Override
            protected void updateItem(Credit item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    Label clientIdLabel = new Label("Client ID: " + item.getClient_id());
                    Label valeurLabel = new Label("Valeur: " + item.getValeur());
                    Label dateLabel = new Label("Date: " + item.getDate());

                    VBox vbox = new VBox(clientIdLabel, valeurLabel, dateLabel);
                    vbox.setSpacing(5);

                    setText(null);
                    setGraphic(vbox);
                }
            }
        });

        ClientService pss2 = new ClientService();
        ArrayList<Client> c2 = new ArrayList<>();
        try {
            c2 = (ArrayList<Client>) pss2.AfficherClient();
        } catch (SQLException ex) {
        }

        ObservableList<Client> obs22 = FXCollections.observableArrayList(c2);
        listclient.setItems(obs22);
listclient.setCellFactory(new Callback<ListView<Client>, ListCell<Client>>() {
    @Override
    public ListCell<Client> call(ListView<Client> param) {
        return new ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setText(null);
                } else {
                    VBox vbox = new VBox();
                    Label nomLabel = new Label("Nom: " + client.getNom());
                    Label prenomLabel = new Label("Prénom: " + client.getPrenom());
                    Label emailLabel = new Label("Email: " + client.getEmail());
                    Label telLabel = new Label("Téléphone: " + client.getTel());
                    vbox.getChildren().addAll(nomLabel, prenomLabel, emailLabel, telLabel);
                    setGraphic(vbox);
                }
            }
        };
    }
});




        try {
            list = FXCollections.observableArrayList(
                    pss.AfficherCredit()
            );
            FilteredList<Credit> filteredData = new FilteredList<>(list, e -> true);
            inputRech.setOnKeyReleased(e -> {
                inputRech.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super Credit>) Credits -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lower = newValue.toLowerCase();
                        String tostring = Float.toString(Credits.getValeur());
                        if (tostring.toLowerCase().contains(lower)) {
                            return true;
                        }
                        return false;
                    });
                });
                SortedList<Credit> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().set(Comparator.comparing(Credit::getValeur));
                listcredit.setItems(sortedData);
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
    list1 = FXCollections.observableArrayList(
        pss2.AfficherClient()
    );

    FilteredList<Client> filteredData = new FilteredList<>(list1, e -> true);
    inputRech1.setOnKeyReleased(e -> {
        inputRech1.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Client>) Clients -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lower = newValue.toLowerCase();
                String tostring = Clients.getNom();
                if (tostring.toLowerCase().contains(lower)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Client> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().set(Comparator.comparing(Client::getNom));
        listclient.setItems(sortedData);
    });
} catch (Exception e) {
    System.out.println(e.getMessage());
}


    }

    public void resetTableData() throws SQLDataException, SQLException {
        CreditService cs = new CreditService();
        List<Credit> listevents = new ArrayList<>();
        listevents = cs.AfficherCredit();
        ObservableList<Credit> data = FXCollections.observableArrayList(listevents);
        listcredit.setItems(data);
    }

    public void resetTableData2() throws SQLDataException, SQLException {
        ClientService cs = new ClientService();
        List<Client> listevents = new ArrayList<>();
        listevents = cs.AfficherClient();
        ObservableList<Client> data = FXCollections.observableArrayList(listevents);
        listclient.setItems(data);
    }

    @FXML
    private void Modifier(ActionEvent event) throws IOException {

        CreditService ps = new CreditService();
        Credit c = new Credit(listcredit.getSelectionModel().getSelectedItem().getId(),
                listcredit.getSelectionModel().getSelectedItem().getClient_id(),
                listcredit.getSelectionModel().getSelectedItem().getValeur(),
                listcredit.getSelectionModel().getSelectedItem().getDate());

        GestionCreditController.connectedCredit = c;
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("ModifierCredit.fxml"));
            anchorepanecredit.getChildren().clear();
            anchorepanecredit.getChildren().add(view);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void Supprimer(ActionEvent event) throws SQLException {
        if (event.getSource() == Supprimer) {
            Credit rec = new Credit();

            rec.setId(listcredit.getSelectionModel().getSelectedItem().getId());
            CreditService cs = new CreditService();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Are you sure want to delete this Credit");
            alert.setContentText(" ");

            // option != null.
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {

            } else if (option.get() == ButtonType.OK) {

                cs.SupprimerCredit(rec);

                TrayNotification tray = new TrayNotification();
                AnimationType type = AnimationType.SLIDE;
                tray.setAnimationType(type);
                tray.setTitle("Supprimé avec succés");
                tray.setMessage("Supprimé avec succés");
                tray.setNotificationType(NotificationType.ERROR);
                tray.showAndDismiss(Duration.millis(3000));

            } else if (option.get() == ButtonType.CANCEL) {

            } else {
                this.label.setText("-");
            }

            resetTableData();

        }

    }

    @FXML
    private void gotocredit(ActionEvent event) throws IOException {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("AjouterCredit.fxml"));
            anchorepanecredit.getChildren().clear();
            anchorepanecredit.getChildren().add(view);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void gotoclient(ActionEvent event) throws IOException {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("AjouterClient.fxml"));
            anchorepa.getChildren().clear();
            anchorepa.getChildren().add(view);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void Supprimer2(ActionEvent event) throws SQLException {
        if (event.getSource() == Supprimer2) {
            Client rec = new Client();

            rec.setId(listclient.getSelectionModel().getSelectedItem().getId());
            ClientService cs = new ClientService();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Are you sure want to delete this Client");
            alert.setContentText(" ");

            // option != null.
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {

            } else if (option.get() == ButtonType.OK) {

                cs.SupprimerClient(rec);

                TrayNotification tray = new TrayNotification();
                AnimationType type = AnimationType.SLIDE;
                tray.setAnimationType(type);
                tray.setTitle("Supprimé avec succés");
                tray.setMessage("Supprimé avec succés");
                tray.setNotificationType(NotificationType.ERROR);
                tray.showAndDismiss(Duration.millis(3000));

            } else if (option.get() == ButtonType.CANCEL) {

            } else {
                this.label.setText("-");
            }

            resetTableData2();

        }
    }

    @FXML
    private void Modifier1(ActionEvent event) throws IOException {
        ClientService ps = new ClientService();
        Client c = new Client(listclient.getSelectionModel().getSelectedItem().getId(),
                listclient.getSelectionModel().getSelectedItem().getNom(),
                listclient.getSelectionModel().getSelectedItem().getPrenom(),
                listclient.getSelectionModel().getSelectedItem().getEmail(),
                listclient.getSelectionModel().getSelectedItem().getTel());

        GestionCreditController.connectedClient = c;

        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("ModifierClient.fxml"));
            anchorepa.getChildren().clear();
            anchorepa.getChildren().add(view);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void printPDF() throws FileNotFoundException, DocumentException, IOException {

        Document d = new Document();
        PdfWriter.getInstance(d, new FileOutputStream("..\\ListCredit.pdf"));
        d.open();

        PdfPTable pTable = new PdfPTable(3);

        listcredit.getItems().forEach((t) -> {
            pTable.addCell(String.valueOf(t.getClient_id()));
            pTable.addCell(String.valueOf(t.getDate()));
            pTable.addCell(String.valueOf(t.getValeur()));

        });

        d.add(pTable);

        d.close();
        Desktop.getDesktop().open(new File("..\\ListCredit.pdf"));

    }

    @FXML
    private void pdf2(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
        if (event.getSource() == pdf2) {

            printPDF();

        }
    }

    @FXML
    private void afficherpardate(ActionEvent event) throws SQLException {
        CreditService cs = new CreditService();
        List<Credit> listevents = new ArrayList<>();
        listevents = cs.AfficherCreditParValeur();
        ObservableList<Credit> data = FXCollections.observableArrayList(listevents);
        listcredit.setItems(data);

    }

    private void printPDF2() throws FileNotFoundException, DocumentException, IOException {

        Document d = new Document();
        PdfWriter.getInstance(d, new FileOutputStream("..\\ListClient.pdf"));
        d.open();

        PdfPTable pTable = new PdfPTable(4);

        listclient.getItems().forEach((t) -> {
            pTable.addCell(String.valueOf(t.getEmail()));
            pTable.addCell(String.valueOf(t.getNom()));
            pTable.addCell(String.valueOf(t.getPrenom()));
            pTable.addCell(String.valueOf(t.getTel()));

        });

        d.add(pTable);

        d.close();
        Desktop.getDesktop().open(new File("..\\ListClient.pdf"));

    }

    @FXML
    private void pdf3(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
        if (event.getSource() == pdf3) {

            printPDF2();

        }
    }

    @FXML
    private void afficherparnom(ActionEvent event) throws SQLException {
        ClientService cs = new ClientService();
        List<Client> listevents = new ArrayList<>();
        listevents = cs.AfficherClientParNom();
        ObservableList<Client> data = FXCollections.observableArrayList(listevents);
        listclient.setItems(data);

    }

    private void stat(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("Stat.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

}
