package dev.jolvera.finjav.services

import dev.jolvera.finjav.models.Recurrence
import dev.jolvera.finjav.services.interfaces.IRecurrenceService
import dev.jolvera.finjav.tables.RecurrenceEntity
import dev.jolvera.finjav.tables.Recurrences
import dev.jolvera.finjav.tables.UserEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class RecurrenceService(private val database: Database) : IRecurrenceService {
    override fun getUsersRecurrences(userId: UUID): List<Recurrence> = transaction(database) {
        RecurrenceEntity.find { Recurrences.user eq userId }.map { it.toRecurrence() }
    }

    override fun addRecurrence(recurrence: Recurrence) {
        require(recurrence.name.isNotBlank()) { "Recurrence name must not be blank." }

        transaction(database) {
            RecurrenceEntity.new(recurrence.id) {
                user = UserEntity[recurrence.userId]
                dateCreated = recurrence.dateCreated
                dateModified = recurrence.dateModified
                name = recurrence.name
                amount = recurrence.amount
                isIncome = recurrence.isIncome
                recurringDate = recurrence.recurringDate
            }
        }
    }

    override fun updateRecurrence(recurrence: Recurrence) {
        require(recurrence.name.isNotBlank()) { "Recurrence name must not be blank." }

        transaction(database) {
            RecurrenceEntity.findByIdAndUpdate(recurrence.id) {
                it.dateModified = recurrence.dateModified
                it.name = recurrence.name
                it.amount = recurrence.amount
                it.isIncome = recurrence.isIncome
                it.recurringDate = recurrence.recurringDate
            }
        }
    }

    override fun deleteRecurrence(id: UUID) = transaction(database) {
        val userEntity = UserEntity.findById(id)
        if (userEntity == null) {
            throw Exception("User with ID $id not found.")
        } else {
            userEntity.delete()
        }
    }

}