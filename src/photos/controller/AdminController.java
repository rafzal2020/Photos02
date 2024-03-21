package photos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.Optional;
import java.util.Scanner;

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
        userList.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null && newItem.equalsIgnoreCase("admin")) {
                // If the selected item is "admin", disable the delete button
                deleteUser.setDisable(true);
                renameUser.setDisable(true);
            } else {
                // If the selected item is not "admin" or no item is selected, enable the delete button
                deleteUser.setDisable(false);
                renameUser.setDisable(false);
            }
        });
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

            users.sort(null);
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
                if (isUsernameExists(username)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("This username already exists.");
                    alert.setContentText("Please enter a different username");
                    alert.showAndWait();
                } else {
                    userList.getItems().add(username);
                    writeUserToFile(username);
                }
            }
        });
    }

    private boolean isUsernameExists(String user) {
        try (Scanner scanner = new Scanner(new File("src/photos/models/users.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equalsIgnoreCase(user)) {
                    return true;
                }
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to write the new user to the users.txt file
    private void writeUserToFile(String newUser) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/photos/models/users.txt", true))) {
            writer.println(newUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUserFromFile(String user) {
        String filePath = "src/photos/models/users.txt";

        try {
            // Read the contents of the file
            File inputFile = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                // Check if the line contains the text to delete
                if (!line.contains(user)) {
                    sb.append(line).append(System.lineSeparator());
                }
            }
            reader.close();

            // Write the modified content back to the file
            FileWriter writer = new FileWriter(inputFile);
            writer.write(sb.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteUser(ActionEvent event) {
        String selectedUser = userList.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            ObservableList<String> users = userList.getItems();
            users.remove(selectedUser);
            deleteUserFromFile(selectedUser);
        }
    }

    @FXML
    void handleRenameUser(ActionEvent event) {
        int selectedUserIndex = userList.getSelectionModel().getSelectedIndex();

        if (selectedUserIndex >= 0) {
            String oldUsername = userList.getItems().get(selectedUserIndex);

            TextInputDialog dialog = new TextInputDialog(oldUsername);
            dialog.setTitle("Rename User");
            dialog.setHeaderText("Change Username:");
            dialog.setContentText("Username:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(newUsername -> {
                if (!newUsername.isEmpty()) {
                    userList.getItems().set(selectedUserIndex, newUsername);
                    deleteUserFromFile(oldUsername);
                    writeUserToFile(newUsername);
                }
            });
        }
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
