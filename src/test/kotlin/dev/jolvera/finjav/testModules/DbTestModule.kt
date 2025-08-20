package dev.jolvera.finjav.testModules

import dev.jolvera.finjav.tables.Recurrences
import dev.jolvera.finjav.tables.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module

val SqliteMemTestModule = module {
    single<Database> {
        Database.connect("jdbc:sqlite::memory:", driver = "org.sqlite.JDBC").also { db ->
            transaction(db) {
                SchemaUtils.create(Users, Recurrences)
            }
        }
    }
}