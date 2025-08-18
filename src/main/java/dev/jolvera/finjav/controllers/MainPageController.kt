package dev.jolvera.finjav.controllers

import dev.jolvera.finjav.FinJavApp
import dev.jolvera.finjav.models.User
import dev.jolvera.finjav.viewModels.MainViewModel
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Button
import javafx.scene.control.Dialog
import javafx.scene.control.DialogPane
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import java.util.Optional

class MainPageController: KoinComponent {
    private lateinit var viewModel: MainViewModel

    @FXML private lateinit var SearchField: TextField
    @FXML private lateinit var AddRecurrenceButton: Button
    @FXML private lateinit var RefreshButton: Button
    @FXML private lateinit var AccountMenuButton: MenuItem
    @FXML private lateinit var CurrentUserDisplay: Label

    @FXML
    private fun initialize() {
        println("starting initialization")
        viewModel = get<MainViewModel>()
        setupHandlers()
        setupListeners()
        println("Initialized!")

        Platform.runLater {
            handleOpenAccountDialog()
        }
    }

    private fun setupListeners() {
        println("setup listeners touched!")
        viewModel.activeUserProperty().addListener { _, _, newUser ->
            if (newUser == null) {
                println("User is null!")
                CurrentUserDisplay.text = "No User"
                AccountMenuButton.text = "Log In"
                AccountMenuButton.onAction = EventHandler { e -> handleOpenAccountDialog() }
            } else {
                println("User logged in!")
                CurrentUserDisplay.text = "User: " + newUser.name
                AccountMenuButton.text = "Log out"
                AccountMenuButton.onAction = EventHandler { e: ActionEvent? -> logOut() }
            }
        }
    }

    private fun setupHandlers() {
        AddRecurrenceButton.onAction = EventHandler { e -> println("AddRecurrenceButton pressed!") }
        RefreshButton.onAction = EventHandler { e -> println("RefreshButton pressed!") }
    }

    private fun logOut() { viewModel.activeUserProperty().set(null) }

    private fun handleOpenAccountDialog() {
        try {
            val loader = FXMLLoader(FinJavApp::class.java.getResource("account-dialog.fxml"))
            val dialogController: AccountDialogController by inject()
            loader.setController(dialogController)
            val dialogPane = loader.load() as DialogPane

            val dialog = Dialog<User>()
            dialog.dialogPane = dialogPane
            dialog.title = "Login/Register Account"

            val result: Optional<User?> = dialog.showAndWait()

            if (result.isPresent) {
                viewModel.activeUserProperty().set(result.get())
            } else {
                val controllerResult = dialogController.getResult()
                viewModel.activeUserProperty().set(controllerResult)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}