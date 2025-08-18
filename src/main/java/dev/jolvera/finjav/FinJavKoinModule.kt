package dev.jolvera.finjav

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

    // factory<ViewModel> { MainViewModel(get()) } <-- get() for any other dependencies
    // controllers and services can also be a factory
}