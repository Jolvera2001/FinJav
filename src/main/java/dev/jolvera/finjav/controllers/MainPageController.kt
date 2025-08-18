package dev.jolvera.finjav.controllers

import dev.jolvera.finjav.HelloApplication
import dev.jolvera.finjav.models.User
import dev.jolvera.finjav.viewModels.MainViewModel
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Button
import javafx.scene.control.Dialog
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import java.util.Optional

class MainPageController(private val viewModel: MainViewModel) {
    @FXML private val SearchField: TextField? = null
    @FXML private val AddRecurrenceButton: Button? = null
    @FXML private val RefreshButton: Button? = null
    @FXML private val AccountMenuButton: MenuItem? = null
    @FXML private val CurrentUserDisplay: Label? = null

    @FXML
    private fun initialize() {
        setupHandlers()
        setupListeners()
    }

    private fun setupListeners() {
        viewModel.activeUserProperty().addListener { _, _, newUser ->
            if (newUser == null) {
                CurrentUserDisplay!!.text = "No User"
                AccountMenuButton!!.text = "Log In"
                AccountMenuButton!!.onAction = EventHandler { e -> handleOpenAccountDialog() }
                handleOpenAccountDialog()
            } else {
                CurrentUserDisplay!!.text = "User: " + newUser.name
                AccountMenuButton!!.text = "Log out"
                AccountMenuButton.onAction = EventHandler { e: ActionEvent? -> logOut() }
            }
        }
    }

    private fun setupHandlers() {
        AddRecurrenceButton!!.onAction = EventHandler { e -> println("AddRecurrenceButton pressed!") }
        RefreshButton!!.onAction = EventHandler { e -> println("RefreshButton pressed!") }
    }

    private fun logOut() { viewModel.activeUserProperty().set(null) }

    private fun handleOpenAccountDialog() {
        try {
            val loader = FXMLLoader(HelloApplication::class.java.getResource("account-dialog.fxml"))
            // figure out how to get injected controller for dialog within koin
            // controller = koin get controller
            loader.setController(controller)
            val dialogPane = loader.load()

            val dialog = Dialog<User>()
            dialog.dialogPane = dialogPane
            dialog.title = "Login/Register Account"

            val result: Optional<User?> = dialog.showAndWait()

            if (result.isPresent) {
                viewModel.activeUserProperty().set(result.get())
            } else {
                val controllerResult = controller.getResult()
                if (controllerResult != null) {
                    viewModel.activeUserProperty().set(controllerResult)
                } else {
                    println("Account dialog was cancelled")
                }
            }
        } catch (ex: Exception) {
            println(ex.message)
        }
    }
}