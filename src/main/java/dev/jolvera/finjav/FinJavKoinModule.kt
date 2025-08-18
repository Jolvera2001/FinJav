package dev.jolvera.finjav

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.koin.dsl.module

val FinJavKoinModule = module {
    single<Jdbi> {
        Jdbi.create("jdbc:sqlite:finjav.sqlite")
            .installPlugin(KotlinPlugin())
            .installPlugin(KotlinSqlObjectPlugin())
    }

    // factory<ViewModel> { MainViewModel(get()) } <-- get() for any other dependencies
    // controllers and services can also be a factory
}