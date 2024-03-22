package photos.controller;

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
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Optional;

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
    private Button logOutBtn;

    @FXML
    private Label photoCountLabel;

    @FXML
    private Button renameAlbumButton;

    @FXML
    private Label userLabel;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    void initialize() {
        // Initialize event handler for each button in the albumPane
        for (Node node : albumPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setOnMouseClicked(event -> handleDeleteAlbum(event, button));
            }
        }
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
        albumPane.getChildren().add(newAlbumButton);
    }

    @FXML
    void handleDeleteAlbum(MouseEvent event, Button button) {
        if (event.getButton() == MouseButton.PRIMARY) {
            button.setStyle("-fx-background-color: lightblue;");
        }
    }

    @FXML
    void handleLogOut(ActionEvent event) {

    }

    @FXML
    void handleRenameAlbum(ActionEvent event) {

    }

}
