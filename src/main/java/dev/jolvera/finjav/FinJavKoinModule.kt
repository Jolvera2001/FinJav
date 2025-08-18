package dev.jolvera.finjav

import dev.jolvera.finjav.controllers.AccountDialogController
import dev.jolvera.finjav.controllers.MainPageController
import dev.jolvera.finjav.services.UserService
import dev.jolvera.finjav.services.interfaces.IUserService
import dev.jolvera.finjav.viewModels.AccountDialogViewModel
import dev.jolvera.finjav.viewModels.MainViewModel
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.koin.dsl.module

val FinJavKoinModule = module {
    single<Jdbi> {
        val jdbi = Jdbi.create("jdbc:sqlite:finjav.sqlite")
            .installPlugin(KotlinPlugin())
            .installPlugin(KotlinSqlObjectPlugin())

        jdbi.withHandle<Unit, RuntimeException> { handle ->
                handle.execute(
                    """
            CREATE TABLE IF NOT EXISTS users (
                id TEXT NOT NULL PRIMARY KEY,
                date_created DATETIME NOT NULL,
                date_Modified DATETIME NOT NULL,
                name TEXT NOT NULL,
                email TEXT NOT NULL,
                password_hash TEXT NOT NULL
                )""".trimIndent())

                handle.execute(
                    """
            CREATE TABLE IF NOT EXISTS recurrences (
                id TEXT NOT NULL PRIMARY KEY,
                user_id TEXT NOT NULL,
                date_created DATETIME NOT NULL,
                date_modified DATETIME NOT NULL,
                name TEXT NOT NULL,
                is_income BOOLEAN NOT NULL,
                recurring_date DATETIME NOT NULL,
                FOREIGN KEY (user_id) REFERENCES users(id)
                )""".trimIndent())
            }
        jdbi
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