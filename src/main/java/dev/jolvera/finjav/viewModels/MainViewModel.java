package dev.jolvera.finjav.viewModels;

import dev.jolvera.finjav.models.Recurrence;
import dev.jolvera.finjav.models.User;
import dev.jolvera.finjav.services.interfaces.UserService;
import jakarta.inject.Inject;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class MainViewModel {
    private UserService userService;

    private ObjectProperty<User> activeUser = new SimpleObjectProperty<>();
    private ListProperty<Recurrence> recurrences = new SimpleListProperty<>(FXCollections.observableArrayList());

    @Inject
    public MainViewModel(UserService userService) {
        this.userService = userService;
    }
}
