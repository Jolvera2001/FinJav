package dev.jolvera.finjav.services

import dev.jolvera.finjav.models.User
import dev.jolvera.finjav.services.interfaces.IUserService
import dev.jolvera.finjav.utils.PasswordUtils
import org.jdbi.v3.core.Jdbi
import java.util.UUID

class UserService(private val jdbi: Jdbi) : IUserService {

    override fun FindById(id: UUID): User? {
        return jdbi.withHandle<User?, Exception> { handle ->
            handle.createQuery("""
                SELECT * 
                FROM users
                WHERE id = :id
            """.trimIndent())
                .bind("id", id)
                .mapToBean(User::class.java)
                .findFirst()
                .orElse(null)
        }
    }

    override fun Login(name: String, password: String): User? {
        val user = jdbi.withHandle<User?, Exception> { handle ->
            handle.createQuery(
                """
                    SELECT id, date_created, date_modified, name, email
                    FROM users
                    WHERE name = :name
                """.trimIndent())
                .bind("name", name)
                .mapToBean(User::class.java)
                .findFirst()
                .orElse(null)
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
            handle.createUpdate(
                """
                INSERT INTO users (id, date_created, date_modified, name, email, password_hash) 
                VALUES (?, ?, ?, ?, ?, ?)
            """.trimIndent()
            )
                .bind(0, user.id)
                .bind(1, user.dateCreated)
                .bind(2, user.dateUpdated)
                .bind(3, user.name)
                .bind(4, user.email)
                .bind(5, user.passwordHash)
                .execute()
        }
    }
}