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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static photos.controller.LoginController.getLoggedInUser;
import photos.models.Album;

public class DashboardController {

    @FXML
    private Button addAlbumBtn;

    @FXML
    private Button albumButton;

    @FXML
    private TilePane albumPane;

    @FXML
    private DatePicker calendarSortFrom;

    @FXML
    private DatePicker calendarSortTo;

    @FXML
    private Label dateCreatedLabel;

    @FXML
    private Button deleteAlbumBtn;

    @FXML
    private ChoiceBox<String> selectAlbumChoice;

    private ObservableList<Album> albums = FXCollections.observableArrayList();
    List<String> albumNamesList = albums.stream()
            .map(Album::getAlbumName)
            .collect(Collectors.toList());

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
        // create default album
        Album defaultAlbum = new Album("Album", getLoggedInUser(), 0);
        albums.add(defaultAlbum);
        initializeArrayPopulations();

        selectAlbumChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedAlbumToDelete = newValue; // Update the selected album name
        });
        selectAlbumChoiceRename.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            selectedAlbumToRename = newVal;
        });


    }

    void initializeArrayPopulations() {
        List<String> albumNamesList = albums.stream()
                .map(Album::getAlbumName)
                .collect(Collectors.toList());
        selectAlbumChoice.setItems(FXCollections.observableArrayList(albumNamesList));
        selectAlbumChoiceRename.setItems(FXCollections.observableArrayList(albumNamesList));
    }

    @FXML
    void handleAddAlbum(ActionEvent event) {
        boolean isValid = true;
        String s = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Album");
        dialog.setHeaderText("Enter Album Name:");
        dialog.setContentText("Album Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            s = result.get();
        }

        if (s != null && isNameExists(s)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("This album name already exists.");
            alert.setContentText("Please enter a different name");
            alert.showAndWait();
            isValid = false;
        }

        if (isValid) {
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
            Album newAlbum = new Album(albumName.getText(), getLoggedInUser(), 0);
            albums.add(newAlbum);
            initializeArrayPopulations();
            albumPane.getChildren().add(newAlbumButton);
            System.out.println(newAlbum.getAlbumName());
            System.out.println(newAlbum.getUser());
            System.out.println(newAlbum.getCreationDate().getTime());
        }
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
            // Remove album name from list
            albums.removeIf(album -> selectedAlbumToDelete != null && selectedAlbumToDelete.equalsIgnoreCase(album.getAlbumName()));
            selectedAlbumToDelete = null; // Reset selected album name
            initializeArrayPopulations();

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
                    /*Album renamedAlbum = null;
                    for (Album album : albums) {
                        if (albumLabel.getText().equalsIgnoreCase(album.getAlbumName())) {
                            renamedAlbum = album;
                            break;
                        }
                    }*/
                    if (albumLabel.getText().equals(selectedAlbumToRename)) {
                        albumLabel.setText(newName); // Update the text of the album label
                        //assert renamedAlbum != null;
                        //renamedAlbum.setAlbumName(newName);
                    }
                }
            });
            // Update the album name in the list
            for (Album album : albums) {
                if (selectedAlbumToRename.equalsIgnoreCase(album.getAlbumName())) {
                    album.setAlbumName(newName);
                    System.out.println("New Name: " + album.getAlbumName());
                    break;
                }
            }
            /*FXCollections.observableArrayList(albumNamesList);
            albumNamesList.set(albumNamesList.indexOf(selectedAlbumToRename), newName);*/


            initializeArrayPopulations();

            selectedAlbumToRename = null; // Reset selected album name
            renameAlbumField.clear(); // Clear the text field
        }
    }

    private boolean isNameExists(String name) {
        for (Album album : albums) {
            if (name.equalsIgnoreCase(album.getAlbumName())) {
                return true;
            }
        }
        return false;
    }

}
