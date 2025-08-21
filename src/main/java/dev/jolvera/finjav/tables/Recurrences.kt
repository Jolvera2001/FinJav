package dev.jolvera.finjav.tables

import dev.jolvera.finjav.models.Recurrence
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.UUID

object Recurrences: UUIDTable("recurrences") {
    val user = reference("user_id", Users.id)
    val dateCreated = date("date_created")
    val dateModified = date("date_modified")
    val name = varchar("name", 50).uniqueIndex()
    val amount = decimal("amount", 12, 2)
    val isIncome = bool("is_income")
    val recurringDate = date("recurring_date")
}

class RecurrenceEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<RecurrenceEntity>(Recurrences)

    var user by UserEntity referencedOn Recurrences.user

    var dateCreated by Recurrences.dateCreated
    var dateModified by Recurrences.dateModified
    var name by Recurrences.name
    var amount by Recurrences.amount
    var isIncome by Recurrences.isIncome
    var recurringDate by Recurrences.recurringDate

    override fun toString() = "RecurrenceEntity(id=$id, dateCreated=$dateCreated, dateModified=$dateModified, name=$name,)"

    fun toRecurrence() : Recurrence {
        return Recurrence(
            id = this.id.value,
            userId = this.user.id.value,
            dateCreated = this.dateCreated,
            dateModified = this.dateModified,
            name = this.name,
            amount = this.amount,
            isIncome = this.isIncome,
            recurringDate = this.recurringDate
        )
    }
}