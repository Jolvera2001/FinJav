package dev.jolvera.finjav;

import dev.jolvera.finjav.controllers.MainPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private FinJavComponent finJavComponent;

    @Override
    public void start(Stage stage) throws IOException {
        this.finJavComponent = DaggerFinJavComponent.create();
        loadMainPage(stage);
    }

    private void loadMainPage(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-page-view.fxml"));

            MainPageController mainPageController = finJavComponent.mainPageController();
            fxmlLoader.setController(mainPageController);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1200, 800);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
