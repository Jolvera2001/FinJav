package dev.jolvera.finjav.viewModels

import dev.jolvera.finjav.models.User
import dev.jolvera.finjav.services.interfaces.IUserService
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty

class MainViewModel(private val userService: IUserService): BaseViewModel() {
    private val activeUser = SimpleObjectProperty<User>()

    fun activeUserProperty(): ObjectProperty<User> = activeUser
}