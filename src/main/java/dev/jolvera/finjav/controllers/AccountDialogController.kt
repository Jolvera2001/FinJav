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
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject

class AccountDialogController: KoinComponent {
    private lateinit var viewModel: AccountDialogViewModel

    @FXML lateinit var LoginNameInput: TextField
    @FXML lateinit var LoginPasswordInput: PasswordField
    @FXML lateinit var LoginConfirmButton: Button
    @FXML lateinit var LoginClearFieldsButton: Button

    @FXML lateinit var RegisterNameInput: TextField
    @FXML lateinit var RegisterEmailInput: TextField
    @FXML lateinit var RegisterPasswordInput: PasswordField
    @FXML lateinit var RegisterConfirmButton: Button
    @FXML lateinit var RegisterClearFieldsButton: Button

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
        LoginNameInput.textProperty().bindBidirectional(viewModel.loginNameProperty())
        LoginPasswordInput.textProperty().bindBidirectional(viewModel.loginPasswordProperty())

        RegisterNameInput.textProperty().bindBidirectional(viewModel.registerNameProperty())
        RegisterEmailInput.textProperty().bindBidirectional(viewModel.registerEmailProperty())
        RegisterPasswordInput.textProperty().bindBidirectional(viewModel.registerPasswordProperty())

        errorLabel.textProperty().bindBidirectional(viewModel.ErrorProperty())
        errorLabel.visibleProperty().bindBidirectional(viewModel.hasErrorProperty())

        LoginConfirmButton.disableProperty().bind(
            viewModel.IsLoadingProperty().or(
                LoginNameInput.textProperty().isEmpty.or(
                    LoginPasswordInput.textProperty().isEmpty
                )
            )
        )

        RegisterConfirmButton.disableProperty().bind(
            viewModel.IsLoadingProperty().or(
                RegisterNameInput.textProperty().isEmpty().or(
                    RegisterEmailInput.textProperty().isEmpty().or(
                        RegisterPasswordInput.textProperty().isEmpty()
                    )
                )
            )
        )

        viewModel.returnedUserProperty().addListener { _, _, newValue ->
            if (newValue != null) {
                this.result = newValue

                Platform.runLater {
                    val stage = LoginConfirmButton.scene.window as Stage
                    stage.close()
                }
            }
        }
    }

    private fun setupHandlers() {
        LoginConfirmButton.onAction = EventHandler { e: ActionEvent? -> handleLogin() }
        LoginClearFieldsButton.onAction = EventHandler { e: ActionEvent? -> handleClearLoginFields() }
        RegisterConfirmButton.onAction = EventHandler { e: ActionEvent? -> handleRegister() }
        RegisterClearFieldsButton.onAction = EventHandler { e: ActionEvent? -> handleClearRegisterFields() }
    }

    private fun handleLogin() { viewModel.attemptLogin()}
    private fun handleRegister() { viewModel.attemptRegister() }

    private fun handleClearLoginFields() {
        LoginNameInput.clear()
        LoginPasswordInput.clear()
    }
    private fun handleClearRegisterFields() {
        RegisterNameInput.clear()
        RegisterEmailInput.clear()
        RegisterPasswordInput.clear()
    }
}