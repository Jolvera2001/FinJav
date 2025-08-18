package dev.jolvera.finjav.services.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> FindById(UUID id);
    List<User> FindAll();
    User Login(String username, String password);
    void Register(User user);
    int UpdateUser(User user);
    int DeleteById(UUID id);
}
