package dev.jolvera.finjav.tables

import dev.jolvera.finjav.models.User
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.UUID

object Users: UUIDTable("users") {
    val dateCreated = date("date_created")
    val dateModified = date("date_modified")
    val name = varchar("name", 50)
    val email = varchar("email", 50)
    val passwordHash = varchar("password_hash", 64)
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(Users)

    var dateCreated by Users.dateCreated
    var dateModified by Users.dateModified
    var name by Users.name
    var email by Users.email
    var passwordHash by Users.passwordHash

    override fun toString() = "User(id=$id, dateCreated=$dateCreated, dateModified=$dateModified, name=$name, email=$email, passwordHash=$passwordHash)"

    fun toUser(): User {
        return User(
            id = this.id.value,
            dateCreated = this.dateCreated,
            dateModified = this.dateModified,
            name = this.name,
            email = this.email,
            passwordHash = this.passwordHash
        )
    }
}