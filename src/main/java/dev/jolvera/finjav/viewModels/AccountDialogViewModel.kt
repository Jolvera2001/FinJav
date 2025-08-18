package dev.jolvera.finjav.viewModels

import dev.jolvera.finjav.models.User
import dev.jolvera.finjav.services.interfaces.IUserService
import dev.jolvera.finjav.utils.PasswordUtils
import javafx.beans.InvalidationListener
import javafx.beans.property.BooleanProperty
import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import java.time.LocalDate
import java.util.UUID

class AccountDialogViewModel(private val userService: IUserService) : BaseViewModel() {
    private val loginName = SimpleStringProperty("")
    private val loginPassword = SimpleStringProperty("")

    private val registerName = SimpleStringProperty("")
    private val registerEmail = SimpleStringProperty("")
    private val registerPassword = SimpleStringProperty("")

    private val hasError = SimpleBooleanProperty(false)
    private val returnedUser = SimpleObjectProperty<User>()

    fun loginNameProperty(): StringProperty = loginName
    fun loginPasswordProperty(): StringProperty = loginPassword

    fun registerNameProperty(): StringProperty = registerName
    fun registerEmailProperty(): StringProperty = registerEmail
    fun registerPasswordProperty(): StringProperty = registerPassword

    fun hasErrorProperty(): BooleanProperty = hasError
    fun returnedUserProperty(): Property<User> = returnedUser

    fun attemptLogin() {
        if (loginName.get() == null || loginPassword.get() == null) {
            showError("One or more login fields are empty")
            return;
        }

        executeAsync(
            backgroundWork = {
                userService.Login(
                    loginName.get(),
                    loginPassword.get()
                )
            },
            uiUpdate = { user ->
                if (user == null) {
                    showError("User not found")
                } else {
                    returnedUser.set(user)
                }
            }
        )
    }

    fun attemptRegister() {
        if (registerName.get() == null || registerEmail.get() == null || registerPassword.get() == null) {
            showError("One or more register fields are empty")
        }

        executeAsync(
            backgroundWork = {
                val newUser = User(
                    UUID.randomUUID(),
                    LocalDate.now(),
                    LocalDate.now(),
                    registerName.get(),
                    registerEmail.get(),
                    PasswordUtils.hashPassword(registerPassword.get()),
                )
                userService.Register(newUser)
                userService.FindById(newUser.id!!)
            },
            uiUpdate = { user ->
                if (user == null) {
                    showError("Error with registration")
                } else {
                    returnedUser.set(user)
                }
            }
        )
    }

    private fun setupErrorClearing() {
        val clearErrors = InvalidationListener { clearError() }
        loginName.addListener(clearErrors)
        loginPassword.addListener(clearErrors)
        registerName.addListener(clearErrors)
        registerEmail.addListener(clearErrors)
        registerPassword.addListener(clearErrors)
    }

    private fun clearError() {
        errorMessage.set("")
        hasError.set(false)
    }

    private fun showError(message: String) {
        errorMessage.set(message)
        hasError.set(true)
    }


}