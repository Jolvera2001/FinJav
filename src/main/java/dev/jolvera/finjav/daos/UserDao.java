package dev.jolvera.finjav.daos;

import dev.jolvera.finjav.models.User;
import dev.jolvera.finjav.models.dtos.UserDto;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
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

    @SqlUpdate("INSERT INTO users (id, name, email, password) VALUES (:name, :email, :password)")
    @GetGeneratedKeys("id")
    UUID CreateUser(@BindBean UserDto userDto);

    @SqlUpdate("UPDATE users SET name = :name, email = :email, password = :password WHERE id = :id")
    int UpdateUser(@BindBean UserDto update);

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    int DeleteById(@Bind("id") UUID id);
}
