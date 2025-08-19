package dev.jolvera.finjav.models

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class Recurrence(
    val id: UUID?,
    val userId: UUID?,
    val dateCreated: LocalDate,
    val dateModified: LocalDate,
    val startDate: LocalDate,
    val name: String,
    val amount: BigDecimal,
    val isIncome: Boolean,
    val recurringDate: LocalDate,
)
