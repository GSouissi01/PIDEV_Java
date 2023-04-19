/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.services.ServiceUser;
import tn.edu.esprit.utils.JDBCDao;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Transport;
import javax.mail.MessagingException;
import tn.edu.esprit.services.TwilioService;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class Login1Controller implements Initializable {

    @FXML
    private BorderPane login_form;
    @FXML
    private Button tfCreateAcc1_btn;
    @FXML
    private TextField tfEmail_Login;
    @FXML
    private PasswordField pfPassword_Login;
    private FXMLLoader registrationLoader;
    @FXML
    private Button submitButton;
    @FXML
    private Button forgotPasswordButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        forgotPasswordButton.setOnAction(event -> showForgotPasswordDialog());
    }    
    
    @FXML
private void handleLogin(ActionEvent event) throws IOException {
    String email = tfEmail_Login.getText();
    String password = pfPassword_Login.getText();

    ServiceUser su = new ServiceUser();
    User u = su.login(email, password);
        System.out.println(u.isIsBanned());
    if (u == null) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Invalid email or password");
        alert.showAndWait();
    } else if (!u.isIsBanned()) { // check if user is banned
        // send SMS notification to user
        TwilioService.sendSms("+216 97397598", "Your access to the application has been restricted due to a violation of our terms of service.");
        
        // show error message to user
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Your access to the application has been restricted due to a violation of our terms of service.");
        alert.showAndWait();
    } else {
        if (u.getRole().equals("ADMIN")) {
            // redirect to backend page
            Parent root = FXMLLoader.load(getClass().getResource("../gui/BackEnd.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (u.getRole().equals("USER")) {
            // redirect to user profile page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
            Parent root = loader.load();
            ProfileController acc = loader.getController();
            acc.setTextNom(u.getNom());
            acc.setTextPrenom(u.getPrenom());
            acc.setTextEmail(u.getEmail());
            acc.setTextTel("" + u.getTel());
            acc.setTextSup(u.getNomSup());
            acc.setTextAdresse(u.getAdresseSup());
            acc.initData(u);

            tfEmail_Login.getScene().setRoot(root);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
    @FXML
    private void handleCreateAccountButtonAction(ActionEvent event) throws IOException {
        if (registrationLoader == null) {
            registrationLoader = new FXMLLoader(getClass().getResource("Register1.fxml"));
            Parent root = registrationLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } else {
            Parent root = registrationLoader.getRoot(); 
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
    }
    public void login(ActionEvent event) throws SQLException {

        Window owner = submitButton.getScene().getWindow();

        System.out.println(tfEmail_Login.getText());
        System.out.println(pfPassword_Login.getText());

        if (tfEmail_Login.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter your email id");
            return;
        }
        if (pfPassword_Login.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter a password");
            return;
        }

        String emailId = tfEmail_Login.getText();
        String password = pfPassword_Login.getText();

        JDBCDao jdbcDao = new JDBCDao();
        boolean flag = jdbcDao.validate(emailId, password);

        if (!flag) {
            infoBox("Please enter correct Email and Password", null, "Failed");
        } else {
            infoBox("Login Successful!", null, "Failed");
        }
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    private void showForgotPasswordDialog() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Forgot Password");
    dialog.setHeaderText("Enter your email address or username");
    dialog.setContentText("Email or Username:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(username -> {
        // Validate the user input
        if (isValidEmail(username)) {
            // Look up the user's account information in the database
            ServiceUser su = new ServiceUser();
            User user = su.getUserByEmail(username);

            if (user != null) {
                // Generate a unique token or code and store it in the database with the user's ID and a timestamp
                String token = generateUniqueToken();
                long timestamp = System.currentTimeMillis();
                su.insertPasswordResetToken(user.getId(), token, timestamp);

                // Send an email to the user with a link to a new window or dialog box that allows the user to enter and confirm a new password
                sendPasswordResetEmail(user.getEmail(), token);

                // Display a message to the user indicating that an email has been sent
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password Reset");
                alert.setHeaderText("Email Sent");
                alert.setContentText("Please check your email for instructions on resetting your password.");
                alert.showAndWait();
            } else {
                // Display an error message if the user does not exist
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Password Reset");
                alert.setHeaderText("User not found");
                alert.setContentText("Sorry, we could not find a user with that email address.");
                alert.showAndWait();
            }
        } else {
            // Display an error message if the user input is not a valid email address or username
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Reset");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid email address or username.");
            alert.showAndWait();
        }
    });
}
    private void sendPasswordResetEmail(String email, String token) {
    // Use a JavaMail library or API to send an email to the user with a link to a new window or dialog box that allows the user to enter and confirm a new password
    // You will need to configure the email server and credentials for your application
    // Here is an example using the JavaMail API:
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("ghadasouissi01@gmail.com", "ypolgyvuskvdhahd");
        }
    });
    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("ghadasouissi01@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Password Reset");
        message.setText("Please click the following link to reset your password:\n\n" +
                "https://your_application.com/reset-password?token=" + token);
        Transport.send(message);
    } catch (MessagingException e) {
        e.printStackTrace();
    }
}
    public boolean isValidEmail(String email) {
    // Regular expression pattern for email validation
    String emailPattern = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    return email.matches(emailPattern);
}
    private String generateUniqueToken() {
    // Use a random number generator or UUID to generate a unique token or code
    // This should be a long, cryptographically secure string that cannot be easily guessed
    return UUID.randomUUID().toString();
}


  
    
}

