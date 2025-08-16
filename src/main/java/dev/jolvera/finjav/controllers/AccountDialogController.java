package dev.jolvera.finjav.controllers;

import dev.jolvera.finjav.viewModels.AccountDialogViewModel;
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
    private void Initialize() {

        setupHandlers();
    }

    private void setupHandlers() {
        LoginConfirmButton.setOnAction(e -> handleLogin());
        RegisterConfirmButton.setOnAction(e -> handleRegister());
    }

    private void handleLogin() {

    }
    private void handleRegister() {}
}
