package dev.jolvera.finjav.controllers;

import dev.jolvera.finjav.viewModels.AccountDialogViewModel;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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


    @FXML
    @Inject
    private void Initialize(AccountDialogViewModel viewModel) {
        this.viewModel = viewModel;
        setupBindings();
        setupHandlers();
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
    }

    private void setupHandlers() {
        LoginConfirmButton.setOnAction(e -> handleLogin());
        RegisterConfirmButton.setOnAction(e -> handleRegister());
    }

    private void handleLogin() {
        viewModel.attemptLogin();
    }
    private void handleRegister() {
        viewModel.attemptRegister();
    }
}
