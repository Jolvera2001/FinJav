package dev.jolvera.finjav.services

import dev.jolvera.finjav.models.Recurrence
import dev.jolvera.finjav.services.interfaces.IRecurrenceService
import org.jetbrains.exposed.sql.Database
import java.util.UUID

class RecurrenceService(private val database: Database): IRecurrenceService {
    override fun getUsersRecurrences(userId: UUID): List<Recurrence> {
        TODO("Not yet implemented")
    }

    override fun addRecurrence(recurrence: Recurrence) {
        TODO("Not yet implemented")
    }

    override fun updateRecurrence(recurrence: Recurrence) {
        TODO("Not yet implemented")
    }

    override fun deleteRecurrence(id: UUID) {
        TODO("Not yet implemented")
    }

}