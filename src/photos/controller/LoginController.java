package photos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin(ActionEvent event) {
        // Implement your login logic here
        // For simplicity, let's just open the dashboard directly
        openDashboard();
    }

    private void openDashboard() {
        try {
            Stage dashboardStage = new Stage();
            // Load the dashboard FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/photos/view/dashboard.fxml"));
            dashboardStage.setScene(new Scene(root));
            dashboardStage.setTitle("Dashboard");
            dashboardStage.show();

            // Close the login window
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}