package dev.jolvera.finjav.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recurrence {
    private UUID id;
    private UUID userId;
    private LocalDate dateCreated;
    private LocalDate dateModified;
    private String name;
    private Boolean isIncome;
    private LocalDate recurringDate;
}

