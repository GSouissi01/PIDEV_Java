/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import tn.edu.esprit.entites.PasswordResetToken;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author SOUISSI
 */
public class ResetPasswordController implements Initializable {

    @FXML
    private Button resetPasswordButton;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    private void resetPassword() {
    String token = getResetTokenFromURL();
    ServiceUser su = new ServiceUser();
    // Verify that the token is valid and has not expired
    PasswordResetToken resetToken = su.getPasswordResetToken(token);
    if (resetToken == null || resetToken.isExpired()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Password Reset");
        alert.setHeaderText("Invalid or Expired Token");
        alert.setContentText("Sorry, the password reset link you clicked is no longer valid. Please try again.");
        alert.showAndWait();
        return;
    }

    // Validate the user input to ensure the new password meets any requirements (e.g. minimum length, complexity)
    String newPassword = newPasswordField.getText();
    String confirmPassword = confirmPasswordField.getText();
    if (!isValidPassword(newPassword)) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Password Reset");
        alert.setHeaderText("Invalid Password");
        alert.setContentText("Sorry, the new password you entered is not valid. Please try again.");
        alert.showAndWait();
        return;
    }
    if (!newPassword.equals(confirmPassword)) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Password Reset");
        alert.setHeaderText("Passwords do not match");
        alert.setContentText("Sorry, the new passwords you entered do not match. Please try again.");
        alert.showAndWait();
        return;
    }

    // Update the user's account information in the database with the new password
    su.updateUserPassword(Integer.toString(resetToken.getUserId()), newPassword);

    // Display a success message to the user and prompt them to log in again using their new password
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Password Reset");
    alert.setHeaderText("Password reset successful");
    alert.setContentText("Your password has been reset. Please log in again using your new password.");
    alert.showAndWait();

    // Close the password reset screen
    Stage stage = (Stage) resetPasswordButton.getScene().getWindow();
    stage.close();
}
    
    private String getResetTokenFromURL() {
    // Parse the reset token from the URL of the password reset screen
    String url = getClass().getResource("/reset-password.fxml").toString();
    int tokenIndex = url.indexOf("token=") + 6;
    return url.substring(tokenIndex);
}

private boolean isValidPassword(String password) {
    // Validate the new password to ensure it meets any requirements (e.g. minimum length, complexity)
    // You can customize this method to suit your application's password policy
    return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*\\d.*");
}
}
