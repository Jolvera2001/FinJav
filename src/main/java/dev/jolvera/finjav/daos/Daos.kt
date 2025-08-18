package dev.jolvera.finjav.daos

import dev.jolvera.finjav.models.Recurrence
import dev.jolvera.finjav.models.User
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.*


interface UserDao {
    @SqlQuery("SELECT * FROM users")
    @RegisterBeanMapper(User::class)
    fun findAll(): List<User>

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    @RegisterBeanMapper(User::class)
    fun findById(id: UUID): User?

    @SqlQuery("SELECT * FROM users WHERE name = :name")
    @RegisterBeanMapper(User::class)
    fun findByName(name: String): User?

    @SqlUpdate("INSERT INTO users (id, date_created, date_modified, name, email, password_hash) VALUES (:id, :dateCreated, :dateModified, :name, :email, :passwordHash)")
    fun createUser(@BindBean user: User)

    @SqlUpdate("UPDATE users SET date_modified = :dateModified, name = :name, email = :email, password_hash = :passwordHash WHERE id = :id")
    fun updateUser(@BindBean update: User): Int

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    fun deleteById(@Bind("id") id: UUID): Int
}

interface RecurrenceDao {
    @SqlQuery("SELECT * FROM recurrences WHERE id = :id")
    @RegisterBeanMapper(Recurrence::class)
    fun findById(@Bind("id") id: UUID): Recurrence?

    @SqlQuery("SELECT * FROM recurrences WHERE user_id = :id")
    @RegisterBeanMapper(Recurrence::class)
    fun findAllFromUser(@Bind("id") id: UUID): List<Recurrence>

    @SqlUpdate("INSERT INTO recurrences (id, user_id, date_created, date_modified, name, amount, is_income, recurring_date) VALUES (:id, :userId, :dateCreated, :dateModified, :name, :amount, :isIncome, :recurringDate)")
    fun createRecurrence(@BindBean recurrence: Recurrence)

    @SqlUpdate("UPDATE recurrences SET  date_modified = :dateModified, name = :name, amount = :amount, is_income = :isIncome, recurring_date = :recurringDate WHERE id = :id")
    fun updateRecurrence(@BindBean recurrence: Recurrence): Int

    @SqlUpdate("DELETE FROM recurrences WHERE id = :id")
    fun deleteRecurrence(@Bind("id") id: UUID): Int
}