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
    @SqlQuery("SELECT * FROM user WHERE id = :id")
    @RegisterBeanMapper(User.class)
    User FindById(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM User")
    @RegisterBeanMapper(User.class)
    List<User> findAll();

    @SqlUpdate("INSERT INTO user (name, email, password) VALUES (:name, :email, :password)")
    @RegisterBeanMapper(User.class)
    User CreateUser(
            @Bind("name") String name,
            @Bind("email") String email,
            @Bind("password") String password);

    @SqlUpdate("UPDATE user SET name = :name, email = :email, password = :password WHERE id = :id")
    User UpdateUser(@BindBean User update);

    @SqlUpdate("DELETE FROM user WHERE id = :id")
    Boolean deleteById(@Bind("id") UUID id);
}
