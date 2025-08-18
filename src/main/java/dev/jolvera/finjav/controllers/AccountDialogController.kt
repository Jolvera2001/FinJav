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

class AccountDialogController(private val viewModel: AccountDialogViewModel) {
    @FXML var LoginNameInput: TextField? = null
    @FXML var LoginPasswordInput: PasswordField? = null
    @FXML var LoginConfirmButton: Button? = null
    @FXML var LoginClearFieldsButton: Button? = null

    @FXML var RegisterNameInput: TextField? = null
    @FXML var RegisterEmailInput: TextField? = null
    @FXML var RegisterPasswordInput: PasswordField? = null
    @FXML var RegisterConfirmButton: Button? = null
    @FXML var RegisterClearFieldsButton: Button? = null

    @FXML var errorLabel: Label? = null

    private lateinit var result: User
    fun getResult(): User { return this.result }

    @FXML
    private fun initialize() {
        setupBindings()
        setupHandlers()
    }

    private fun setupBindings() {
        LoginNameInput!!.textProperty().bindBidirectional(viewModel.loginNameProperty())
        LoginPasswordInput!!.textProperty().bindBidirectional(viewModel.loginPasswordProperty())

        RegisterNameInput!!.textProperty().bindBidirectional(viewModel.registerNameProperty())
        RegisterEmailInput!!.textProperty().bindBidirectional(viewModel.registerEmailProperty())
        RegisterPasswordInput!!.textProperty().bindBidirectional(viewModel.registerPasswordProperty())

        errorLabel!!.textProperty().bindBidirectional(viewModel.ErrorProperty())
        errorLabel!!.visibleProperty().bindBidirectional(viewModel.hasErrorProperty())

        LoginConfirmButton!!.disableProperty().bind(
            viewModel.IsLoadingProperty().or(
                LoginNameInput!!.textProperty().isEmpty.or(
                    LoginPasswordInput!!.textProperty().isEmpty
                )
            )
        )

        RegisterConfirmButton!!.disableProperty().bind(
            viewModel.IsLoadingProperty().or(
                RegisterNameInput!!.textProperty().isEmpty().or(
                    RegisterEmailInput!!.textProperty().isEmpty().or(
                        RegisterPasswordInput!!.textProperty().isEmpty()
                    )
                )
            )
        )

        viewModel.returnedUserProperty().addListener { _, _, newValue ->
            if (newValue != null) {
                this.result = newValue

                Platform.runLater(Runnable {
                    val stage = LoginConfirmButton!!.scene.window as Stage
                    stage.close()
                })
            }
        }
    }

    private fun setupHandlers() {
        LoginConfirmButton!!.onAction = EventHandler { e: ActionEvent? -> handleLogin() }
        LoginClearFieldsButton!!.onAction = EventHandler { e: ActionEvent? -> handleClearLoginFields() }
        RegisterConfirmButton!!.onAction = EventHandler { e: ActionEvent? -> handleRegister() }
        RegisterClearFieldsButton!!.onAction = EventHandler { e: ActionEvent? -> handleClearRegisterFields() }
    }

    private fun handleLogin() { viewModel.attemptLogin()}
    private fun handleRegister() { viewModel.attemptRegister() }

    private fun handleClearLoginFields() {
        LoginNameInput!!.clear()
        LoginPasswordInput!!.clear()
    }
    private fun handleClearRegisterFields() {
        RegisterNameInput!!.clear()
        RegisterEmailInput!!.clear()
        RegisterPasswordInput!!.clear()
    }


}