package dev.jolvera.finjav.services.interfaces;

import dev.jolvera.finjav.models.User;
import dev.jolvera.finjav.models.dtos.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<Optional<User>> FindById(UUID id);
    CompletableFuture<List<User>> FindAll();
    CompletableFuture<User> Login(String username, String password);
    CompletableFuture<User> Register(UserDto user);
    CompletableFuture<Integer> UpdateUser(UserDto user);
    CompletableFuture<Integer> DeleteById(UUID id);
}
