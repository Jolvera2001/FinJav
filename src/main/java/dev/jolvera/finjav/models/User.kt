package dev.jolvera.finjav.models

import java.time.LocalDate
import java.util.UUID

data class User(
    val id: UUID?,
    val dateCreated: LocalDate,
    val dateUpdated: LocalDate,
    val name: String,
    val email: String,
    val passwordHash: String?
)
