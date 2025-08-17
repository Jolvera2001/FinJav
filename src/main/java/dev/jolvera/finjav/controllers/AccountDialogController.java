package dev.jolvera.finjav.controllers;

import dev.jolvera.finjav.models.User;
import dev.jolvera.finjav.viewModels.AccountDialogViewModel;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AccountDialogController {
    @FXML public TextField LoginUsernameInput;
    @FXML public PasswordField LoginPasswordInput;
    @FXML public Button LoginConfirmButton;
    @FXML public Button LoginClearFieldsButton;

    @FXML public TextField RegisterUsernameInput;
    @FXML public TextField RegisterEmailInput;
    @FXML public PasswordField RegisterPasswordInput;
    @FXML public Button RegisterConfirmButton;
    @FXML public Button RegisterClearFieldsButton;

    @FXML public Label errorLabel;

    private AccountDialogViewModel viewModel;
    private User result;

    @Inject
    public AccountDialogController(AccountDialogViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        setupBindings();
        setupHandlers();
    }

    public User getResult() {
        return this.result;
    }

    private void setupBindings() {
        LoginUsernameInput.textProperty().bindBidirectional(viewModel.loginUsernameProperty());
        LoginPasswordInput.textProperty().bindBidirectional(viewModel.loginPasswordProperty());

        RegisterUsernameInput.textProperty().bindBidirectional(viewModel.registerUsernameProperty());
        RegisterEmailInput.textProperty().bindBidirectional(viewModel.registerEmailProperty());
        RegisterPasswordInput.textProperty().bindBidirectional(viewModel.registerPasswordProperty());

        errorLabel.textProperty().bindBidirectional(viewModel.errorMessageProperty());
        errorLabel.visibleProperty().bindBidirectional(viewModel.hasErrorProperty());

        // Bind button states
        LoginConfirmButton.disableProperty().bind(
                viewModel.isLoadingProperty().or(
                        LoginUsernameInput.textProperty().isEmpty().or(
                                LoginPasswordInput.textProperty().isEmpty()
                        )
                )
        );

        RegisterConfirmButton.disableProperty().bind(
                viewModel.isLoadingProperty().or(
                        RegisterUsernameInput.textProperty().isEmpty().or(
                                RegisterEmailInput.textProperty().isEmpty().or(
                                        RegisterPasswordInput.textProperty().isEmpty()
                                )
                        )
                )
        );

        viewModel.userProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.result = newValue;

                Platform.runLater(() -> {
                    Stage stage = (Stage)LoginConfirmButton.getScene().getWindow();
                    stage.close();
                });
            }
        });
    }

    private void setupHandlers() {
        LoginConfirmButton.setOnAction(e -> handleLogin());
        LoginClearFieldsButton.setOnAction(e -> handleClearLoginFields());
        RegisterConfirmButton.setOnAction(e -> handleRegister());
        RegisterClearFieldsButton.setOnAction(e -> handleClearRegisterFields());
    }

    private void handleLogin() {
        viewModel.attemptLogin();
    }

    private void handleRegister() {
        viewModel.attemptRegister();
    }

    private void handleClearLoginFields() {
        LoginUsernameInput.clear();
        LoginPasswordInput.clear();
    }

    private void handleClearRegisterFields() {
        RegisterUsernameInput.clear();
        RegisterPasswordInput.clear();
        RegisterPasswordInput.clear();
    }

    private void closeDialog() {
        Stage stage = (Stage) LoginConfirmButton.getScene().getWindow();
        stage.close();
    }
}
