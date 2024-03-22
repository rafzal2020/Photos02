package photos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    void handleAddAlbum(ActionEvent event) {
        ImageView albumIcon = new ImageView(new Image("/photos/models/folder-regular.png"));
        Label albumName = new Label("New Album");

        VBox buttonContainer = new VBox(albumIcon, albumName);
        buttonContainer.setAlignment(Pos.CENTER);

        Button newAlbumButton = new Button();
        newAlbumButton.setGraphic(buttonContainer);
        newAlbumButton.setPrefSize(100, 100);

        albumPane.getChildren().add(newAlbumButton);
    }

    @FXML
    void handleDeleteAlbum(ActionEvent event) {

    }

    @FXML
    void handleLogOut(ActionEvent event) {

    }

    @FXML
    void handleRenameAlbum(ActionEvent event) {

    }

}
