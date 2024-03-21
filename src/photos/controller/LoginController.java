package photos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import java.io.IOException;

import javafx.event.ActionEvent;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private Button loginButton;

    @FXML
    private Label blankField;

    @FXML
    private void handleLogin(ActionEvent event) {
        // login logic
        String user = usernameField.getText();
        File users = new File("src/photos/models/users.txt");

        try {
            //File users = new File("users.txt");
            Scanner readUsers = new Scanner(users);
            while(readUsers.hasNextLine()) {
                String data = readUsers.nextLine();
                String admin = "admin";
                if (user.equalsIgnoreCase(data)) {
                    if (user.equalsIgnoreCase(admin)) {
                        openAdminDashboard();
                    }
                    else openDashboard();
                }
                else {
                    blankField.setText("The user \"" + user + "\" does not exist.");
                }
            }
            readUsers.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    private void openAdminDashboard() {
        try {
            Stage adminStage = new Stage();
            // Load the dashboard FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/photos/view/admin.fxml"));
            adminStage.setScene(new Scene(root));
            adminStage.setTitle("Dashboard");
            adminStage.show();

            // Close the login window
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}