package dev.jolvera.finjav.services;

import dev.jolvera.finjav.daos.UserDao;
import dev.jolvera.finjav.models.User;
import dev.jolvera.finjav.models.dtos.UserDto;
import dev.jolvera.finjav.services.interfaces.UserService;
import dev.jolvera.finjav.utils.PasswordUtils;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserServiceImpl implements UserService {
    private final DbContext dbContext;

    @Inject
    public UserServiceImpl(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public CompletableFuture<Optional<User>> FindById(UUID id) {
        if (id == null) {
            return  CompletableFuture.completedFuture(Optional.empty());
        }

        return dbContext.withHandleAsync(handle ->
                Optional.ofNullable(handle.attach(UserDao.class).FindById(id)));
    }

    @Override
    public CompletableFuture<List<User>> FindAll() {
        return dbContext.withHandleAsync(handle ->
                handle.attach(UserDao.class).FindAll());
    }

    @Override
    public CompletableFuture<User> Login(String username, String password) {
        return dbContext.withHandleAsync(handle ->
                handle.attach(UserDao.class).Login(username))
                .thenApply(user -> {
                    if (PasswordUtils.VerifyPassword(password, user.getPasswordHash())) {
                        return new User(user.getId(), user.getName(), user.getEmail());
                    } else {
                        return null;
                    }
                });
    }

    @Override
    public CompletableFuture<UUID> CreateUser(UserDto user) {
        return dbContext.withHandleAsync(handle ->
                handle.attach(UserDao.class).CreateUser(user));
    }

    @Override
    public CompletableFuture<Integer> UpdateUser(UserDto user) {
        return dbContext.withHandleAsync(handle ->
                handle.attach(UserDao.class).UpdateUser(user));
    }

    @Override
    public CompletableFuture<Integer> DeleteById(UUID id) {
        return dbContext.withHandleAsync(handle ->
                handle.attach(UserDao.class).DeleteById(id));
    }
}
