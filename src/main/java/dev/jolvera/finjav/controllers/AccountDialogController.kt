package dev.jolvera.finjav.controllers

import dev.jolvera.finjav.models.User
import dev.jolvera.finjav.viewModels.AccountDialogViewModel
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class AccountDialogController: KoinComponent {
    private lateinit var viewModel: AccountDialogViewModel

    @FXML lateinit var loginNameInput: TextField
    @FXML lateinit var loginPasswordInput: PasswordField
    @FXML lateinit var loginConfirmButton: Button
    @FXML lateinit var loginClearFieldsButton: Button

    @FXML lateinit var registerNameInput: TextField
    @FXML lateinit var registerEmailInput: TextField
    @FXML lateinit var registerPasswordInput: PasswordField
    @FXML lateinit var registerConfirmButton: Button
    @FXML lateinit var registerClearFieldsButton: Button

    @FXML lateinit var errorLabel: Label

    private lateinit var result: User
    fun getResult(): User { return this.result }

    @FXML
    private fun initialize() {
        viewModel = get<AccountDialogViewModel>()
        setupBindings()
        setupHandlers()
    }

    private fun setupBindings() {
        loginNameInput.textProperty().bindBidirectional(viewModel.loginNameProperty())
        loginPasswordInput.textProperty().bindBidirectional(viewModel.loginPasswordProperty())

        registerNameInput.textProperty().bindBidirectional(viewModel.registerNameProperty())
        registerEmailInput.textProperty().bindBidirectional(viewModel.registerEmailProperty())
        registerPasswordInput.textProperty().bindBidirectional(viewModel.registerPasswordProperty())

        errorLabel.textProperty().bindBidirectional(viewModel.ErrorProperty())
        errorLabel.visibleProperty().bindBidirectional(viewModel.hasErrorProperty())

        loginConfirmButton.disableProperty().bind(
            viewModel.IsLoadingProperty().or(
                loginNameInput.textProperty().isEmpty.or(
                    loginPasswordInput.textProperty().isEmpty
                )
            )
        )

        registerConfirmButton.disableProperty().bind(
            viewModel.IsLoadingProperty().or(
                registerNameInput.textProperty().isEmpty().or(
                    registerEmailInput.textProperty().isEmpty().or(
                        registerPasswordInput.textProperty().isEmpty()
                    )
                )
            )
        )

        viewModel.returnedUserProperty().addListener { _, _, newValue ->
            if (newValue != null) {
                this.result = newValue

                Platform.runLater {
                    val stage = loginConfirmButton.scene.window as Stage
                    stage.close()
                }
            }
        }
    }

    private fun setupHandlers() {
        loginConfirmButton.onAction = EventHandler { e: ActionEvent? -> handleLogin() }
        loginClearFieldsButton.onAction = EventHandler { e: ActionEvent? -> handleClearLoginFields() }
        registerConfirmButton.onAction = EventHandler { e: ActionEvent? -> handleRegister() }
        registerClearFieldsButton.onAction = EventHandler { e: ActionEvent? -> handleClearRegisterFields() }
    }

    private fun handleLogin() { viewModel.attemptLogin()}
    private fun handleRegister() { viewModel.attemptRegister() }

    private fun handleClearLoginFields() {
        loginNameInput.clear()
        loginPasswordInput.clear()
    }
    private fun handleClearRegisterFields() {
        registerNameInput.clear()
        registerEmailInput.clear()
        registerPasswordInput.clear()
    }
}