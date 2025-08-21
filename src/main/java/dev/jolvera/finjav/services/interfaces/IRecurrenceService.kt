package dev.jolvera.finjav.services.interfaces

import dev.jolvera.finjav.models.Recurrence
import java.util.UUID

interface IRecurrenceService {
    fun getUsersRecurrences(userId: UUID): List<Recurrence>
    fun addRecurrence(recurrence: Recurrence)
    fun updateRecurrence(recurrence: Recurrence)
    fun deleteRecurrence(id: UUID)
}