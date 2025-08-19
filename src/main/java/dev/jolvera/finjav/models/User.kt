package dev.jolvera.finjav.models

import java.time.LocalDate
import java.util.UUID

data class User(
    val id: UUID?,
    val dateCreated: LocalDate,
    val dateModified: LocalDate,
    val name: String,
    val email: String,
    val passwordHash: String
) {
    fun withoutPasswordHash(): User {
        return this.copy(
            id = id,
            dateCreated = dateCreated,
            dateModified = dateModified,
            name = name,
            email = email
        )
    }
}
