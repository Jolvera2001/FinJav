package dev.jolvera.finjav.controllers

import dev.jolvera.finjav.viewModels.MainViewModel
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField

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

    }
}