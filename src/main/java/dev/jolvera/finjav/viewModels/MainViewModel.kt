package dev.jolvera.finjav.viewModels

import dev.jolvera.finjav.models.User
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty

class MainViewModel(): BaseViewModel() {
    private val activeUser = SimpleObjectProperty<User?>(null)

    fun activeUserProperty(): ObjectProperty<User?> = activeUser
}