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

    public User withoutPassword() {
        return new User(
                this.id,
                this.dateCreated,
                this.dateModified,
                this.name,
                this.email,
                null
        );
    }
}
