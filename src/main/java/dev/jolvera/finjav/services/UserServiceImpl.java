package dev.jolvera.finjav.services;

import dev.jolvera.finjav.daos.UserDao;
import dev.jolvera.finjav.models.User;
import dev.jolvera.finjav.models.dtos.UserDto;
import dev.jolvera.finjav.services.interfaces.UserService;
import dev.jolvera.finjav.utils.PasswordUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class UserServiceImpl implements UserService {
    private final DbContext dbContext;

    @Inject
    public UserServiceImpl(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public Optional<User> FindById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }

        return dbContext.withHandle(handle ->
            Optional.ofNullable(handle.attach(UserDao.class).FindById(id)));
    }

    @Override
    public List<User> FindAll() {
        return dbContext.withHandle(handle ->
                handle.attach(UserDao.class).FindAll());
    }

    @Override
    public User Login(String username, String password) {
        var user = dbContext.withHandle(handle ->
                handle.attach(UserDao.class).Login(username));

        if (user == null) {
            return null;
        }

        if (PasswordUtils.VerifyPassword(password, user.getPasswordHash())) {
            return user.withoutPassword();
        } else {
            return null;
        }
    }

    @Override
    public User Register(UserDto user) {
        var registeredId = dbContext.withHandle(handle ->
                handle.attach(UserDao.class).CreateUser(user));

        var newUser = this.FindById(registeredId);
        return newUser.map(User::withoutPassword).orElse(null);
    }

    @Override
    public int UpdateUser(UserDto user) {
        return dbContext.withHandle(handle ->
                handle.attach(UserDao.class).UpdateUser(user));
    }

    @Override
    public int DeleteById(UUID id) {
        return dbContext.withHandle(handle ->
                handle.attach(UserDao.class).DeleteById(id));
    }
}
