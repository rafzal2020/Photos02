package photos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static photos.controller.LoginController.getLoggedInUser;


public class DashboardController {

    @FXML
    private Button addAlbumBtn;

    @FXML
    private Button albumButton;

    @FXML
    private TilePane albumPane;

    @FXML
    private DatePicker calendarSort;

    @FXML
    private Label dateCreatedLabel;

    @FXML
    private Button deleteAlbumBtn;

    @FXML
    private ChoiceBox<String> selectAlbumChoice;

    private ObservableList<String> albumNames = FXCollections.observableArrayList();

    @FXML
    private Button logOutBtn;

    @FXML
    private Label photoCountLabel;

    @FXML
    private Button renameAlbumButton;

    @FXML
    private ChoiceBox<String> selectAlbumChoiceRename;

    private ObservableList<String> renameAlbumChoices = FXCollections.observableArrayList();

    @FXML
    private TextField renameAlbumField;

    @FXML
    private Label userLabel;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    private String selectedAlbumToDelete;

    private String selectedAlbumToRename;

    @FXML
    void initialize() {
        userLabel.setText("Logged in as " + getLoggedInUser());
        albumNames.add("Album");
        selectAlbumChoice.setItems(albumNames);
        selectAlbumChoiceRename.setItems(albumNames);

        selectAlbumChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedAlbumToDelete = newValue; // Update the selected album name
        });
        selectAlbumChoiceRename.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            selectedAlbumToRename = newVal;
        });


    }

    @FXML
    void handleAddAlbum(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Album");
        dialog.setHeaderText("Enter Album Name:");
        dialog.setContentText("Album Name:");

        Optional<String> result = dialog.showAndWait();

        ImageView albumIcon = new ImageView(new Image("/photos/models/folder-regular.png"));
        albumIcon.setFitHeight(55);
        albumIcon.setFitWidth(55);
        albumIcon.setPickOnBounds(true);
        albumIcon.setPreserveRatio(true);
        // Create a new Label for the album name
        Label albumName = new Label();
        result.ifPresent(username -> {
            if (!username.isEmpty()) {
                albumName.setText(username);
            } else {
                albumName.setText("New Album");
            }
        });

        //albumName.setPrefSize(17, 55);
        albumName.setAlignment(Pos.CENTER);
        albumName.setFont(new Font("Arial", 8));

        // Create a VBox to hold the album icon and name
        VBox buttonContainer = new VBox(albumIcon, albumName);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPrefSize(55, 55);

        // Create a new Button instance
        Button newAlbumButton = new Button();
        newAlbumButton.setMnemonicParsing(true);
        // Set the graphic of the button to the VBox containing the album icon and name
        newAlbumButton.setGraphic(buttonContainer);
        // Set the preferred size of the button
        newAlbumButton.setPrefSize(71, 71);

        // Add the new album button to the albumPane TilePane
        albumNames.add(albumName.getText());
        selectAlbumChoice.setItems(albumNames);
        albumPane.getChildren().add(newAlbumButton);
    }

    @FXML
    void handleDeleteAlbum(ActionEvent event) {
        if (selectedAlbumToDelete != null) {
            albumPane.getChildren().removeIf(node -> {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    VBox buttonContainer = (VBox) button.getGraphic();
                    Label albumLabel = (Label) buttonContainer.getChildren().get(1);
                    return albumLabel.getText().equals(selectedAlbumToDelete);
                }
                return false;
            });
            albumNames.remove(selectedAlbumToDelete); // Remove album name from list
            selectedAlbumToDelete = null; // Reset selected album name
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

    @FXML
    void handleRenameAlbum(ActionEvent event) {
        String newName = renameAlbumField.getText();
        if (selectedAlbumToRename != null && !newName.isEmpty()) {
            albumPane.getChildren().forEach(node -> {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    VBox buttonContainer = (VBox) button.getGraphic();
                    Label albumLabel = (Label) buttonContainer.getChildren().get(1);
                    if (albumLabel.getText().equals(selectedAlbumToRename)) {
                        albumLabel.setText(newName); // Update the text of the album label
                    }
                }
            });
            // Update the album name in the list
            albumNames.set(albumNames.indexOf(selectedAlbumToRename), newName);
            selectedAlbumToRename = null; // Reset selected album name
            renameAlbumField.clear(); // Clear the text field
        }
    }

}
