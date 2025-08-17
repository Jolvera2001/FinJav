package dev.jolvera.finjav;

import dagger.Component;
import dev.jolvera.finjav.controllers.AccountDialogController;
import dev.jolvera.finjav.controllers.MainPageController;
import jakarta.inject.Singleton;

@Singleton
@Component(modules = {FinJavModule.class})
public interface FinJavComponent {
    MainPageController mainPageController();
    AccountDialogController accountDialogController();
    FinJavComponent self();
}
