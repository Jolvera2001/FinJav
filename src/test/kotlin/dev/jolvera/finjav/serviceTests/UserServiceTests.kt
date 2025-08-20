package dev.jolvera.finjav.serviceTests

import dev.jolvera.finjav.models.User
import dev.jolvera.finjav.services.interfaces.IUserService
import dev.jolvera.finjav.tables.Recurrences
import dev.jolvera.finjav.tables.Users
import dev.jolvera.finjav.testModules.SqliteMemTestModule
import dev.jolvera.finjav.utils.PasswordUtils
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import java.time.LocalDate
import java.util.*

class UserServiceTests : FunSpec({
    beforeSpec {
        startKoin { modules(SqliteMemTestModule) }
    }

    afterSpec {
        stopKoin()
    }

    beforeEach {
        val database = GlobalContext.get().get<Database>()
        transaction(database) {
            SchemaUtils.drop(Users, Recurrences)
            SchemaUtils.create(Users, Recurrences)
        }
    }

    test("should register user") {
        val userService = GlobalContext.get().get<IUserService>()
        val user = User(
            id = UUID.randomUUID(),
            dateCreated = LocalDate.now(),
            dateModified = LocalDate.now(),
            name = "John Goat",
            email = "JGoat@steds.edu",
            passwordHash = PasswordUtils.hashPassword("SecretGoatPassword")
        )

        userService.Register(user)
        val foundUser = userService.FindById(user.id)

        foundUser shouldNotBe null
        foundUser?.id shouldBe user.id
        foundUser?.name shouldBe user.name
        foundUser?.email shouldBe user.email
    }

    test("should login user after registration") {
        val userService = GlobalContext.get().get<IUserService>()
        val pass = "SecretGoatPassword"
        val user = User(
            id = UUID.randomUUID(),
            dateCreated = LocalDate.now(),
            dateModified = LocalDate.now(),
            name = "John Goat",
            email = "JGoat@steds.edu",
            passwordHash = PasswordUtils.hashPassword(pass)
        )

        userService.Register(user)
        val loggedInUser = userService.Login(user.name, pass)

        loggedInUser shouldNotBe null
        loggedInUser?.id shouldBe user.id
        loggedInUser?.name shouldBe user.name
        loggedInUser?.email shouldBe user.email
    }
})