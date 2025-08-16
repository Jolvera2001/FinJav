package dev.jolvera.finjav.viewModels;

import dev.jolvera.finjav.models.User;
import dev.jolvera.finjav.models.dtos.UserDto;
import dev.jolvera.finjav.services.interfaces.UserService;
import dev.jolvera.finjav.utils.PasswordUtils;
import jakarta.inject.Inject;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class AccountDialogViewModel extends BaseViewModel {
    private final UserService userService;

    // login
    private final StringProperty loginUsername = new SimpleStringProperty("");
    private final StringProperty loginPassword = new SimpleStringProperty("");

    // register
    private final StringProperty registerUsername = new SimpleStringProperty("");
    private final StringProperty registerEmail = new SimpleStringProperty("");
    private final StringProperty registerPassword = new SimpleStringProperty("");

    public StringProperty loginUsernameProperty() { return loginUsername; }
    public StringProperty loginPasswordProperty() { return loginPassword; }

    public StringProperty registerUsernameProperty() { return registerUsername; }
    public StringProperty registerEmailProperty() { return registerEmail; }
    public StringProperty registerPasswordProperty() { return registerPassword; }

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
    }

    private void showError(String message) {
        errorMessage.set(message);
    }

    public void attemptLogin() {
        if (loginUsername.get() == null || loginPassword.get() == null) {
            showError("One or more login fields are empty");
            return;
        }

        executeAsync(
                () -> {
                    return userService.Login(loginUsername.get(), loginPassword.get());
                },
                user -> {
                    if (user == null) {
                        showError("Login failed");
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
                    var dto = new UserDto(
                            UUID.randomUUID(),
                            registerUsername.get(),
                            registerEmail.get(),
                            PasswordUtils.HashPassword(registerPassword.get())
                    );
                    return userService.Register(dto);
                },
                user -> {
                    if (user == null) {
                        showError("Register failed");
                    }
                }
        );
    }
}
