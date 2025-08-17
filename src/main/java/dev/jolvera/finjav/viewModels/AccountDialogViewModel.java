package dev.jolvera.finjav.viewModels;

import dev.jolvera.finjav.models.User;
import dev.jolvera.finjav.services.interfaces.UserService;
import dev.jolvera.finjav.utils.PasswordUtils;
import jakarta.inject.Inject;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.UUID;

public class AccountDialogViewModel extends BaseViewModel {
    private final UserService userService;

    // login
    private final StringProperty loginUsername = new SimpleStringProperty("");
    private final StringProperty loginPassword = new SimpleStringProperty("");

    // register
    private final StringProperty registerUsername = new SimpleStringProperty("");
    private final StringProperty registerEmail = new SimpleStringProperty("");
    private final StringProperty registerPassword = new SimpleStringProperty("");

    private final BooleanProperty hasError = new SimpleBooleanProperty(false);
    private final Property<User> userProperty = new SimpleObjectProperty<>();

    public StringProperty loginUsernameProperty() { return loginUsername; }
    public StringProperty loginPasswordProperty() { return loginPassword; }

    public StringProperty registerUsernameProperty() { return registerUsername; }
    public StringProperty registerEmailProperty() { return registerEmail; }
    public StringProperty registerPasswordProperty() { return registerPassword; }

    public BooleanProperty hasErrorProperty() { return hasError; }
    public Property<User> userProperty() { return userProperty; }

    @Inject
    public AccountDialogViewModel(UserService userService) {
        this.userService = userService;
        setupErrorClearing();
    }

    private void setupErrorClearing() {
        InvalidationListener clearErrors = obs -> clearError();
        loginUsername.addListener(clearErrors);
        loginPassword.addListener(clearErrors);
        registerUsername.addListener(clearErrors);
        registerEmail.addListener(clearErrors);
        registerPassword.addListener(clearErrors);
    }

    private void clearError() {
        errorMessage.set("");
        hasError.set(false);
    }

    private void showError(String message) {
        errorMessage.set(message);
        hasError.set(true);
    }

    public void attemptLogin() {
        if (loginUsername.get() == null || loginPassword.get() == null) {
            showError("One or more login fields are empty");
            return;
        }

        executeAsync(
                () -> userService.Login(loginUsername.get(), loginPassword.get()),
                user -> {
                    if (user == null) {
                        showError("Login failed");
                    } else {
                        userProperty.setValue(user);
                    }
                }
        );
    }

    public void attemptRegister() {
        if (registerUsername.get() == null || registerEmail.get() == null || registerPassword.get() == null) {
            showError("One or more register fields are empty");
        }

        executeAsync(
                () -> {
                    var user = new User(
                            UUID.randomUUID(),
                            LocalDate.now(),
                            LocalDate.now(),
                            registerUsername.get(),
                            registerEmail.get(),
                            PasswordUtils.HashPassword(registerPassword.get())
                    );
                    userService.Register(user);
                    return userService.FindById(user.getId());
                },
                user -> {
                    if (user.isEmpty()) {
                        showError("Register failed");
                    } else {
                        userProperty.setValue(user.get());
                    }
                }
        );
    }
}
