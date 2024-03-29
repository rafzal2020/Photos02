package photos.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import photos.controller.LoginController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/login.fxml"));
        //loader.setController(new LoginController()); // Set the controller here
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, 335, 600));
        primaryStage.setTitle("Simple Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        // test
        launch(args);
    }
}