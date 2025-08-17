package dev.jolvera.finjav.controllers;

import dev.jolvera.finjav.models.User;
import dev.jolvera.finjav.viewModels.MainViewModel;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.util.Optional;

public class MainPageController {
    @FXML private TextField SearchField;
    @FXML private Button AddRecurrenceButton;
    @FXML private Button RefreshButton;

    private MainViewModel viewModel;

    @Inject
    public MainPageController(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void Initialize() {
        setupHandlers();
        viewModel.activeUserProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                handleOpenAccountDialog();
            } else {
                System.out.println("User already logged in somehow idk");
            }
        });
    }

    private void setupHandlers() {
        AddRecurrenceButton.setOnAction(e -> System.out.println("addRecurrence pressed"));
        RefreshButton.setOnAction(e -> System.out.println("refresh pressed"));
    }

    private void handleOpenAccountDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("account-dialog.fxml"));
            DialogPane dialogPane = loader.load();
            AccountDialogController controller = loader.getController();

            Dialog<User> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Login/Register Account");

            dialog.setResultConverter(buttonType -> controller.getResult());

            Optional<User> result = dialog.showAndWait();

            if (result.isPresent()) {
                User user = result.get();
                viewModel.activeUserProperty().setValue(user);
            } else {
                System.out.println("Account dialog was cancelled");
            }
        } catch  (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
