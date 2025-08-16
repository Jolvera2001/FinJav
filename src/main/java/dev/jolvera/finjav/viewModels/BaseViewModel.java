package dev.jolvera.finjav.viewModels;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BaseViewModel {
    protected final BooleanProperty isLoading = new SimpleBooleanProperty(false);
    protected final StringProperty errorMessage = new SimpleStringProperty("");

    public BooleanProperty isLoadingProperty() { return isLoading; }
    public StringProperty errorMessageProperty() { return errorMessage; }

    protected <T> void executeAsync(Supplier<T> supplier, Consumer<T> uiUpdate) {
        isLoading.set(true);
        errorMessage.set("");

        Thread.ofVirtual().start(() -> {
           try {
                T result = supplier.get();
                Platform.runLater(() -> {
                    uiUpdate.accept(result);
                    isLoading.set(false);
                });
           } catch (Exception e) {
                Platform.runLater(() -> {
                   errorMessage.set(e.getMessage());
                   isLoading.set(false);
                });
           }
        });
    }
}
