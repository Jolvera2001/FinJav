package dev.jolvera.finjav.services

import dev.jolvera.finjav.models.User
import dev.jolvera.finjav.services.interfaces.IUserService
import dev.jolvera.finjav.tables.UserEntity
import dev.jolvera.finjav.tables.Users
import dev.jolvera.finjav.utils.PasswordUtils
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserService(private val database: Database) : IUserService {

    override fun FindById(id: UUID): User? = transaction(database) {
        UserEntity.findById(id)?.toUser();
    }

    override fun Login(name: String, password: String): User?  {

        require(name.isNotBlank() && password.isNotBlank()) { "Username and password must not be blank" }

        return transaction(database) {
            val userEntity = UserEntity.find { Users.name eq name }.singleOrNull()

            if (userEntity == null) {
                return@transaction null;
            }

            val user = userEntity.toUser();
            if (PasswordUtils.verifyPassword(password, user.passwordHash)) {
                user.withoutPasswordHash()
            } else {
                null
            }
        }
    }

    override fun Register(user: User) {

        require(user.name.isNotBlank()) { "User name cannot be blank" }
        require(user.email.isNotBlank()) { "User email cannot be blank" }
        require(user.passwordHash.isNotBlank()) { "User password cannot be blank" }

        transaction(database) {
            UserEntity.new(user.id) {
                dateCreated = user.dateCreated
                dateModified = user.dateModified
                name = user.name
                email = user.email
                passwordHash = user.passwordHash
            }
        }
    }
}