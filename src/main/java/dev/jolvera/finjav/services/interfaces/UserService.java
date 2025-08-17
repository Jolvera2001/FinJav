package dev.jolvera.finjav.services.interfaces;

import dev.jolvera.finjav.models.User;
import dev.jolvera.finjav.models.dtos.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

// TODO: Refactor, can be synchronous while viewModels start Async
public interface UserService {
    Optional<User> FindById(UUID id);
    List<User> FindAll();
    User Login(String username, String password);
    User Register(UserDto user);
    int UpdateUser(UserDto user);
    int DeleteById(UUID id);
}
