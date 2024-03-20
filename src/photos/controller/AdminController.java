package photos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;

public class AdminController {

    @FXML
    private Button addUser;

    @FXML
    private Button deleteUser;

    @FXML
    private Button renameUser;

    @FXML
    private Button logOutBtn;

    @FXML
    private ListView<String> userList;

    @FXML
    void initialize() {
        // populate the array of users for the listview
        populateUserList();
    }

    private void populateUserList() {
        try {
            File usersFile = new File("src/photos/models/users.txt");
            Scanner scanner = new Scanner(usersFile);
            ObservableList<String> users = FXCollections.observableArrayList();

            while (scanner.hasNextLine()) {
                String user = scanner.nextLine();
                users.add(user);
            }

            userList.setItems(users);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddUser(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add User");
        dialog.setHeaderText("Enter Username:");
        dialog.setContentText("Username:");

        Optional<String> result = dialog.showAndWait();

        //String newUser = usernameField.getText().trim();

        // Check if the user is empty
        result.ifPresent(username -> {
            if (!username.isEmpty()) {
                userList.getItems().add(username);
                writeUserToFile(username);
            }
        });
    }

    // Method to write the new user to the users.txt file
    private void writeUserToFile(String newUser) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/photos/models/users.txt", true))) {
            writer.println("\n" + newUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteUser(ActionEvent event) {

    }

    @FXML
    void handleRenameUser(ActionEvent event) {

    }

    @FXML
    void handleLogOut(ActionEvent event) {
        try {
            Stage logInStage = new Stage();
            // Load the dashboard FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/photos/view/login.fxml"));
            logInStage.setScene(new Scene(root));
            logInStage.setTitle("Dashboard");
            logInStage.show();

            // Close the login window
            Stage adminStage = (Stage) logOutBtn.getScene().getWindow();
            adminStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
