/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import com.github.sarxos.webcam.Webcam;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
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

import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import tn.edu.esprit.entities.PasswordResetToken;
import tn.edu.esprit.dialog.PasswordDialog;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import com.google.common.collect.ImmutableMap;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.openqa.selenium.By;

import org.opencv.objdetect.CascadeClassifier;
import tn.edu.esprit.entities.PasswordHasher;
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
    private WebView webView;
    @FXML
    private Label statusLabel;
    int timeLeft = 6 * 60 * 60;
    @FXML
    private Label timerLabel;
    private ToggleButton toggleButton;
    @FXML
    private Button close;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        forgotPasswordButton.setOnAction(event -> showForgotPasswordDialog());
        webView = new WebView();
         close.setStyle("-fx-background-image: url('file:///C:/Users/azizb/Downloads/close.png');-fx-background-size: 100% 100%;");

    close.setOnAction(event -> {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
});
    }
    private int loginAttempts = 0;

    private void onShowPasswordToggle(ActionEvent event) {
        pfPassword_Login.setVisible(!toggleButton.isSelected());

        // If the password field is showing, set its text to the actual password
        if (pfPassword_Login.isVisible()) {
            pfPassword_Login.setText(pfPassword_Login.getUserData().toString());
        } else {
            pfPassword_Login.setText("");
        }
    }

 public String getTextEmail() {
        return tfEmail_Login.getText();
    }
 public String getTextPassword() {
        return pfPassword_Login.getText();
    }

    
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String email = tfEmail_Login.getText();
        String password = pfPassword_Login.getText();

        ServiceUser su = new ServiceUser();
        User u = su.login(email, password);

        if (u == null) {

            loginAttempts++;

            if (loginAttempts >= 3) {
                // Disable text fields
                tfEmail_Login.setDisable(true);
                pfPassword_Login.setDisable(true);

                // Start countdown timer
                Timer timer = new Timer();

                AnchorPane pane = (AnchorPane) tfEmail_Login.getScene().getRoot();
                pane.getChildren().add(timerLabel);

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            timeLeft--;
                            int hours = timeLeft / 3600;
                            int minutes = (timeLeft % 3600) / 60;
                            int seconds = timeLeft % 60;
                            timerLabel.setText("You can try again in " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                            timerLabel.setLayoutX(163);
                            timerLabel.setLayoutY(486);
                            if (timeLeft <= 0) {
                                tfEmail_Login.setDisable(false);
                                pfPassword_Login.setDisable(false);
                                timer.cancel();
                                timerLabel.setText("");
                                pane.getChildren().remove(timerLabel);
                            }
                        });
                    }
                }, 0, 1000);

                // Take a photo
                Webcam webcam = Webcam.getDefault();
                webcam.open();
                BufferedImage image = webcam.getImage();
                webcam.close();
                loginAttempts = 0;

                // Save the image
                File outputFile = new File("user_photo.png");
                ImageIO.write(image, "png", outputFile);

                // Show error message to user
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email or password");
                alert.showAndWait();
            } else {
                // Show error message to user
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email or password");
                alert.showAndWait();
            }
        } else if (u.isIsBanned()) { // check if user is banned
            // send SMS notification to user
            TwilioService.sendSms("+216 97397598", "Your access to the application has been restricted due to a violation of our terms of service.");

            // show error message to user
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Your access to the application has been restricted due to a violation of our terms of service.");
            alert.showAndWait();
        } else {
            loginAttempts = 0;

            if (u.getRole().equals("ADMIN")) {
                // redirect to backend page
                Parent root = FXMLLoader.load(getClass().getResource("dash.fxml"));
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

                Scene scene = new Scene(root, 78, 26);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                
                
            }
        }
    }


    /* @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String email = tfEmail_Login.getText();
        String password = pfPassword_Login.getText();

        UserController uc = new UserController();
        User u = new User();
        System.out.println(u.isIsBanned());
        if (!uc.authenticate(email, password)) {
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
/*
    @FXML
    private void handleFacialRecognitionLogin(ActionEvent event) {
        VideoCapture camera = new VideoCapture();
        camera.open(0); // Open the default camera

        Mat frame = new Mat();
        if (camera.read(frame)) {
            // Save the captured image to disk
            Imgcodecs.imwrite("capture.jpg", frame);

            // Use OpenCV to detect and extract facial features from the captured image
            CascadeClassifier faceDetector = new CascadeClassifier();
            faceDetector.load("haarcascade_frontalface_alt.xml"); // Load the pre-trained face detection classifier

            MatOfRect faceDetections = new MatOfRect();
            faceDetector.detectMultiScale(frame, faceDetections); // Detect faces in the captured image

            if (faceDetections.toArray().length > 0) { // If at least one face was detected
                // Extract the first face detected (assuming there is only one face in the image)
                Rect rect = faceDetections.toArray()[0];
                Mat face = new Mat(frame, rect);

                // Use OpenCV to encode the facial features of the extracted face
                MatOfByte encodedFace = new MatOfByte();
                Imgcodecs.imencode(".png", face, encodedFace);

                // Convert the encoded facial features to a byte array
                byte[] faceBytes = encodedFace.toArray();

                // Compare the facial features to the features of each user in your database
                ServiceUser su = new ServiceUser();
                List<User> users = su.getAllUsers();
                User matchedUser = null;
                for (User user : users) {
                    byte[] userFaceBytes = user.getFace(); // Retrieve the facial features of the user from the database
                    if (userFaceBytes != null && Arrays.equals(faceBytes, userFaceBytes)) { // Compare the features
                        matchedUser = user;
                        break;
                    }
                }

                if (matchedUser != null) { // If a match is found
                    try {
                        // Load the profile view and pass the matched user as a parameter
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                        Parent root = loader.load();
                        ProfileController acc = loader.getController();
                        acc.setTextNom(matchedUser.getNom());
                        acc.setTextPrenom(matchedUser.getPrenom());
                        acc.setTextEmail(matchedUser.getEmail());
                        acc.setTextTel("" + matchedUser.getTel());
                        acc.setTextSup(matchedUser.getNomSup());
                        acc.setTextAdresse(matchedUser.getAdresseSup());
                        acc.initData(matchedUser);

                        tfEmail_Login.getScene().setRoot(root);

                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else { // If no match is found
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("No match found, please log in using email and password");
                    alert.showAndWait();
                }
            } else { // If no faces were detected
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("No face detected, please try again");
                alert.showAndWait();
            }
        } else {
            // Failed to capture image from camera
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Failed to capture image from camera");
            alert.showAndWait();
        }

        camera.release();
    }
     */
   
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

        dialog.showAndWait().ifPresent(username -> {
            // Do something with the username string
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

                    // Show a dialog box to allow the user to enter the code and their new password
                    TextInputDialog resetDialog = new TextInputDialog();
                    resetDialog.setTitle("Reset Password");
                    resetDialog.setHeaderText("Enter the code sent to your email and your new password");

                    resetDialog.setContentText("Code:");

                    Optional<String> resetResult = resetDialog.showAndWait();

                    resetResult.ifPresent(code -> {

                        // Validate the user input
                        if (!isValidCode(code.trim())) {
                            // Look up the user's password reset token in the database
                            PasswordResetToken resetToken = su.getPasswordResetToken(token);
                            System.out.println("Before");
                            if (resetToken != null) {
                                // Check if the token has expired
                                long now = System.currentTimeMillis();
                                long elapsedTime = now - resetToken.getTimestamp().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                                long expiryTime = TimeUnit.MINUTES.toMillis(30); // Token expires after 30 minutes
                                System.out.println("now: " + now + ", token timestamp: " + resetToken.getTimestamp() + ", elapsed time: " + elapsedTime);
                                if (elapsedTime <= expiryTime) {
                                    // Show a dialog box to allow the user to enter their new password
                                    PasswordDialog passwordDialog = new PasswordDialog();
                                    Optional<String> newPasswordResult = passwordDialog.showAndWait();

                                    if (newPasswordResult.isPresent()) {
                                        String newPassword = newPasswordResult.get();
                                        String hashedPassword = PasswordHasher.hashPassword(newPassword);

                                        // Update the user's password in the database
                                        su.updateUserPassword(user.getId(), hashedPassword);

                                        // Display a message to the user indicating that their password has been reset
                                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                        successAlert.setTitle("Password Reset");
                                        successAlert.setHeaderText("Password Reset Successful");
                                        successAlert.setContentText("Your password has been reset.");
                                        successAlert.showAndWait();
                                    }
                                } else {
                                    // Display an error message if the token has expired
                                    Alert expiredAlert = new Alert(Alert.AlertType.ERROR);
                                    expiredAlert.setTitle("Password Reset");
                                    expiredAlert.setHeaderText("Token Expired");
                                    expiredAlert.setContentText("Sorry, the password reset token has expired.");
                                    expiredAlert.showAndWait();
                                }
                            } else {
                                // Display an error message if the user enters an invalid code
                                Alert invalidCodeAlert = new Alert(Alert.AlertType.ERROR);
                                invalidCodeAlert.setTitle("Password Reset");
                                invalidCodeAlert.setHeaderText("Invalid Code");
                                invalidCodeAlert.setContentText("Sorry, the code you entered is invalid.");
                                invalidCodeAlert.showAndWait();
                            }
                        }
                    });
                } else {
// Display an error message if the user enters an invalid email address or username
                    Alert invalidUserAlert = new Alert(Alert.AlertType.ERROR);
                    invalidUserAlert.setTitle("Password Reset");
                    invalidUserAlert.setHeaderText("Invalid Email or Username");
                    invalidUserAlert.setContentText("Sorry, we could not find an account with the email address or username you entered.");
                    invalidUserAlert.showAndWait();
                }
            } else {
// Display an error message if the user enters an invalid email address or username
                Alert invalidInputAlert = new Alert(Alert.AlertType.ERROR);
                invalidInputAlert.setTitle("Password Reset");
                invalidInputAlert.setHeaderText("Invalid Input");
                invalidInputAlert.setContentText("Sorry, you entered an invalid email address or username.");
                invalidInputAlert.showAndWait();
            }
        });

    }

    private void sendPasswordResetEmail(String email, String code) {
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
            message.setText("Please enter the following code to reset your password:\n\n"
                    + code);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidCode(String code) {
// Regular expression pattern for validating password reset codes
        String trimmedCode = code.trim();
        String pattern = "[a-zA-Z0-9]{6}";

        // Validate the code against the pattern
        return trimmedCode.matches(pattern);
    }

    public boolean isValidEmail(String email) {
        try {
            new InternetAddress(email, true).validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }

    private String generateUniqueToken() {
// Generate a random alphanumeric string of length 32
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            int index = random.nextInt(characters.length());
            token.append(characters.charAt(index));
        }
        return token.toString();
    }

    /*
     */

 /*@FXML
    private void facebooklogin(ActionEvent event) throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\SOUISSI\\Downloads\\chromedriver_win32\\chromedriver.exe");
        //   java.io.File file = new java.io.File("src/GUI/Authentification/newfile.txt");;

        String accessToken;
        WebDriver driver = new ChromeDriver();
        
        driver.get("https:/www.facebook.com");
        
        driver.manage().window().maximize();
        driver.findElement(By.id("email")).sendKeys("97397598");
        driver.findElement(By.name("pass")).sendKeys("Bramli05112021");
        driver.findElement(By.name("login")).click();
/*
///https://stackoverflow.com/people/auth/facebook/callback
        String authURl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=436759923642453&redirect_uri=https://stackoverflow.com/people/auth/facebook/callback/&scope=email";


        
        //client_id=436759923642453&redirect_uri=https://stackoverflow.com/people/auth/facebook/callback/
        
        
        
        
        // String authURl="https://www.facebook.com/v4.0/dialog/oauth?response_type=token&display=popup&client_id=436759923642453&redirect_uri=https://stackoverflow.com";
        driver.get(authURl);

        while (true) {
            if (driver.getCurrentUrl().contains("facebook.com")) {
                String url = driver.getCurrentUrl();
                String first;
                accessToken=url.replaceAll(".*#access_token=(.+)&.*","$1");
                  System.out.println(accessToken);
                first = accessToken.substring(13, accessToken.lastIndexOf("&"));
                
                
                FacebookClient fbClient = new DefaultFacebookClient(first,Version.VERSION_13_0);//accessToken, Version.VERSION_2_4


                //System.out.println("111111111111111111     "+fbClient.getClass());
                
                //, User.class,Parameter.with("fields", "email")
                User me = fbClient.fetchObject("me",User.class);
               

                /*
                FacebookClient fbClient = new DefaultFacebookClient("me", Version.LATEST);
                User user = fbClient.fetchObject("me", User.class);*///User me = fbClient.fetchObject("me",User.class);
    //System.out.println(me.getFirstName());
    //System.out.println(me)
    // driver.quit();
    //System.out.println(me.getFirstName());
    /*((Node) (event.getSource())).getScene().getWindow().hide();

            } else {
                System.out.println("erreur");
            }
        }

    }*/
}
