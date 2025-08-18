package dev.jolvera.finjav.services

import dev.jolvera.finjav.daos.UserDao
import dev.jolvera.finjav.models.User
import dev.jolvera.finjav.services.interfaces.IUserService
import dev.jolvera.finjav.utils.PasswordUtils
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.attach
import java.util.UUID

class UserService(private val jdbi: Jdbi) : IUserService {

    override fun FindById(id: UUID): User? {
        return jdbi.withHandle<User?, Exception> { handle ->
            handle.attach(UserDao::class).findById(id)
        }
    }

    override fun Login(username: String, password: String): User? {
        val user = jdbi.withHandle<User?, Exception> { handle ->
            handle.attach(UserDao::class).findByName(username)
        }

        if (user == null) {
            return null
        }

        return if (PasswordUtils.verifyPassword(password, user.passwordHash!!)) {
            user
        } else {
            null
        }
    }

    override fun Register(user: User) {
        jdbi.withHandle<Unit, Exception> { handle ->
            handle.attach(UserDao::class).createUser(user)
        }
    }
}