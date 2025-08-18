package dev.jolvera.finjav.models

import java.time.LocalDate
import java.util.UUID

data class Recurrence(
    val id: UUID?,
    val userId: UUID?,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val name: String,
    val isIncome: Boolean,
    val recurrenceDate: LocalDate,
)
