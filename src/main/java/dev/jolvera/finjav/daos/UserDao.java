package dev.jolvera.finjav.daos;

import dev.jolvera.finjav.models.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    @SqlQuery("SELECT * FROM users WHERE id = :id")
    @RegisterBeanMapper(User.class)
    User FindById(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM users")
    @RegisterBeanMapper(User.class)
    List<User> FindAll();

    @SqlQuery("SELECT * FROM users WHERE name = :name")
    @RegisterBeanMapper(User.class)
    User Login(@Bind("name") String username);

    @SqlUpdate("INSERT INTO users (id, date_created, date_modified, name, email, password_hash) VALUES (:id, :dateCreated, :dateModified, :name, :email, :passwordHash)")
    void CreateUser(@BindBean User userDto);

    @SqlUpdate("UPDATE users SET date_modified = :dateModified, name = :name, email = :email, password_hash = :passwordHash WHERE id = :id")
    int UpdateUser(@BindBean User update);

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    int DeleteById(@Bind("id") UUID id);
}
