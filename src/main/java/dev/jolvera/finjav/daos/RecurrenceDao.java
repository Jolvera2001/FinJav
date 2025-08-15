package dev.jolvera.finjav.daos;

import dev.jolvera.finjav.models.Recurrence;
import dev.jolvera.finjav.models.dtos.RecurrenceDto;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RecurrenceDao {

    @SqlQuery("SELECT * FROM recurrences WHERE id = :id")
    @RegisterBeanMapper(Recurrence.class)
    Recurrence FindById(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM recurrences")
    @RegisterBeanMapper(Recurrence.class)
    List<Recurrence> findAll();

    @SqlUpdate("INSERT INTO recurrences (id, name, is_income, recurring_date) VALUES (:name, :is_income, :recurring_date")
    @GetGeneratedKeys("id")
    UUID CreateRecurrence(@BindBean RecurrenceDto recurrenceDto);

    @SqlUpdate("UPDATE recurrences SET name = :name, email = :email, password = :password WHERE id = :id")
    int UpdateRecurrence(@BindBean Recurrence recurrence);

    @SqlUpdate("DELETE FROM recurrences WHERE id = :id")
    int DeleteRecurrence(@Bind("id") UUID id);
}
