package dev.jolvera.finjav.models;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private LocalDate dateCreated;
    private LocalDate dateModified;
    private String name;
    private String email;
    private String passwordHash;

    public User(final UUID id, final String name, final String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
