package dev.jolvera.finjav.viewModels;

import dev.jolvera.finjav.models.User;
import dev.jolvera.finjav.services.interfaces.UserService;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MainViewModel extends BaseViewModel {
    private UserService userService;

    private ObjectProperty<User> activeUser = new SimpleObjectProperty<>();

    public ObjectProperty<User> activeUserProperty() { return activeUser; }

    @Inject
    public MainViewModel(UserService userService) {
        this.userService = userService;
    }

}
