package dev.jolvera.finjav.controllers;

import dev.jolvera.finjav.FinJavComponent;
import dev.jolvera.finjav.HelloApplication;
import dev.jolvera.finjav.viewModels.MainViewModel;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.util.Optional;

public class MainPageController {
    @FXML private TextField SearchField;
    @FXML private Button AddRecurrenceButton;
    @FXML private Button RefreshButton;
    @FXML private MenuItem AccountMenuButton;
    @FXML private Label CurrentUserDisplay;

    private MainViewModel viewModel;
    private FinJavComponent component;

    @Inject
    public MainPageController(MainViewModel viewModel, FinJavComponent component) {
        this.viewModel = viewModel;
        this.component = component;
    }

    @FXML
    private void initialize() {
        setupHandlers();
        setupListeners();

        if (viewModel.activeUserProperty().getValue() == null) {
            Platform.runLater(this::handleOpenAccountDialog);
        }
    }

    private void setupHandlers() {
        AddRecurrenceButton.setOnAction(e -> System.out.println("addRecurrence pressed"));
        RefreshButton.setOnAction(e -> System.out.println("refresh pressed"));
    }

    private void setupListeners() {
        viewModel.activeUserProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                CurrentUserDisplay.setText("");
                AccountMenuButton.setText("Log in");
                AccountMenuButton.setOnAction(e -> handleOpenAccountDialog());
                handleOpenAccountDialog();
            } else {
                CurrentUserDisplay.setText("User: " + newValue.getName());
                AccountMenuButton.setText("Log out");
                AccountMenuButton.setOnAction(e -> logOut());
            }
        });
    }

    private void handleOpenAccountDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("account-dialog.fxml"));
            AccountDialogController controller = component.accountDialogController();
            loader.setController(controller);
            DialogPane dialogPane = loader.load();

            Dialog<User> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Login/Register Account");

            Optional<User> result = dialog.showAndWait();

            if (result.isPresent()) {
                User user = result.get();
                viewModel.activeUserProperty().setValue(user);
            } else {
                User controllerResult = controller.getResult();
                if (controllerResult != null) {
                    viewModel.activeUserProperty().setValue(controllerResult);
                } else {
                    System.out.println("Account dialog was cancelled");
                }
            }
        } catch  (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void logOut() {
        viewModel.activeUserProperty().setValue(null);
    }

}
