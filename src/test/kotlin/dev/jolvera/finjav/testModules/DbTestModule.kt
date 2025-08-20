package dev.jolvera.finjav.testModules

import dev.jolvera.finjav.services.UserService
import dev.jolvera.finjav.services.interfaces.IUserService
import dev.jolvera.finjav.tables.Recurrences
import dev.jolvera.finjav.tables.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module

val SqliteMemTestModule = module {
    single<Database> {
        val dbFile = "test_${System.currentTimeMillis()}.db"
        val db = Database.connect("jdbc:sqlite:$dbFile", driver = "org.sqlite.JDBC")
        transaction(db) {
                SchemaUtils.create(Users, Recurrences)
        }
        db
    }

    factory<IUserService> { UserService(get()) }
}