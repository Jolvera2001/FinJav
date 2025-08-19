package dev.jolvera.finjav

import dev.jolvera.finjav.controllers.AccountDialogController
import dev.jolvera.finjav.controllers.MainPageController
import dev.jolvera.finjav.services.UserService
import dev.jolvera.finjav.services.interfaces.IUserService
import dev.jolvera.finjav.tables.Recurrences
import dev.jolvera.finjav.tables.Users
import dev.jolvera.finjav.viewModels.AccountDialogViewModel
import dev.jolvera.finjav.viewModels.MainViewModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.koin.dsl.module

val FinJavKoinModule = module {
    single<Database> {
        Database.connect(
            url = "jdbc:sqlite:finjav.db",
            driver = "org.sqlite.JDBC",
        ).also { _ ->
            SchemaUtils.create(Users, Recurrences)
        }
    }

    // services
    factory<IUserService> { UserService(get()) }

    // viewModels
    factory<MainViewModel> { MainViewModel() }
    factory<AccountDialogViewModel> { AccountDialogViewModel(get()) }

    // controllers
    factory<MainPageController> { MainPageController() }
    factory<AccountDialogController> { AccountDialogController() }
}